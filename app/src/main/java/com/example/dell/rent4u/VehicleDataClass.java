package com.example.dell.rent4u;

import android.os.Parcel;
import android.os.Parcelable;

class VehicleDataClass implements Parcelable {

    private String City;
    private String Condition;
    private String Front;
    private String Interior;
    private String ModelName;
    private String NumberPlate;
    private String OnRentStatus;
    private String Owner;
    private String Rent;
    private String Seating;
    private String Side;
    private String VehicleId;
    private String VehicleType;

    protected VehicleDataClass(Parcel in) {
        City = in.readString();
        Condition = in.readString();
        Front = in.readString();
        Interior = in.readString();
        ModelName = in.readString();
        NumberPlate = in.readString();
        OnRentStatus = in.readString();
        Owner = in.readString();
        Rent = in.readString();
        Seating = in.readString();
        Side = in.readString();
        VehicleId = in.readString();
        VehicleType = in.readString();
    }

    public static final Creator<VehicleDataClass> CREATOR = new Creator<VehicleDataClass>() {
        @Override
        public VehicleDataClass createFromParcel(Parcel in) {
            return new VehicleDataClass(in);
        }

        @Override
        public VehicleDataClass[] newArray(int size) {
            return new VehicleDataClass[size];
        }
    };

    public String getVehicleType() {
        return VehicleType;
    }

    public VehicleDataClass() {
    }

    public String getFront() {
        return Front;
    }

    public String getSide() {
        return Side;
    }

    public String getInterior() {
        return Interior;
    }

    public String getRent() {
        return Rent;
    }

    public String getCondition() {
        return Condition;
    }

    public String getOwner() {
        return Owner;
    }

    public String getSeating() {
        return Seating;
    }

    public String getOnRentStatus() {
        return OnRentStatus;
    }

    public String getCity() {
        return City;
    }

    public String getModelName() {
        return ModelName;
    }

    public String getNumberPlate() {
        return NumberPlate;
    }

    public VehicleDataClass(String city, String condition,
                            String front, String interior,
                            String modelName, String numberPlate,
                            String onRentStatus, String owner,
                            String rent, String seating,
                            String side, String vehicleType,
                            String vehicleId) {
        City = city;
        Condition = condition;
        Front = front;
        Interior = interior;
        ModelName = modelName;
        NumberPlate = numberPlate;
        OnRentStatus = onRentStatus;
        Owner = owner;
        Rent = rent;
        Seating = seating;
        Side = side;
        VehicleId = vehicleId;
        VehicleType = vehicleType;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(City);
        dest.writeString(Condition);
        dest.writeString(Front);
        dest.writeString(Interior);
        dest.writeString(ModelName);
        dest.writeString(NumberPlate);
        dest.writeString(OnRentStatus);
        dest.writeString(Owner);
        dest.writeString(Rent);
        dest.writeString(Seating);
        dest.writeString(Side);
        dest.writeString(VehicleId);
        dest.writeString(VehicleType);
    }
}
