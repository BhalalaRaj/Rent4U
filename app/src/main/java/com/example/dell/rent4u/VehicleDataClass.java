package com.example.dell.rent4u;

class VehicleDataClass {

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
    private String VehicleType;

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
                            String side, String vehicleType) {
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
        VehicleType = vehicleType;
    }
}
