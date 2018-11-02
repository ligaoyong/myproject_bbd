package com.bbd.builder;

/**
 * Created by lgy on 2018/9/17.
 */
public class Person {
    //必要参数
    private Long id;
    private String name;
    //可选参数
    private String highLength;
    private Integer age;
    private Integer sex;
    //构造器私有
    //也可以public 再把builder写成一个单独的类
    private Person(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.highLength = builder.highLength;
        this.age = builder.age;
        this.sex = builder.sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHighLength() {
        return highLength;
    }

    public void setHighLength(String highLength) {
        this.highLength = highLength;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", highLength='" + highLength + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

    //构造者类
    public static class Builder{
        //必要参数
        private Long id;
        private String name;
        //可选参数
        private String highLength;
        private Integer age;
        private Integer sex;

        public Builder(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder highLength(String highLength){
            this.highLength = highLength;
            return this;
        }
        public Builder age(Integer age){
            this.age = age;
            return this;
        }
        public Builder sex(Integer sex){
            this.sex = sex;
            return this;
        }

        //构造者的构造方法
        public Person build(){
            return new Person(this);
        }
    }
}
