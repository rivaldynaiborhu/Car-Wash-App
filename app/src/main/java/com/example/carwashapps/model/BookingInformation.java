package com.example.carwashapps.model;

public class BookingInformation {
    private String customerName, customerPhone, time, workerId, workerName, carwashId, carwashName, carwashAddress ;
    private Long slot;

    public BookingInformation() {
    }

    public BookingInformation(String customerName, String customerPhone, String time, String workerId, String workerName, String carwashId, String carwashName, String carwashAddress, Long slot) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.time = time;
        this.workerId = workerId;
        this.workerName = workerName;
        this.carwashId = carwashId;
        this.carwashName = carwashName;
        this.carwashAddress = carwashAddress;
        this.slot = slot;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getCarwashId() {
        return carwashId;
    }

    public void setCarwashId(String carwashId) {
        this.carwashId = carwashId;
    }

    public String getCarwashName() {
        return carwashName;
    }

    public void setCarwashName(String carwashName) {
        this.carwashName = carwashName;
    }

    public String getCarwashAddress(String address) {
        return carwashAddress;
    }

    public void setCarwashAddress(String carwashAddress) {
        this.carwashAddress = carwashAddress;
    }

    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }
}