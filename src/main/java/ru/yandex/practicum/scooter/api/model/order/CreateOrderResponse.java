package ru.yandex.practicum.scooter.api.model.order;

public class CreateOrderResponse {
    private int track;

    public CreateOrderResponse(int track) {
        this.track = track;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }
}
