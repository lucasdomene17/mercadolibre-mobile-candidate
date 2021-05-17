package com.mercadolibretest.testmobile.models.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Item implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("currency_id")
    @Expose
    private String currencyId;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("available_quantity")
    @Expose
    private Long availableQuantity;
    @SerializedName("sold_quantity")
    @Expose
    private Long soldQuantity;
    @SerializedName("pictures")
    @Expose
    private List<Picture> pictures = new ArrayList<>();
    @SerializedName("attributes")
    @Expose
    private List<Attribute> attributes = new ArrayList<>();
    public final static Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {

        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return (new Item[size]);
        }

    };

    protected Item(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((Double) in.readValue((Long.class.getClassLoader())));
        this.currencyId = ((String) in.readValue((String.class.getClassLoader())));
        this.permalink = ((String) in.readValue((String.class.getClassLoader())));
        this.condition = ((String) in.readValue((String.class.getClassLoader())));
        this.availableQuantity = ((Long) in.readValue((Long.class.getClassLoader())));
        this.soldQuantity = ((Long) in.readValue((Long.class.getClassLoader())));
        in.readList(this.pictures, (Picture.class.getClassLoader()));
        in.readList(this.attributes, (Attribute.class.getClassLoader()));
    }

    public Item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Long getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Long availableQuantity) {
        this.availableQuantity = availableQuantity;
    }


    public Long getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Long soldQuantity) {
        this.soldQuantity = soldQuantity;
    }


    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(price);
        dest.writeValue(currencyId);
        dest.writeValue(permalink);
        dest.writeValue(condition);
        dest.writeValue(availableQuantity);
        dest.writeValue(soldQuantity);
        dest.writeList(pictures);
        dest.writeList(attributes);
    }

    public int describeContents() {
        return 0;
    }

}