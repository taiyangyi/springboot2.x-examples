package com.daobili.domain;

/**
 * @Author bamaw
 * @Date 2020-12-07  23:17
 * @Description
 */
public class Product {

    private String name;
    private String taste;
    private String batching;
    private String supplement;
    private String address;

    public Product() {
    }

    public Product(String name, String taste, String batching, String supplement, String address) {
        this.name = name;
        this.taste = taste;
        this.batching = batching;
        this.supplement = supplement;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getBatching() {
        return batching;
    }

    public void setBatching(String batching) {
        this.batching = batching;
    }

    public String getSupplement() {
        return supplement;
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
