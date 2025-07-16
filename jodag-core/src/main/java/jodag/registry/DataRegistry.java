package jodag.registry;

import jodag.exception.dataRegistry.DataRegistryException;
import jodag.exception.dataRegistry.DuplicateGeneratorException;
import jodag.exception.dataRegistry.NotFoundGeneratorException;
import jodag.generator.*;
import jodag.generator.common.DefaultGenerator;
import jodag.generator.common.EmailGenerator;
import jodag.generator.common.LoremIpsumGenerator;
import jodag.generator.common.NameGenerator;
import jodag.generator.primitive.*;
import jodag.generator.registable.RegisterableGenerator;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * DataRegistry : Generator를 생성, 관리, 삭제하는 역할을 맡는 클래스
 */
public class DataRegistry {

    // DataRegistry는 싱글톤으로 관리
    private static final DataRegistry INSTANCE = new DataRegistry();
//     다양한 타입의 Generator를 하나의 registry에서 관리하기 위해 Generator<?>로 제네릭 설정 진행
//     해당 generator를 get할 때 타입 안정성 unchecked예외 발생 가능
    private final Map<String, Generator<?>> registry = new ConcurrentHashMap<>();

    // DataRegistry 생성 시 기본 generator 초기화
    private DataRegistry() {
        registerDefaults();
    }

    public static DataRegistry getInstance() {
        return INSTANCE;
    }

    // 기본 generator 등록 로직
    private void registerDefaults() {
        registerCommon();
        registerPrimitive();
    }

    // 기본 데이터 추가 (ex. 이름, 이메일 등..)
    private void registerCommon() {
        add(GenerateType.DEFAULT,  DefaultGenerator.class);
        add(GenerateType.NAME, NameGenerator.class);
        add(GenerateType.EMAIL, EmailGenerator.class);
        add(GenerateType.LOREM_IPSUM, LoremIpsumGenerator.class);
    }

    // Primitive 타입 추가 (int, string, double, 등...)
    private void registerPrimitive() {
        add(PrimitiveType.Boolean, BooleanGenerator.class);
        add(PrimitiveType.Byte, ByteGenerator.class);
        add(PrimitiveType.Character, CharacterGenerator.class);
        add(PrimitiveType.Double, DoubleGenerator.class);
        add(PrimitiveType.Float, FloatGenerator.class);
        add(PrimitiveType.Integer, IntegerGenerator.class);
        add(PrimitiveType.Long, LongGenerator.class);
        add(PrimitiveType.Short, ShortGenerator.class);
    }

    /**
     * 등록할 key와 데이터파일이 담긴 resourcePath를 파라미터로 generator를 등록
     * generator는 데이터파일을 검색하여 랜덤 값 반환
     * @param key
     * @param resourcePath
     */
    public <T> void add(String key, Path resourcePath, Class<T> type) {
        if (registry.containsKey(key)) {
            throw new DuplicateGeneratorException(key);
        }

        Generator<T> generator = new RegisterableGenerator<>(key, resourcePath, type);
        registry.put(key, generator);
    }

    /**
     * 기본으로 들어있는 Generator를 등록
     * @param type
     * @param generatorClass
     */
    private void add(GenerateType type, Class<? extends Generator<?>> generatorClass) {
        if (registry.containsKey(type.name())) {
            throw new DuplicateGeneratorException(type.name());
        }

        try {
            Generator<?> generator = switch (type) {
                case EMAIL -> EmailGenerator.getInstance();
                case NAME -> NameGenerator.getInstance();
                case LOREM_IPSUM -> LoremIpsumGenerator.getInstance();
                default -> DefaultGenerator.getInstance();
            };

            registry.put(type.name(), generator);
        } catch (Exception e) {
            throw new RuntimeException("Generator 생성 실패 - " + generatorClass.getName(), e);
        }
    }

    private void add(PrimitiveType type, Class<? extends Generator<?>> generatorClass) {
        if (registry.containsKey(type.name())) {
            throw new DuplicateGeneratorException(type.name());
        }

        try {
            Generator<?> generator = switch (type) {
                case Boolean -> BooleanGenerator.getInstance();
                case Byte -> ByteGenerator.getInstance();
                case Character -> CharacterGenerator.getInstance();
                case Double -> DoubleGenerator.getInstance();
                case Float -> FloatGenerator.getInstance();
                case Integer -> IntegerGenerator.getInstance();
                case Long -> LongGenerator.getInstance();
                case Short -> ShortGenerator.getInstance();
            };

            registry.put(type.name(), generator);
        } catch (Exception e) {
            throw new RuntimeException("Generator 생성 실패 - " + generatorClass.getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private  <T> Generator<T> get(String key) {
        Generator<?> generator = registry.get(key);
        if (generator == null) throw new NotFoundGeneratorException("등록된 Generator 없음: " + key);
        return (Generator<T>) generator;
    }

    @SuppressWarnings("unchecked")
    private <T> Generator<T> get(GenerateType type) {
        Generator<?> generator = registry.get(type.name());
        if (generator == null) throw new NotFoundGeneratorException("등록된 Generator 없음: " + type.name());
        return (Generator<T>) generator;
    }

    @SuppressWarnings("unchecked")
    private <T> Generator<T> get(PrimitiveType type) {
        Generator<?> generator = registry.get(type.name());
        if (generator == null) throw new NotFoundGeneratorException("등록된 Generator 없음: " + type.name());
        return (Generator<T>) generator;
    }

    public Generator<String> getGenerator() {
        return get(GenerateType.DEFAULT);
    }

    /**
     * 기본으로 등록된 generator를 return하는 메서드
     * 다양한 형식의 generator를 return하기 때문에
     * `SuppressWarnings("unchecked") 처리 진행
     * @param generatetype
     * @return
     */
    public <T> Generator<T> getGenerator(GenerateType generatetype) {
        return get(generatetype);
    }

    public <T> Generator<T> getGenerator(PrimitiveType primitivetype) {
        return get(primitivetype);
    }

    /**
     * 등록된 key로 key에 해당하는 generator를 return
     * @param key
     * @return
     */
    public <T> Generator<T> getGenerator(String key) {
        return get(key);
    }

    /**
     * key값으로 등록된 generator를 삭제
     * @param key
     */
    public void delete(String key) {
        Generator<?> removed = registry.remove(key);
        if(removed == null) {
            throw new NotFoundGeneratorException("삭제할 generator가 존재하지 않습니다.");
        }
    }

    public GeneratorInfo<?> getGeneratorInfo(String key) {
        Generator<?> generator = get(key);
        if (generator == null) {
            throw new IllegalArgumentException("generator를 찾을 수 없습니다. key : " + key);
        }
        return GeneratorInfo.from(generator);
    }

    public List<GeneratorInfo<?>> getAllGeneratorInfo() {
        List<GeneratorInfo<?>> generatorInfoList = registry.values().stream()
                .map(GeneratorInfo::from).collect(Collectors.toUnmodifiableList());

        if(generatorInfoList.isEmpty()) {
            throw new DataRegistryException("generator가 없습니다.");
        }
        return generatorInfoList;
    }

    public void clearAll() {

        for (Map.Entry<String, Generator<?>> entry : registry.entrySet()) {

            boolean isPrimitiveType = Arrays.stream(PrimitiveType.values())
                    .anyMatch(type -> type.name().equals(entry.getKey()));

            boolean isGenerateType = Arrays.stream(GenerateType.values())
                    .anyMatch(type -> type.name().equals(entry.getKey()));

            if(isPrimitiveType) {
                continue;
            } else if(isGenerateType) {
                continue;
            }
            registry.remove(entry.getKey());
        }
    }
}
