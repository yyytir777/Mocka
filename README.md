Mocka
===
[![Test Entity_Instantiator Library](https://github.com/yyytir777/Mocka/actions/workflows/test.yaml/badge.svg?branch=main)](https://github.com/yyytir777/Mocka/actions/workflows/test.yaml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=yyytir777_mocka&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=yyytir777_mocka)

<p align="center">
  <img src="https://raw.githubusercontent.com/wiki/yyytir777/Mocka/images/README%20title.png" width="800"/>
</p>

<p align="center"><b><span style="font-size: 16px; font-style: italic">"Generate ORM Entity Instances Faster for Mock Data"</span></b></p>

**Mocka** is a Spring-based library for automatically generate ORM Entity instances for **Mock Data**.
It can create from not only single entities but also complex entity relationships.
By mapping files to entity classes or fields, it provides flexible and customizable instance generation.
It supports **Hibernate** and **MyBatis** as **ORM** frameworks, and automatically detects which one is in use to scan and generate the appropriate entity instances.

You can create entity instances **without knowing their fields**:
```java
Generator<Member> generator = springGenerator.getGenerator(Member.class);
Member member = generator.get();
```

Such randomly generated ORM entity instances can be used in various contexts, such as **testing**, **data populating**, and more.

This project consists of two modules:
- **Mocka Core** – generates field values when instantiating entities.
- **Mocka Spring** – handles the logic for generating entity instances.

Please visit the GitHub Wiki for detailed usage of each module.

| module           | version                                                                                           | Github Page                                                                     |
|------------------|---------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| **Mocka Core**   | ![Maven Central Version](https://img.shields.io/maven-central/v/io.github.yyytir777/mocka-core)   | [**Core Github Page**](https://github.com/yyytir777/Mocka/wiki/Mocka-Core)      |
| **Mocka Spring** | ![Maven Central Version](https://img.shields.io/maven-central/v/io.github.yyytir777/mocka-spring) | [**Spring Github Page**](https://github.com/yyytir777/Mocka/wiki/Mocka-Spring)  |


## Key Features
- **ORM Entity Auto Generation** : Easily instantiate ORM-managed entities without manually assigning fields.
- **Multi-ORM Support** : Automatically detects ORM frameworks (Hibernate, MyBatis) and scans entity classes to generate appropriate instances.
- **Random Data Generation** : Supports basic types such as String, Number, and Date, as well as Email, Lorem Ipsum, Name, IP Address, and more.
- **Regex Pattern Generation** : Supports generating strings that match the given regular expression patterns.
- **Entity Relationship Support** : Not only generates entities but also creates associated entities recursively.
- **Extensibility** : Implement your own custom generators to support new data types seamlessly.
- **Flexibility** : Map entity fields or entire classes to specific datasets or files for tailored instance generation.
- **Test Optimization** : Automatically generate large volumes of entities to accelerate unit, integration, and data-populating tests.


## Import Library

### Gradle
```groovy
dependencies {
    implementation 'io.github.yyytir777:mocka-core:1.0.0'
    implementation 'io.github.yyytir777:mocka-spring:1.0.0'
}
```

### Maven
```xml
<dependencies>
    <dependency>
        <groupId>io.github.yyytir777</groupId>
        <artifactId>mocka-core</artifactId>
        <version>1.0.0</version>
    </dependency>
    <dependency>
        <groupId>io.github.yyytir777</groupId>
        <artifactId>mocka-spring</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## What Can You Do With It?

### 1. Generate Entity Instances
```java
Generator<Member> generator = springGenerator.getGenerator(Member.class);
Member member = generator.get();
```

### 2. Map Entity Fields to a Generator or File
```java
@Entity
public class Member {
    @ValueSource(generatorKey = "name")
    private String name;
}
```

```java
@Entity
public class Member {
    @ValueSource(path = "name.txt", type=String.class)
    private String name;
}
```

### 3. Map Entire Entities to Files
- Supports **YAML**, **XML**, **JSON**, and **CSV**
```java
@Entity
@FileSource(path = "member.csv")
public class Member {
    
}
```

### 4. Map Entity Fields to Regex Patterns

```java
@Entity
public class Member {
    @RegexSource(pattern = "[a-zA-Z]{10}")
    private String name;
}
```

### 5. Register Custom Generator based specific file to GeneratorFactory

```java
GeneratorFactory.putGenerator(new RegistrableGenerator<>("test_generator", "test.txt", String.class));
Generator<String> generator = GeneratorFactory.getGenerator("test_generator");
```


## GenerateType
When generating entity instances, you can specify the GenerateType to control how associated entities are created.

![GenerateType.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType.png)

In the diagram above, entity A and B have a 1:N relationship, and B and C also have a 1:N relationship.
Let’s use this to explain each GenerateType strategy.

---

### GenerateType.SELF
Generates only the specified entity instance.<br>
Even if relationships exist, they are initialized as null.

```java
Generator<B> generator = springGenerator.getGenerator(B.class);
B b = generator.get(GenerateType.SELF);
```
If you generate entity B with `GenerateType.SELF`, only B will be created; associated entities will not be generated.

![GenerateType SELF.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20SELF.png)

---

#### GenerateType.CHILD
Generates the specified entity and its direct child entities.

```java
Generator<B> generator = springGenerator.getGenerator(B.class);
B b = generator.get(GenerateType.CHILD);
```
If you generate entity B with `GenerateType.CHILD`, both B and its child entity C will be created.

![GenerateType CHILD.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20CHILD.png)

---

#### GenerateType.CHILDREN
Recursively generates the specified entity and all its descendant entities.

```java
Generator<A> generator = springGenerator.getGenerator(A.class);
A a = generator.get(GenerateType.CHILDREN);
```
If you generate entity A with `GenerateType.CHILDREN`, entities A, B, and C will all be created recursively.

![GenerateType CHILDREN.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20CHILDREN.png)

---

#### GenerateType.PARENT
Generates the specified entity and its direct parent entity.

```java
Generator<B> generator = springGenerator.getGenerator(B.class);
B b = generator.get(GenerateType.PARENT);
```
If you generate entity B with `GenerateType.PARENT`, both B and its parent entity A will be created.

![GenerateType PARENT.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20PARENT.png)

---

#### GenerateType.PARENTS
Recursively generates the specified entity and all its ancestor entities.

```java
Generator<C> generator = springGenerator.getGenerator(C.class);
C c = generator.get(GenerateType.PARENTS);
```
If you generate entity C with `GenerateType.PARENTS`, entities C, B, and A will all be created recursively.

![GenerateType PARENTS.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20PARENTS.png)

---

#### GenerateType.ALL
Recursively generates the specified entity and all related entities, both parents and children.

```java
Generator<B> generator = springGenerator.getGenerator(B.class);
B b = generator.get(GenerateType.ALL);
```
If you generate entity B with `GenerateType.ALL`, entities A, B, and C will all be created together.

![GenerateType ALL.png](https://github.com/yyytir777/entityinstantiator/wiki/images/GenerateType%20ALL.png)


---

## GitHub Wiki

If you want to see more information of `Mocka`, please visit [GitHub Wiki](https://github.com/yyytir777/Mocka/wiki) Pages!!