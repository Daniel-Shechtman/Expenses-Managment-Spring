package com.ilandaniel.project.classes;

import java.util.Date;

/**
 * storing the necessary expense information
 */
public class Expense {
    private int id;
    private int categoryId;
    private String categoryName;
    private float cost;
    private String currency;
    private String info;
    private Date dateCreated;

    public Expense() {
    }

    public Expense(int categoryId, float cost, String currency, String info) {
        setCategoryId(categoryId);
        setCost(cost);
        setCurrency(currency);
        setInfo(info);
    }

    //set id for unit tests
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currencyId) {
        this.currency = currencyId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date date) {
        this.dateCreated = date;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * overriding the toString method
     *
     * @return
     */
    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", cost=" + cost +
                ", currencyId=" + currency +
                ", info='" + info + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
