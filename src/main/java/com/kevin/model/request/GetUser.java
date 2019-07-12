package com.kevin.model.request;

public class GetUser {
    private Integer age;
    private String code;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "GetUser{" +
                "age=" + age +
                ", code='" + code + '\'' +
                '}';
    }
}
