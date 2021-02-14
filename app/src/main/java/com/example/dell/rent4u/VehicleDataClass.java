package com.example.dell.rent4u;

class VehicleDataClass {

    String frontImage, sideImage, interiorImage, rentPrice, condition, owner, seatingCapacity, onRentStatus, city;

    public VehicleDataClass(String frontImage, String sideImage,
                            String interiorImage, String rentPrice,
                            String condition, String owner,
                            String seatingCapacity, String onRentStatus,
                            String city
    ) {
        this.frontImage = frontImage;
        this.sideImage = sideImage;
        this.interiorImage = interiorImage;
        this.rentPrice = rentPrice;
        this.condition = condition;
        this.owner = owner;
        this.seatingCapacity = seatingCapacity;
        this.onRentStatus = onRentStatus;
        this.city = city;
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
