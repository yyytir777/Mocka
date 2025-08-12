Jodag
===
[![Test Jodag Library](https://github.com/yyytir777/jodag/actions/workflows/test.yaml/badge.svg?branch=main)](https://github.com/yyytir777/jodag/actions/workflows/test.yaml)

**Jodag** is a Java library for **automatic data generation** based on predefined files. <br>
You can register your own **text-based datasets** using line-separated files (CRLF format), allowing you to freely define and use any type of data you want <br>
It consists of two modules:

> **jodag-core**: a lightweight, pure Java library with no dependencies.<br> 
> **jodag-spring**: an extension for generating data based on Spring JPA Entity.


Jodag-core
===
- **Jodag-core** helps you to automatically generate common data like name, email, etc
- `GeneratFactory` Class manages instances of the `Generator` class, which is responsible for generating each type of data.
- You can add custom registerable Generator, `RegistableGenerator` by injecting file paths containing your own datasets
- Supports random selection, range-based values, and reusable generators.


Jodag-spring
===
- **Jodag-spring** is a Spring Boot extension that helps you auto-generate entity data based on JPA `@Entity` classes.
- It allows you to generate countless Entity instances fit perfectly into your project, using `EntityGenerator`.
- Ideal for creating dummy test data or prepopulating your database.

Use
===

## Import Library

### Gradle

```groovy
dependencies {
    implementation 'io.github.yyytir777:jodag-core:0.0.2'
    implementation 'io.github.yyytir777:jodag-spring:0.0.2'
}
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>io.github.yyytir777</groupId>
        <artifactId>jodag-core</artifactId>
        <version>0.0.2</version>
    </dependency>
    <dependency>
        <groupId>io.github.yyytir777</groupId>
        <artifactId>jodag-spring</artifactId>
        <version>0.0.2</version>
    </dependency>
</dependencies>
```


Example Test Codes
===
```java
@SpringBootTest(classes = TestConfig.class)
@DisplayName("Jodag-Spring 메인 기능 테스트 코드")
public class JodagSpringMainTest {

    @Test
    @DisplayName("엔티티 클래스는 자동으로 SpringGeneratorFactory에 저장됩니다.")
    void auto_register_generate_class_in_sprigGeneratorFactory() {
        List<String> generatorNames = SpringGeneratorFactory.getGeneratorNames();
        assertThat(Member.class.getSimpleName()).isIn(generatorNames);
    }

    @Test
    @DisplayName("Member 엔티티 및 인스턴스 생성")
    void test() {
        Generator<Member> generator = SpringGeneratorFactory.getGenerator(Member.class);

        Member member = generator.get();
        System.out.println("member = " + member.toString());
        assertThat(member.getId()).isNull(); // id는 JPA 생성전략에 따라 null로 설정 (@GeneratedValue(strategy = GenerationType.IDENTITY))
        assertThat(member.getEmail()).isNotNull();
        assertThat(member.getName()).isNotNull();

        List<String> generatorNames = SpringGeneratorFactory.getGeneratorNames();
        System.out.println("generatorNames = " + generatorNames);
        assertThat("Member").isIn(generatorNames);
    }

    @Test
    @DisplayName("Member 엔티티 인스턴스 10개 생성")
    void create_10_instances() {
        Generator<Member> generator = SpringGeneratorFactory.getGenerator(Member.class);

        for (int i = 0; i < 10; i++) {
            Member member = generator.get();
            System.out.println("member : " + member.toString());
            assertThat(member.getName()).isNotNull();
        }
    }
}
```