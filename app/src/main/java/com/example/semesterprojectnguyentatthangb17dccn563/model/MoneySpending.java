package com.example.semesterprojectnguyentatthangb17dccn563.model;

import java.io.Serializable;

public class MoneySpending implements Serializable {
    private String key, spendMoney, spendDate,
            spendType, spendDescription, email;

    public MoneySpending() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSpendDate() {
        return spendDate;
    }

    public void setSpendDate(String spendDate) {
        this.spendDate = spendDate;
    }

    public String getSpendType() {
        return spendType;
    }

    public void setSpendType(String spendType) {
        this.spendType = spendType;
    }

    public String getSpendDescription() {
        return spendDescription;
    }

    public void setSpendDescription(String spendDescription) {
        this.spendDescription = spendDescription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpendMoney() {
        return spendMoney;
    }

    public void setSpendMoney(String spendMoney) {
        this.spendMoney = spendMoney;
    }
}
