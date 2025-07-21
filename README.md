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

How To Use
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
    @Test
    void test() {
        // string 반환
        StringGenerator stringGenerator = GeneratorFactory.string();
        String string = stringGenerator.get();
        String string = stringGenerator.get(10);
    
    
        // email 반환
        EmailGenerator emailGenerator = GeneratorFactory.email();
        String email = emailGenerator.get();
        Pattern pattern = Pattern.compile("\\w+@\\w+\\.\\w+");
        assertThat(pattern.matcher(email).matches()).isTrue();
    
        // name 반환
        NameGenerator nameGenerator = GeneratorFactory.name();
        String name = nameGenerator.get();
        assertThat(name).isNotEmpty();
    
        // lorem ipsum 반환
        LoremIpsumGenerator loremIpsumGenerator = GeneratorFactory.loremIpsum();
        String loremIpsum = loremIpsumGenerator.get();
        assertThat(loremIpsum).isNotEmpty();
    
        // Primitive Generator 반환
        PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();
    
        Boolean bool = primitiveGenerator.getBoolean();
        assertThat(bool).isIn(Boolean.TRUE, Boolean.FALSE);
    
        Byte b = primitiveGenerator.getByte();
        assertThat(b).isBetween(Byte.MIN_VALUE, Byte.MAX_VALUE);
    
        Short s = primitiveGenerator.getShort();
        assertThat(s).isBetween(Short.MIN_VALUE, Short.MAX_VALUE);
    
        Character c = primitiveGenerator.getCharacter();
        assertThat(c).isBetween(Character.MIN_VALUE, Character.MAX_VALUE);
    
        Integer i = primitiveGenerator.getInteger();
        assertThat(i).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
    
        Long l = primitiveGenerator.getLong();
        assertThat(l).isBetween(Long.MIN_VALUE, Long.MAX_VALUE);
    
        Float f = primitiveGenerator.getFloat();
        assertThat(f).isBetween(Float.MIN_VALUE, Float.MAX_VALUE);
    
        Double d = primitiveGenerator.getDouble();
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
        
        // Registable Generator 반환
        Generator<String> generator = GeneratorFactory.getRegistableGenerator("asdf", "name.txt", String.class);
        String s1 = generator.get();
        assertThat(s1).isNotEmpty();
    }
```