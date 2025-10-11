package entityinstantiator.entity.filesource.xml;

public class Person {
    public String name;
    public int age;

    // JAXB는 기본 생성자 필수
    public Person() {}
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
