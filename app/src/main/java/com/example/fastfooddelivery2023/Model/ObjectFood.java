package com.example.fastfooddelivery2023.Model;

import java.util.List;

public class ObjectFood {
    private String category;
    private String image_category;
    private List<Food> listFood;

    public ObjectFood(String category, String image_category, List<Food> listFood) {
        this.category = category;
        this.image_category = image_category;
        this.listFood = listFood;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage_category() {
        return image_category;
    }

    public void setImage_category(String image_category) {
        this.image_category = image_category;
    }

    public List<Food> getListFood() {
        return listFood;
    }

    public void setListFood(List<Food> listFood) {
        this.listFood = listFood;
    }
}
