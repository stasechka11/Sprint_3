package ru.yandex.practicum.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.courier.Courier;
import ru.yandex.practicum.scooter.api.model.courier.CourierCredentials;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierClient extends BaseApiClient {
    public static final String BASE_PATH_COURIER = "/api/v1/courier/";
    //create courier messages
    public static final String CREATE_COURIER_NOT_ENOUGH_DATA_MESSAGE = "Недостаточно данных для создания учетной записи";
    public static final String COURIER_EXIST_MESSAGE = "Этот логин уже используется";
    //login courier messages
    public static final String LOGIN_COURIER_NOT_EXIST_MESSAGE = "Учетная запись не найдена";
    public static final String LOGIN_COURIER_NOT_ENOUGH_DATA_MESSAGE = "Недостаточно данных для входа";

    @Step("Create courier {courier}")
    public Response createCourier(Courier courier) {
        return given()
                .spec(getReqSpec())
                .body(courier)
                .when()
                .log().all()
                .post(BASE_URL + BASE_PATH_COURIER);
    }

    @Step("Login with created courier {courierCredentials}")
    public Response login(CourierCredentials courierCredentials) {
        return given()
                .spec(getReqSpec())
                .body(courierCredentials)
                .when()
                .log().all()
                .post(BASE_URL + BASE_PATH_COURIER + "login");
    }

    @Step("Get courier id")
    public int getCourierId(CourierCredentials courierCredentials) {
        return given()
                .spec(getReqSpec())
                .body(courierCredentials)
                .when()
                .log().all()
                .post(BASE_URL + BASE_PATH_COURIER + "login")
                .jsonPath().getInt("id");
    }

    @Step("Delete courier with id - {courierId}")
    public Boolean deleteCourier(int courierId) {
        return given()
                .spec(getReqSpec())
                .when()
                .log().all()
                .delete(BASE_URL + BASE_PATH_COURIER + courierId)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("ok");
    }

}
