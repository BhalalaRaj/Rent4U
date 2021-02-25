package com.example.dell.rent4u;

class UserDataClass {

    private String Address, City, City_PinCode, Email, License, Mobile_no, Name, Password;

    public UserDataClass(String address, String city,
                         String city_PinCode, String email,
                         String license, String mobile_no,
                         String name, String password) {
        Address = address;
        City = city;
        City_PinCode = city_PinCode;
        Email = email;
        License = license;
        Mobile_no = mobile_no;
        Name = name;
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public String getCity() {
        return City;
    }

    public String getCity_PinCode() {
        return City_PinCode;
    }

    public String getEmail() {
        return Email;
    }

    public String getLicense() {
        return License;
    }

    public String getMobile_no() {
        return Mobile_no;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }
}
