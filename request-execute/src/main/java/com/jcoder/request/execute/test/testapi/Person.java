package com.jcoder.request.execute.test.testapi;

import java.util.List;

public class Person {

    private Long personId;

    private String name;

    private int age;

    private String sex;

    private String country;

    private List<String> relations;

    public Person() {
    }

    public Person(Long personId, String name, int age, String sex, String country, List<String> relations) {
        this.personId = personId;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.country = country;
        this.relations = relations;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getRelations() {
        return relations;
    }

    public void setRelations(List<String> relations) {
        this.relations = relations;
    }
}
