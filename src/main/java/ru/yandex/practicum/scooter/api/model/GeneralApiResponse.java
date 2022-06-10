package ru.yandex.practicum.scooter.api.model;

public class GeneralApiResponse {
    private Boolean ok;
    private String message;

    public GeneralApiResponse(Boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
