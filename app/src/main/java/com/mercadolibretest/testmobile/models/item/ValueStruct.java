package com.mercadolibretest.testmobile.models.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValueStruct implements Parcelable {

    @SerializedName("number")
    @Expose
    private Double number;
    @SerializedName("unit")
    @Expose
    private String unit;
    public final static Parcelable.Creator<ValueStruct> CREATOR = new Creator<ValueStruct>() {


        public ValueStruct createFromParcel(Parcel in) {
            return new ValueStruct(in);
        }

        public ValueStruct[] newArray(int size) {
            return (new ValueStruct[size]);
        }

    };

    protected ValueStruct(Parcel in) {
        this.number = ((Double) in.readValue((Long.class.getClassLoader())));
        this.unit = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ValueStruct() {
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(number);
        dest.writeValue(unit);
    }

    public int describeContents() {
        return 0;
    }

}