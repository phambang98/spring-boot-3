package com.example.springcore.model;

public class TradeBean {
    private String isin;
    private Integer quantity;
    private Double price;
    private String customer;

    private String xmlns;
    private String text;

    public TradeBean() {
    }

    public TradeBean(String isin, Integer quantity, Double price, String customer, String xmlns, String text) {
        this.isin = isin;
        this.quantity = quantity;
        this.price = price;
        this.customer = customer;
        this.xmlns = xmlns;
        this.text = text;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


}