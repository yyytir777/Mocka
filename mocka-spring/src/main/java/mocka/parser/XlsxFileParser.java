package mocka.parser;

import mocka.random.RandomProvider;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class XlsxFileParser implements FileParser {

    private static final XlsxFileParser INSTANCE = new XlsxFileParser();
    private final RandomProvider randomProvider = RandomProvider.getInstance();

    private XlsxFileParser() {}

    public static XlsxFileParser getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T parse(InputStream inputStream, Class<T> clazz) {
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);


            // 시트가 없거나, 행이 하나도 없는 경우 → 헤더 없음
            if (sheet == null || sheet.getPhysicalNumberOfRows() == 0) {
                throw new RuntimeException("Empty XLSX file: no header found.");
            }

            Iterator<Row> rowIterator = sheet.iterator();
            if (!rowIterator.hasNext()) {
                throw new RuntimeException("Empty XLSX file: no header found.");
            }

            rowIterator = sheet.iterator();
            List<String> headers = getHeader(rowIterator);
            if (headers.isEmpty()) {
                throw new RuntimeException("Empty XLSX file: header row is blank.");
            }

            if (!rowIterator.hasNext()) {
                throw new RuntimeException("Empty XLSX file: no data found.");
            }

            Row row = rowIterator.next();
            if(row.getFirstCellNum() == -1) {
                throw new RuntimeException("Empty XLSX file: no data found.");
            }

            List<T> list = getRecord(rowIterator, clazz, headers);
            if (list.isEmpty()) {
                throw new RuntimeException("Empty XLSX file: no valid records found.");
            }

            return list.get(randomProvider.getNextIdx(list.size()));
        } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getHeader(Iterator<Row> rowIterator) {
        Row headerRow = rowIterator.next();
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            DataFormatter formatter = new DataFormatter();
            headers.add(formatter.formatCellValue(cell).trim());
        }
        return headers;
    }

    private <T> List<T> getRecord(Iterator<Row> rowIterator, Class<T> clazz, List<String> headers) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();

        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if(row.getFirstCellNum() == -1) break;
            T instance = clazz.getDeclaredConstructor().newInstance();

            Map<String, Field> fieldMap = new HashMap<>();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                fieldMap.put(field.getName(), field);
            }

            for (int i = 0; i < headers.size(); i++) {
                String headerName = headers.get(i);
                Field field = fieldMap.get(headerName);
                if (field == null) continue;

                Cell cell = row.getCell(i);
                Object value = convertValue(field.getType(), cell);
                field.set(instance, value);
            }
            list.add(instance);
        }
        return list;
    }
}
