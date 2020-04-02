package com.example.isitvacantrestaurantmanager;

public class ModelReservation {
    String date,guests,hotelName,location,tableId,timeSlot,userName;
    int totalSeatPrice;

    public ModelReservation() {
    }

    public ModelReservation(String date, String guests, String hotelName, String location, String tableId, String timeSlot, String userName, int totalSeatPrice) {
        this.date = date;
        this.guests = guests;
        this.hotelName = hotelName;
        this.location = location;
        this.tableId = tableId;
        this.timeSlot = timeSlot;
        this.userName = userName;
        this.totalSeatPrice = totalSeatPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotalSeatPrice() {
        return totalSeatPrice;
    }

    public void setTotalSeatPrice(int totalSeatPrice) {
        this.totalSeatPrice = totalSeatPrice;
    }
}
