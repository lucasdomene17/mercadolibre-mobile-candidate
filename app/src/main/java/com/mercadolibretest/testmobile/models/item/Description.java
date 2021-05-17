package com.mercadolibretest.testmobile.models.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Description implements Parcelable {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("plain_text")
    @Expose
    private String plainText;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    public final static Parcelable.Creator<Description> CREATOR = new Creator<Description>() {


        public Description createFromParcel(Parcel in) {
            return new Description(in);
        }

        public Description[] newArray(int size) {
            return (new Description[size]);
        }

    };

    protected Description(Parcel in) {
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.plainText = ((String) in.readValue((String.class.getClassLoader())));
        this.lastUpdated = ((String) in.readValue((String.class.getClassLoader())));
        this.dateCreated = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Description() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(text);
        dest.writeValue(plainText);
        dest.writeValue(lastUpdated);
        dest.writeValue(dateCreated);
    }

    public int describeContents() {
        return 0;
    }

}