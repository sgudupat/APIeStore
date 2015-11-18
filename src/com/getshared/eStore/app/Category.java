package com.getshared.eStore.app;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    String categoryName;
    String categoryUrl;
    String CategoryDescription;
    ArrayList<String> urlList;
    HashMap<String,ArrayList<String>> categoryList;

    public HashMap<String, ArrayList<String>> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(HashMap<String, ArrayList<String>> categoryList) {
		this.categoryList = categoryList;
	}

	public ArrayList<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(ArrayList<String> urlList) {
		this.urlList = urlList;
	}

	Category(String categoryName, String categoryUrl) {
        this.categoryName = categoryName;
        this.categoryUrl = categoryUrl;

    }
	Category(String categoryName,  ArrayList<String> urlList) {
        this.categoryName = categoryName;
        this.urlList = urlList;

    }
	Category(String categoryName,  HashMap<String,ArrayList<String>> categoryList) {
        this.categoryName = categoryName;
        this.categoryList = categoryList;

    }
	Category(HashMap<String,ArrayList<String>> categoryList) {
        
        this.categoryList = categoryList;

    }

    public Category() {
		// TODO Auto-generated constructor stubfdaf
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

    public String getCategoryDescription() {
        return CategoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        CategoryDescription = categoryDescription;
    }

    @Override
    public String toString() {
        return "Category [categoryName=" + categoryName + ", categoryUrl="
                + categoryUrl + ", CategoryDescription=" + CategoryDescription
                + "]";
    }


}
