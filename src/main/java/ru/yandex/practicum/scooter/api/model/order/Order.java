package ru.yandex.practicum.scooter.api.model.order;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getRandomOrder(List<String> colorList){
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(10);
        String metroStation = RandomStringUtils.randomAlphabetic(10);
        String phone = RandomStringUtils.randomAlphabetic(10);
        int rentTime = RandomUtils.nextInt(1, 30);
        String comment = RandomStringUtils.randomAlphabetic(10);
        String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colorList);
    }

    @Override
    public String toString() {
        return "color=" + color;
    }
}

