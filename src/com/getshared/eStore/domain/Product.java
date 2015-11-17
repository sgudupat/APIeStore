package com.getshared.eStore.domain;

import android.graphics.Bitmap;

public class Product {

    private String category;
    private String price;
    private String name;
    private String specification;
    private String image;
    private Bitmap transformedImage;
    private String link;
    private String productInfo;

    public Product(String category, String price, String name,
                   String specification, String image, String code, String productInfo) {
        super();
        this.category = category;
        this.price = price;
        this.name = name;
        this.specification = specification;
        this.image = image;
        this.code = code;
    }

    public Product(String image, String link) {
        this.image = image;
        this.link = link;
    }

    public Product(String image, String link, String productInfo, String price, String name) {

        this.image = image;
        this.link = link;
        this.productInfo = productInfo;
        this.price = price;
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;

    public Bitmap getTransformedImage() {
        return transformedImage;
    }

    public void setTransformedImage(Bitmap transformedImage) {
        this.transformedImage = transformedImage;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    @Override
    public String toString() {
        return "Product [category=" + category + ", price=" + price + ", name="
                + name + ", specification=" + specification + ", image="
                + image + ", transformedImage=" + transformedImage + ", link="
                + link + ", productInfo=" + productInfo + ", code=" + code
                + "]";
    }


}
