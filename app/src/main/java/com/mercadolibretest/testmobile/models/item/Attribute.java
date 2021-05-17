package com.mercadolibretest.testmobile.models.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attribute implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value_id")
    @Expose
    private String valueId;
    @SerializedName("value_name")
    @Expose
    private String valueName;
    @SerializedName("value_struct")
    @Expose
    private ValueStruct valueStruct;
    public final static Parcelable.Creator<Attribute> CREATOR = new Creator<Attribute>() {

        public Attribute createFromParcel(Parcel in) {
            return new Attribute(in);
        }

        public Attribute[] newArray(int size) {
            return (new Attribute[size]);
        }

    };

    protected Attribute(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.valueId = ((String) in.readValue((String.class.getClassLoader())));
        this.valueName = ((String) in.readValue((String.class.getClassLoader())));
        this.valueStruct = ((ValueStruct) in.readValue((ValueStruct.class.getClassLoader())));
    }

    public Attribute() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public ValueStruct getValueStruct() {
        return valueStruct;
    }

    public void setValueStruct(ValueStruct valueStruct) {
        this.valueStruct = valueStruct;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(valueId);
        dest.writeValue(valueName);
        dest.writeValue(valueStruct);
    }

    public int describeContents() {
        return 0;
    }

}