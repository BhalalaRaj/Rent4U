package com.example.dell.rent4u;

class VehicleHistoryDataClass {

    private String
            BookingDate,
            Destination,
            ProviderUid,
            ReturnDate,
            Source,
            UserAddress,
            UserContactNo,
            UserName,
            UserUId,
            VehicleId,
            VehicleImage,
            VehicleName,
            VehicleNumberPlate;


    public VehicleHistoryDataClass() {
    }

    public VehicleHistoryDataClass(String bookingDate, String destination,
                                   String providerUid, String returnDate,
                                   String source, String userAddress,
                                   String userContactNo, String userName,
                                   String userUId, String vehicleId, String vehicleImage, String vehicleName, String vehicleNumberPlate) {
        BookingDate = bookingDate;
        Destination = destination;
        ProviderUid = providerUid;
        ReturnDate = returnDate;
        Source = source;
        UserAddress = userAddress;
        UserContactNo = userContactNo;
        UserName = userName;
        UserUId = userUId;
        VehicleId = vehicleId;
        VehicleImage = vehicleImage;
        VehicleName = vehicleName;
        VehicleNumberPlate = vehicleNumberPlate;
    }


    public String getBookingDate() {
        return BookingDate;
    }

    public String getDestination() {
        return Destination;
    }

    public String getProviderUid() {
        return ProviderUid;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public String getSource() {
        return Source;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public String getUserContactNo() {
        return UserContactNo;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserUId() {
        return UserUId;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    public String getVehicleImage() {
        return VehicleImage;
    }

    public String getVehicleName() {
        return VehicleName;
    }

    public String getVehicleNumberPlate() {
        return VehicleNumberPlate;
    }
}
