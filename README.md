Jodag
===
[![Test Jodag Library](https://github.com/yyytir777/jodag/actions/workflows/test.yaml/badge.svg?branch=main)](https://github.com/yyytir777/jodag/actions/workflows/test.yaml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=yyytir777_jodag&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=yyytir777_jodag)

**Jodag** is a Java library for **automatic data generation** based on predefined files. <br>
You can register your own **text-based datasets** using line-separated files (CRLF format), allowing you to freely define and use any type of data you want. <br>
- It consists of two modules:

> **jodag-core**: a lightweight, pure Java library with no dependencies.<br> 
> **jodag-spring**: an extension for generating data based on Spring JPA Entity.


Jodag-core
===
- **Jodag-core** helps you to automatically generate common data like name, email, etc
- `GenerateFactory` Class manages instances of the `Generator` class, which is responsible for generating each type of data.
- You can add custom registerable Generator, `RegistableGenerator` by injecting file paths containing your own datasets
- Supports random selection, range-based values, and reusable generators.


Jodag-spring
===
- **Jodag-spring** is a Spring Boot extension that helps you auto-generate entity data based on JPA `@Entity` classes.
- It allows you to generate countless Entity instances fit perfectly into your project, using `SpringGeneratorFactory` and `EntityGenerator`.
- Ideal library for creating dummy test data or prepopulating your database.

How To Use
===

## Import Library

### Gradle

```groovy
dependencies {
    implementation 'io.github.yyytir777:jodag-core:0.0.3'
    implementation 'io.github.yyytir777:jodag-spring:0.0.3'
}
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>io.github.yyytir777</groupId>
        <artifactId>jodag-core</artifactId>
        <version>0.0.3</version>
    </dependency>
    <dependency>
        <groupId>io.github.yyytir777</groupId>
        <artifactId>jodag-spring</artifactId>
        <version>0.0.3</version>
    </dependency>
</dependencies>
```

## Use Generator

### Common Generator
- Common Generator is predefined Generator used by EntityGenerator to fill fields with random value
- Generators are provided for every type supported by Hibernate.
- Default Generator(like name, email, country ...) is available.
- `GeneratorFactory` provides all Common Generators through static methods.

```java
NameGenerator nameGenerator = GeneratorFactory.name();
String name = nameGenerator.get();

EmailGenerator emailGenerator = GeneratorFactory.email();
String email = emailGenerator.get();

PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();
Integer integer = primitiveGenerator.getInteger();
```


### Registerable Generator
- `Registerable Generator` is custom Generator registered by library user.
- You can register Generator by providing an external file path, like this...
```java
GeneratorFactory.register("test_generator", "/Users/{user}/Desktop/test.txt", String.class);
Generator registerableGenerator = GeneratorFactory.getRegistableGenerator("test_generator");
String value = registableGenerator.get();
```

### Entity Generator
- `Entity Generator` is responsible only for creating entity instances.
- `SpringGeneratorFactory` is responsible for CRUD of `EntityGenerator`.
- An entity instance is created along with its associated entities, regardless of the relationship type (`@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`).  
- You can configure the entity generation strategy through `GenerateType.class`

```java
Generator<Member> memberGenerator = SpringGeneratorFactory.getGenerator(Member.class);

// GenerateType.SELF: only the entity itself is created.
// - Associated entities are not generated.
// - e.g., member is not null, but member.post == null
Member member = memberGenerator.get(GenerateType.SELF);


// GenerateType.CHILDREN: the entity and its child entities are created.
// - Parent entities are not generated.
// - e.g., member and member.post are not null, but member.group (parent) == null
Member member = memberGenerator.get(GenerateType.CHILDREN);


// GenerateType.PARENTS: the entity and its parent entities are created.
// - child entities are not generated.
// - e.g., member and member.post (parent) == null, but member.group is not null
Member member = memberGenerator.get(GenerateType.CHILDREN);


// GenerateType.ALL: the entity and its parents and children entities are created.
// All entities associated with the entity are generated.
// - e.g., member and member.post is not null, but member.group is not null
Member member = memberGenerator.get(GenerateType.CHILDREN);
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
