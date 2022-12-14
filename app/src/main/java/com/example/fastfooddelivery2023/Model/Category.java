package com.example.fastfooddelivery2023.Model;

public class Category {
    private String id_category;
    private String image_Category;
    private String name_Category;

    public Category() {
    }

    public Category(String id_category, String image_Category, String name_Category) {
        this.id_category = id_category;
        this.image_Category = image_Category;
        this.name_Category = name_Category;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getImage_Category() {
        return image_Category;
    }

    public void setImage_Category(String image_Category) {
        this.image_Category = image_Category;
    }

    public String getName_Category() {
        return name_Category;
    }

    public void setName_Category(String name_Category) {
        this.name_Category = name_Category;
    }
}
