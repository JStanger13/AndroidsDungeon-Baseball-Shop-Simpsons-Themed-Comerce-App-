package com.nyc.justinstanger.androidsdungeon;

/**
 * Created by justinstanger on 4/5/17.
 */

public class Item {
    private String mName;
    private String mType;
    private String mDescription;
    private int mImage;
    private int mPrice;
    private long mId;
    private int mQuantity;

    //

    public Item(String name, String type, String description, int image, int price, long id, int quantity) {
        mName = name;
        mType = type;
        mDescription = description;
        mPrice = price;
        mId = id;
        mImage = image;
        mQuantity = quantity;
    }

    //getters
    public String getName() {return mName;}
    public String getType() {
        return mType;
    }
    public String getDescription() {
        return mDescription;
    }
    public int getImage() {
        return mImage;
    }
    public int getPrice() {return mPrice;}
    public long getId() {return mId;}
    public int getQuantity() {return mQuantity;}


    //setters
    public void setName(String name) {
        mName = name;
    }
    public void setType(String type) {mType = type;}
    public void setDescription(String description) {mDescription = description;}
    public void setImage(int image) {
        mImage = image;
    }
    public void setPrice(int price) {mPrice = price;}
    public void setId(int price) {mPrice = price;}
    public void setQuantity(int quantity) {mQuantity = quantity;}


}
