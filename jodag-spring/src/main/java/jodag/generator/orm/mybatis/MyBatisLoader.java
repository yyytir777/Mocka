package jodag.generator.orm.mybatis;

import jodag.generator.orm.ORMLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.lang.reflect.Field;

@Component
public class MyBatisLoader implements ORMLoader {

    @Value("${mybatis.location:classpath*:mapper/**/*.xml}")
    private String mapperLocations;

    private final MyBatisMetadata myBatisMetadata;

    public MyBatisLoader(MyBatisMetadata myBatisMetadata) {
        this.myBatisMetadata = myBatisMetadata;
    }

    private static final Logger log = LoggerFactory.getLogger(MyBatisLoader.class);


    public void load() {
        Long startMs = System.currentTimeMillis();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources;

        try {
            resources = resolver.getResources(mapperLocations);
            myBatisMetadata.addResources(resources);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Resource resource : resources) {
            parseXml(resource);
        }

        myBatisMetadata.printResources();
        System.out.println("====================");
        myBatisMetadata.printClass();
        System.out.println("====================");
        myBatisMetadata.printFields();

        Long endMs = System.currentTimeMillis();
        log.info("Finished parsing Mapper.xml in {}ms. Add {} mappers in SpringGeneratorFactory", (endMs - startMs), myBatisMetadata.getResourceCount());
    }

    private void parseXml(Resource resource) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(resource.getInputStream());
            Element docElement = document.getDocumentElement();

            if(!docElement.getNodeName().equals("mapper")) return;

            // resultMap 태그
            NodeList resultMapList = docElement.getElementsByTagName("resultMap");

            for (int i = 0; i < resultMapList.getLength(); i++) {
                Element resultMap = (Element) resultMapList.item(i);
                String id = resultMap.getAttribute("id");
                String type = resultMap.getAttribute("type");

                Class<?> clazz = Class.forName(type);
                myBatisMetadata.addClass(id, clazz);

                // <id> 태그 -> 여러개일수도 있음 -> field를 저장할 때 id인지 아닌지 체크하는 PropertyField로 wrap진행
                parseAndAddFields(clazz, resultMap, "id", true);

                // <result> 태그
                parseAndAddFields(clazz, resultMap, "result",  false);
            }

        } catch (Exception e) {
            throw new RuntimeException(resource.getFilename() + "을 파싱할 수 없습니다.");
        }
    }

    private void parseAndAddFields(Class<?> clazz, Element resultMap, String tagName, boolean isId) {
        NodeList idNodes = resultMap.getElementsByTagName(tagName);
        for (int j = 0; j < idNodes.getLength(); j++) {
            String property = ((Element) idNodes.item(j)).getAttribute("property");
            try {
                Field field = clazz.getDeclaredField(property);
                myBatisMetadata.addFieldToClass(clazz, PropertyField.of(field, isId));
            } catch (NoSuchFieldException ignored) {
                // 해당 필드가 없으면 무시 (xml파일과 클래스 파일을 비교하여 공통된 필드만 사용 예정)
            }
        }
    }
}
