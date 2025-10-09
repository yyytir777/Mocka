package entityinstantiator.entity.mybatis.associations;

import entityinstantiator.annotation.ValueSource;

public class Child {

    private Long id;

    @ValueSource(generatorKey = "lorem_ipsum")
    private String name;

    private Parent parent;

    public Parent getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
