Mocka
===
[![Test Entity_Instantiator Library](https://github.com/yyytir777/Mocka/actions/workflows/test.yaml/badge.svg?branch=main)](https://github.com/yyytir777/Mocka/actions/workflows/test.yaml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=yyytir777_mocka&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=yyytir777_mocka)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=yyytir777_mocka&metric=bugs)](https://sonarcloud.io/summary/new_code?id=yyytir777_mocka)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=yyytir777_mocka&metric=coverage)](https://sonarcloud.io/summary/new_code?id=yyytir777_mocka)

[//]: # (<p align="center">)

[//]: # (  <img src="https://raw.githubusercontent.com/wiki/yyytir777/Mocka/images/README%20title.png" width="800"/>)

[//]: # (</p>)

<p align="center"><b><span style="font-size: 16px; font-style: italic">"Generate ORM/ODM Entity Instances Faster for Mock Data"</span></b></p>

**Mocka** is a Spring-based library for automatically generating ORM/ODM entity instances for **Mock Data**.
It can create not only single entities but also complex entity relationships.
By mapping files to entity classes or fields, it provides flexible and customizable instance generation.
It supports **Hibernate** and **MyBatis** as **ORM** frameworks, and **MongoDB** as an **ODM** framework.

You can create entity instances **without knowing their fields**:
```java
// ORM
Generator<Member> generator = entityGeneratorFactory.getGenerator(Member.class);
Member member = generator
        .generateType(GenerateType.SELF)
        .ormType(ORMType.HIBERNATE)
        .get();

// ODM
DocumentGenerator<Member> generator = documentGeneratorFactory.getGenerator(Member.class);
Member member = generator.get();
```

Such randomly generated instances can be used in various contexts, such as **testing**, **data populating**, and more.

This project consists of three modules:
- **Mocka Core** – generates field values when instantiating entities/documents.
- **Mocka ORM** – handles the logic for generating ORM entity instances (Hibernate, MyBatis).
- **Mocka ODM** – handles the logic for generating ODM document instances (MongoDB).

Please visit the GitHub Wiki for detailed usage of each module.

| module          | version                                                                                          | Github Page                                                                 |
|-----------------|--------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| **mocka core**  | ![Maven Central Version](https://img.shields.io/maven-central/v/io.github.yyytir777/mocka-core) | [**Core Github Page**](https://github.com/yyytir777/Mocka/wiki/Mocka-Core)  |
| **mocka orm**   | ![Maven Central Version](https://img.shields.io/maven-central/v/io.github.yyytir777/mocka-orm)  | [**ORM Github Page**](https://github.com/yyytir777/Mocka/wiki/Mocka-ORM)    |
| **mocka odm**   | ![Maven Central Version](https://img.shields.io/maven-central/v/io.github.yyytir777/mocka-odm)  | [**ODM Github Page**](https://github.com/yyytir777/Mocka/wiki/Mocka-ODM)    |


## Key Features
- **ORM/ODM Instance Auto Generation** : Easily instantiate ORM entities and ODM documents without manually assigning fields.
- **Multi-ORM Support** : Automatically detects ORM frameworks (Hibernate, MyBatis) and scans entity classes to generate appropriate instances.
- **MongoDB Support** : Generates MongoDB document instances including BSON types, Geo types, and GeoJSON types.
- **Random Data Generation** : Supports basic types such as String, Number, and Date, as well as Email, Lorem Ipsum, Name, IP Address, and more.
- **Regex Pattern Generation** : Supports generating strings that match the given regular expression patterns.
- **Relationship Support** : Not only generates entities/documents but also creates associated ones recursively.
- **Extensibility** : Implement your own custom generators to support new data types seamlessly.
- **Flexibility** : Map entity fields or entire classes to specific datasets or files for tailored instance generation.
- **Test Optimization** : Automatically generate large volumes of instances to accelerate unit, integration, and data-populating tests.


## Import Library

### Gradle
```groovy
// ORM
dependencies {
    implementation 'io.github.yyytir777:mocka-core:1.4.0'
    implementation 'io.github.yyytir777:mocka-orm:1.4.0'
}

// ODM
dependencies {
    implementation 'io.github.yyytir777:mocka-core:1.4.0'
    implementation 'io.github.yyytir777:mocka-odm:1.4.0'
}
```

### Maven
```xml
<!-- ORM -->
<dependencies>
    <dependency>
        <groupId>io.github.yyytir777</groupId>
        <artifactId>mocka-core</artifactId>
        <version>1.4.0</version>
    </dependency>
    <dependency>
        <groupId>io.github.yyytir777</groupId>
        <artifactId>mocka-orm</artifactId>
        <version>1.4.0</version>
    </dependency>
</dependencies>

<!-- ODM -->
<dependencies>
    <dependency>
        <groupId>io.github.yyytir777</groupId>
        <artifactId>mocka-core</artifactId>
        <version>1.4.0</version>
    </dependency>
    <dependency>
        <groupId>io.github.yyytir777</groupId>
        <artifactId>mocka-odm</artifactId>
        <version>1.4.0</version>
    </dependency>
</dependencies>
```

## What Can You Do With It?

### 1. Generate Entity/Document Instances
```java
// ORM
Generator<Member> generator = entityGeneratorFactory.getGenerator(Member.class);
Member member = generator
        .generateType(GenerateType.SELF)
        .ormType(ORMType.HIBERNATE)
        .get();

// ODM
DocumentGenerator<Member> generator = documentGeneratorFactory.getGenerator(Member.class);
Member member = generator.get();
```

### 2. Map Fields to a Generator or File
```java
// ORM
@Entity
public class Member {
    @ValueSource(generatorKey = "name")
    private String name;
}

// ODM
@Document
public class Member {
    @ValueSource(generatorKey = "name")
    private String name;
}
```

```java
@ValueSource(path = "name.txt", type = String.class)
private String name;
```

### 3. Map Entire Entities/Documents to Files
- Supports **YAML**, **XML**, **JSON**, **CSV**, and **XLSX**
```java
// ORM
@Entity
@FileSource(path = "member.csv")
public class Member { }

// ODM
@Document
@FileSource(path = "member.yaml")
public class Member { }
```

### 4. Map Fields to Regex Patterns

```java
@RegexSource(pattern = "[a-zA-Z]{10}")
private String name;
```

### 5. Register Custom Generator to GeneratorFactory

```java
GeneratorFactory.putGenerator(new RegistrableGenerator<>("test_generator", "test.txt", String.class));
Generator<String> generator = GeneratorFactory.getGenerator("test_generator");
```


## @Mocka Annotation
- You can easily initialize entity/document instances in test code.
- Developers can focus on core logic without having to manually generate relational instances for each test method.

```java
// ORM
@SpringBootTest
@ExtendWith(MockaOrmExtension.class)
public class MockaOrmTest {

    @Mocka
    Parent parent;

    @Test
    void assert_parent_is_not_null() {
        assertThat(parent).isNotNull();
    }
}

// ODM
@SpringBootTest
@ExtendWith(MockaOdmExtension.class)
public class MockaOdmTest {

    @Mocka
    Parent parent;

    @Test
    void assert_parent_is_not_null() {
        assertThat(parent).isNotNull();
    }
}
```

## @MockaOrmConfig / @MockaOdmConfig Annotation
- Defines configuration options for instance generation in tests.
- `size` : Specifies how many instances should be generated for `1:n` associations. (Default : 5)
- `ormType` (ORM only) : Specifies which ORM implementation (`HIBERNATE`, `MYBATIS`) to use.
- `odmType` (ODM only) : Specifies which ODM implementation (`MONGODB`) to use.

```java
// ORM
@MockaOrmConfig(size = 10, ormType = ORMType.HIBERNATE)
public class MockaOrmTest {

    @Mocka(GenerateType.CHILD)
    Parent parent;

    @Test
    void generate_children_with_given_size_and_specified_orm() {
        assertThat(parent.getChildren()).hasSize(10);
    }
}

// ODM
@MockaOdmConfig(size = 10, odmType = ODMType.MONGODB)
public class MockaOdmTest {

    @Mocka(GenerateType.CHILD)
    Parent parent;

    @Test
    void generate_children_with_given_size_and_specified_odm() {
        assertThat(parent.getChildren()).hasSize(10);
    }
}
```


## GenerateType
When generating instances, you can specify the GenerateType to control how associated entities/documents are created.

![GenerateType.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType.png)

In the diagram above, entity A and B have a 1:N relationship, and B and C also have a 1:N relationship.
Let's use this to explain each GenerateType strategy.

---

### GenerateType.SELF
Generates only the specified instance.<br>
Even if relationships exist, they are initialized as null.

```java
Generator<B> generator = springGenerator.getGenerator(B.class);
B b = generator
        .generateType(GenerateType.SELF)
        .get();
```
If you generate entity B with `GenerateType.SELF`, only B will be created; associated entities will not be generated.

![GenerateType SELF.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20SELF.png)

---

#### GenerateType.CHILD
Generates the specified instance and its direct child instances.

```java
Generator<B> generator = springGenerator.getGenerator(B.class);
B b = generator
        .generateType(GenerateType.CHILD)
        .get();
```
If you generate entity B with `GenerateType.CHILD`, both B and its child entity C will be created.

![GenerateType CHILD.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20CHILD.png)

---

#### GenerateType.CHILDREN
Recursively generates the specified instance and all its descendant instances.

```java
Generator<B> generator = springGenerator.getGenerator(B.class);
B b = generator
        .generateType(GenerateType.CHILDREN)
        .get();
```
If you generate entity A with `GenerateType.CHILDREN`, entities A, B, and C will all be created recursively.

![GenerateType CHILDREN.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20CHILDREN.png)

---

#### GenerateType.PARENT
Generates the specified instance and its direct parent instance.

```java
Generator<B> generator = springGenerator.getGenerator(B.class);
B b = generator
        .generateType(GenerateType.PARENT)
        .get();
```
If you generate entity B with `GenerateType.PARENT`, both B and its parent entity A will be created.

![GenerateType PARENT.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20PARENT.png)

---

#### GenerateType.PARENTS
Recursively generates the specified instance and all its ancestor instances.

```java
Generator<B> generator = springGenerator.getGenerator(B.class);
B b = generator
        .generateType(GenerateType.PARENTS)
        .get();
```
If you generate entity C with `GenerateType.PARENTS`, entities C, B, and A will all be created recursively.

![GenerateType PARENTS.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20PARENTS.png)

---

#### GenerateType.ALL
Recursively generates the specified instance and all related instances, both parents and children.

```java
Generator<B> generator = springGenerator.getGenerator(B.class);
B b = generator
        .generateType(GenerateType.ALL)
        .get();
```
If you generate entity B with `GenerateType.ALL`, entities A, B, and C will all be created together.

![GenerateType ALL.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20ALL.png)


---

## GitHub Wiki

If you want to see more information of `Mocka`, please visit [GitHub Wiki](https://github.com/yyytir777/Mocka/wiki) Pages!!
