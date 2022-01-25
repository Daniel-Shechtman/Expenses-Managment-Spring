package com.ilandaniel.project.dtos;

/**
 * Expense class that holds the information of add-expense method(Expense screen) of the current expense
 */
public class ExpenseDTO {
    private int categoryId;
    private String categoryName;
    private String cost;
    private String currency;
    private String info;

    public ExpenseDTO(int categoryId, String cost, String currency, String info, String categoryName) {
        setCategoryId(categoryId);
        setCost(cost);
        setCurrency(currency);
        setInfo(info);
        setCategoryName(categoryName);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currencyName) {
        currency = currencyName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
