package com.mercadolibretest.testmobile.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currency implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("decimal_places")
    @Expose
    private Long decimalPlaces;
    public final static Parcelable.Creator<Currency> CREATOR = new Creator<Currency>() {


        public Currency createFromParcel(Parcel in) {
            return new Currency(in);
        }

        public Currency[] newArray(int size) {
            return (new Currency[size]);
        }

    };

    protected Currency(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.symbol = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.decimalPlaces = ((Long) in.readValue((Long.class.getClassLoader())));
    }

    public Currency() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Currency withId(String id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Currency withSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Currency withDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(Long decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public Currency withDecimalPlaces(Long decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(symbol);
        dest.writeValue(description);
        dest.writeValue(decimalPlaces);
    }

    public int describeContents() {
        return 0;
    }

}