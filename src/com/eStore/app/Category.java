package com.eStore.app;

public class Category {
    String categoryName;
    String categoryUrl;

    Category(String categoryName, String categoryUrl) {
        this.categoryName = categoryName;
        this.categoryUrl = categoryUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    @Override
    public String toString() {
        return "Category [categoryName=" + categoryName + ", categoryUrl="
                + categoryUrl + "]";
    }


}
