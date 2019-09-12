package pojo;

import java.util.Objects;

public class Student {
   private int id;
   private String name;
   private String secondName;
   private String group;

    public Student() { }

    public Student(String name, String secondName, String group) {
        this.name = name;
        this.secondName = secondName;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                Objects.equals(name, student.name) &&
                Objects.equals(secondName, student.secondName) &&
                Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, secondName, group);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "pojo.Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
