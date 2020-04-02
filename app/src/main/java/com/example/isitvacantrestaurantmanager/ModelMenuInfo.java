package com.example.isitvacantrestaurantmanager;

public class ModelMenuInfo {
    String foodName,image,type;
    int quantity,totalPrice;

    public ModelMenuInfo() {
    }

    public ModelMenuInfo(String foodName, String image, String type, int quantity, int totalPrice) {
        this.foodName = foodName;
        this.image = image;
        this.type = type;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
