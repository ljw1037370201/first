package com.taotao.item.test;

public class Student {
    private int id;
    private String usrname;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", usrname='" + usrname + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public Student() {
    }

    public Student(int id, String usrname, int age) {
        this.id = id;
        this.usrname = usrname;
        this.age = age;
    }
}
