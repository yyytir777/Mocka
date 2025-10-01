package jodag.entity.mybatis.associations;

import jodag.annotation.ValueSource;

import java.util.List;

public class Parent {

    private Long id;

    @ValueSource(generatorKey = "name")
    private String name;

    private List<Child> children;
    private GrandParent grandParent;


    public List<Child> getChildren() {
        return children;
    }

    public GrandParent getGrandParent() {
        return grandParent;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
