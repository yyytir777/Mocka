Jodag
===
**Jodag** is a Java library for **automatic data generation** based on predefined files. <br>
You can register your own **text-based datasets** using line-separated files (CRLF format), allowing you to freely define and use any type of data you want <br>
It consists of two modules:

> **jodag-core**: a lightweight, pure Java library with no dependencies.<br> 
> **jodag-spring**: an extension for generating data based on Spring JPA Entity.


Jodag-core
===
- **Jodag-core** helps you to automatically generate common data like name, email, etc
- `DataRegistry` Class manages instances of the `Generator` class, which is responsible for generating each type of data.
- You can add custom registerable `Generator` by injecting file paths containing your own datasets
- Supports random selection, range-based values, and reusable generators.


Jodag-spring
===
- **Jodag-spring** is a Spring Boot extension that helps you auto-generate entity data based on JPA `@Entity` classes.
- You can annotate entity fields with `@Generate` to specify how values should be filled.
- It allows you to generate countless Entity instances fit perfectly into your project.
- Ideal for creating dummy test data or prepopulating your database.

Example Test Codes
===
```java
    @Test
    @DisplayName("DataRegistry_클래스를_가져옵니다.")
    public void get_dataRegistry() {
        // given & when
        DataRegistry dataRegistry = DataRegistry.getInstance();
        
        // then
        assertThat(dataRegistry).isNotNull();
    }
    
    @Test
    @DisplayName("DataRegistry는_싱글톤으로_관리됩니다V1.")
    public void dataRegistry_is_singleton_V1() {
        // given & when
        DataRegistry firstDataRegistry = DataRegistry.getInstance();
        DataRegistry secondDataRegistry = DataRegistry.getInstance();
    
        // then
        assertThat(firstDataRegistry).isEqualTo(secondDataRegistry);
    }
    
    @Test
    @DisplayName("기본_generator를_받아옵니다.")
    public void get_generator() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
    
        // when
        Generator<String> defaultGenerator = dataRegistry.getGenerator();
    
        // then
        assertThat(defaultGenerator.get()).isNotNull();
    }
    
    @Test
    @DisplayName("기본_Generator는_싱글톤으로_관리됩니다.")
    public void generator_is_singleton() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
    
        // when
        Generator<String> nameGenerator1 = dataRegistry.getGenerator(GenerateType.NAME);
        Generator<String> nameGenerator2 = dataRegistry.getGenerator(GenerateType.NAME);
    
        // then
        assertThat(nameGenerator1).isSameAs(nameGenerator2);
    }
    
    @Test
    @DisplayName("이름을_반환하는_generator를_생성하여_데이터를_랜덤_생성합니다.")
    public void random_generate_name_by_generator() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
    
        // when
        Generator<String> generator = dataRegistry.getGenerator(GenerateType.NAME);
        String randomName1 = generator.get();
        String randomName2 = generator.get();
    
        // then
        assertThat(randomName1).isNotNull();
        assertThat(randomName2).isNotNull();
        assertThat(randomName1).isNotEqualTo(randomName2);
    }
    
    @Test
    @DisplayName("이메일을_반환하는_generator를_생성하여_데이터를_랜덤_생성합니다.")
    public void random_generate_email_by_generator() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
    
        // when
        Generator<String> generator = dataRegistry.getGenerator(GenerateType.EMAIL);
        String randomEmail1 = generator.get();
        String randomEmail2 = generator.get();
    
        // then
        assertThat(randomEmail1).isNotNull();
        assertThat(randomEmail2).isNotNull();
        assertThat(randomEmail1).isNotEqualTo(randomEmail2);
    }
```