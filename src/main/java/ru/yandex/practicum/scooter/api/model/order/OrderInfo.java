package ru.yandex.practicum.scooter.api.model.order;

import java.util.Date;
import java.util.List;

public class OrderInfo {
    private int id;
    private int courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private Date deliveryDate;
    private int track;
    private List<String> color;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
    private int status;

    public OrderInfo(int id, int courierId, String firstName, String lastName, String address, String metroStation, String phone, int rentTime, Date deliveryDate, int track, List<String> color, String comment, Date createdAt, Date updatedAt, int status) {
        this.id = id;
        this.courierId = courierId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.track = track;
        this.color = color;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrdersList{" +
                "id=" + id +
                ", courierId=" + courierId +
                ", track=" + track +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
