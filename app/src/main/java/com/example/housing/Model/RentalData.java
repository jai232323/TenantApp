package com.example.housing.Model;

public class RentalData {

    private String Rent_id,P_ID,T_ID,Building_Name,Portion_Name,Tenant_Name,Start_Date,End_Date,Due_Date,Document,Rental_Type;
    private Double Rent_Amount,Deposit_Amount,Maintenace_Charge;


    public RentalData() {

    }

    public RentalData(String rent_id, String p_ID, String t_ID, String building_Name, String portion_Name, String tenant_Name, String start_Date, String end_Date, String due_Date,
                      String document, String rental_Type, Double rent_Amount, Double deposit_Amount, Double maintenace_Charge) {
        Rent_id = rent_id;
        P_ID = p_ID;
        T_ID = t_ID;
        Building_Name = building_Name;
        Portion_Name = portion_Name;
        Tenant_Name = tenant_Name;
        Start_Date = start_Date;
        End_Date = end_Date;
        Due_Date = due_Date;
        Document = document;
        Rental_Type = rental_Type;
        Rent_Amount = rent_Amount;
        Deposit_Amount = deposit_Amount;
        Maintenace_Charge = maintenace_Charge;
    }

    public String getRent_id() {
        return Rent_id;
    }

    public void setRent_id(String rent_id) {
        Rent_id = rent_id;
    }

    public String getP_ID() {
        return P_ID;
    }

    public void setP_ID(String p_ID) {
        P_ID = p_ID;
    }

    public String getT_ID() {
        return T_ID;
    }

    public void setT_ID(String t_ID) {
        T_ID = t_ID;
    }

    public String getBuilding_Name() {
        return Building_Name;
    }

    public void setBuilding_Name(String building_Name) {
        Building_Name = building_Name;
    }

    public String getPortion_Name() {
        return Portion_Name;
    }

    public void setPortion_Name(String portion_Name) {
        Portion_Name = portion_Name;
    }

    public String getTenant_Name() {
        return Tenant_Name;
    }

    public void setTenant_Name(String tenant_Name) {
        Tenant_Name = tenant_Name;
    }

    public String getStart_Date() {
        return Start_Date;
    }

    public void setStart_Date(String start_Date) {
        Start_Date = start_Date;
    }

    public String getEnd_Date() {
        return End_Date;
    }

    public void setEnd_Date(String end_Date) {
        End_Date = end_Date;
    }

    public String getDue_Date() {
        return Due_Date;
    }

    public void setDue_Date(String due_Date) {
        Due_Date = due_Date;
    }

    public String getDocument() {
        return Document;
    }

    public void setDocument(String document) {
        Document = document;
    }

    public String getRental_Type() {
        return Rental_Type;
    }

    public void setRental_Type(String rental_Type) {
        Rental_Type = rental_Type;
    }

    public Double getRent_Amount() {
        return Rent_Amount;
    }

    public void setRent_Amount(Double rent_Amount) {
        Rent_Amount = rent_Amount;
    }

    public Double getDeposit_Amount() {
        return Deposit_Amount;
    }

    public void setDeposit_Amount(Double deposit_Amount) {
        Deposit_Amount = deposit_Amount;
    }

    public Double getMaintenace_Charge() {
        return Maintenace_Charge;
    }

    public void setMaintenace_Charge(Double maintenace_Charge) {
        Maintenace_Charge = maintenace_Charge;
    }
}
