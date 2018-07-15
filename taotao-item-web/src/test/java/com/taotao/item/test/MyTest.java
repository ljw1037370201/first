package com.taotao.item.test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class MyTest {
    @Test
    public void demo1() throws Exception {
        //创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        //目前只设置本地路径
        configuration.setDirectoryForTemplateLoading(new File("D:\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        configuration.setDefaultEncoding("utf-8");
        //模板对象
        Template template = configuration.getTemplate("demo1.ftl");
        Map map = new HashMap();
        map.put("hello","欢迎使用freemarker模板");
        //指定静态页面输出的到哪里
        Writer writer = new FileWriter(new File("F:\\abcd.html"));
        //第一个是数据，第二个是地址
        template.process(map,writer);
        writer.close();
    }
    @Test
    public void demo2() throws Exception {
        //创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        //目前只设置本地路径
        configuration.setDirectoryForTemplateLoading(new File("D:\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        configuration.setDefaultEncoding("utf-8");
        //模板对象
        Template template = configuration.getTemplate("demo3.ftl");
        Map map = new HashMap();
        Student student1 = new Student(1,"张三",18);
        Student student2= new Student(2,"李四",22);
        Student student3 = new Student(3,"王五",30);
        Student student4 = new Student(4,"赵六",42);
        Student student5 = new Student(5,"王麻子",41);
        List<Student> students = new ArrayList<Student>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        map.put("students",students);
        map.put("hello","xxxxxxx");//demo1的ftl页面需要赋初始值
        map.put("date",new Date());
        //指定静态页面输出的到哪里s
        Writer writer = new FileWriter(new File("F:\\abb.html"));
        //第一个是数据，第二个是地址
        template.process(map,writer);
        writer.close();
    }
}
