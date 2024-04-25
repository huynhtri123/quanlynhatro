package com.example.quanlynhatro.Entity;

public class Tenant {
    private Account account;
    private String name;
    private String thumUrl;
    private String phone;
    private String address;
    private String age;
    private String gender;

    public Tenant(Account account, String name, String thumUrl, String phone, String address, String age, String gender) {
        this.account = account;
        this.name = name;
        this.thumUrl = thumUrl;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "account=" + account +
                ", name='" + name + '\'' +
                ", thumUrl='" + thumUrl + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumUrl() {
        return thumUrl;
    }

    public void setThumUrl(String thumUrl) {
        this.thumUrl = thumUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
