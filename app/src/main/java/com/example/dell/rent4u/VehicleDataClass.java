package com.example.dell.rent4u;

class VehicleDataClass {

    private String Front;
    private String Side;
    private String Interior;
    private String Rent;
    private String Condition;
    private String Owner;
    private String Seating;
    private String OnRentStatus;
    private String City;
    private String ModelName;
    private String NumberPlate;
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

    public VehicleDataClass(String front, String side,
                            String interior, String rent,
                            String condition, String owner,
                            String seating, String onRentStatus,
                            String city, String modelName, String numberPlate, String vehicleType) {
        Front = front;
        Side = side;
        Interior = interior;
        Rent = rent;
        Condition = condition;
        Owner = owner;
        Seating = seating;
        this.OnRentStatus = onRentStatus;
        City = city;
        ModelName = modelName;
        NumberPlate = numberPlate;
        this.VehicleType = vehicleType;
    }
}
