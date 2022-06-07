package ru.yandex.practicum.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.Courier;
import ru.yandex.practicum.scooter.api.model.CourierCredentials;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierClient extends BaseApiClient {
    //create courier messages
    public static final String createCourierNotEnoughDataMessage = "Недостаточно данных для создания учетной записи";
    public static final String courierExistMessage = "Этот логин уже используется";
    //login courier messages
    public static final String loginCourierNotExistMessage = "Учетная запись не найдена";
    public static final String loginCourierNotEnoughDataMessage = "Недостаточно данных для входа";

    @Step("Create courier {courier}")
    public Response createCourier(Courier courier) {
        return given()
                .spec(getReqSpec())
                .body(courier)
                .when()
                .log().all()
                .post(BASE_URL + "/api/v1/courier/");
    }

    @Step("Login with created courier {courierCredentials}")
    public Response login(CourierCredentials courierCredentials) {
        return given()
                .spec(getReqSpec())
                .body(courierCredentials)
                .when()
                .log().all()
                .post(BASE_URL + "/api/v1/courier/login");
    }

    @Step("Get courier id")
    public int getCourierId(CourierCredentials courierCredentials){
        return given()
                .spec(getReqSpec())
                .body(courierCredentials)
                .when()
                .log().all()
                .post(BASE_URL + "/api/v1/courier/login")
                .jsonPath().getInt("id");
    }

    @Step("Delete courier with id - {courierId}")
    public Boolean deleteCourier(int courierId) {
        return given()
                .spec(getReqSpec())
                .when()
                .log().all()
                .delete(BASE_URL + "/api/v1/courier/" + courierId)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("ok");
    }

}
