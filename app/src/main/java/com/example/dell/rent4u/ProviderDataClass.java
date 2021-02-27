package com.example.dell.rent4u;

class ProviderDataClass {

    String Address, City, City_PinCode, Company_Name, ContactNo, Email, License, Owner_Name, Password, ProviderId;

    public ProviderDataClass(String address, String city,
                             String city_PinCode, String company_Name,
                             String contactNo, String email,
                             String license, String owner_Name, String password,
                             String providerId) {
        Address = address;
        City = city;
        City_PinCode = city_PinCode;
        Company_Name = company_Name;
        ContactNo = contactNo;
        Email = email;
        License = license;
        Owner_Name = owner_Name;
        Password = password;
        ProviderId = providerId;
    }

    public String getProviderId() {
        return ProviderId;
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

    public String getCompany_Name() {
        return Company_Name;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public String getEmail() {
        return Email;
    }

    public String getLicense() {
        return License;
    }

    public String getOwner_Name() {
        return Owner_Name;
    }

    public String getPassword() {
        return Password;
    }
}
