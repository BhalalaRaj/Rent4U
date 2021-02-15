package com.example.dell.rent4u;

class VehicleDataClass {

    String frontImage;
    String sideImage;
    String interiorImage;
    String rentPrice;
    String condition;
    String owner;
    String seatingCapacity;
    String onRentStatus;
    String city;
    String vehicleName;
    String numberPlate;

    public String getVehicleType() {
        return vehicleType;
    }

    String vehicleType;

    public VehicleDataClass() {
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public VehicleDataClass(String frontImage, String sideImage,
                            String interiorImage, String rentPrice,
                            String condition, String owner,
                            String seatingCapacity, String onRentStatus,
                            String city, String vehicleName,
                            String numberPlate, String vehicleType) {
        this.frontImage = frontImage;
        this.sideImage = sideImage;
        this.interiorImage = interiorImage;
        this.rentPrice = rentPrice;
        this.condition = condition;
        this.owner = owner;
        this.seatingCapacity = seatingCapacity;
        this.onRentStatus = onRentStatus;
        this.city = city;
        this.vehicleName = vehicleName;
        this.numberPlate = numberPlate;
        this.vehicleType = vehicleType;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public String getSideImage() {
        return sideImage;
    }

    public String getInteriorImage() {
        return interiorImage;
    }

    public String getRentPrice() {
        return rentPrice;
    }

    public String getCondition() {
        return condition;
    }

    public String getOwner() {
        return owner;
    }

    public String getSeatingCapacity() {
        return seatingCapacity;
    }

    public String getOnRentStatus() {
        return onRentStatus;
    }

    public String getCity() {
        return city;
    }
}
