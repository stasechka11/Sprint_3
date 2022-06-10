package ru.yandex.practicum.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.order.CreateOrderResponse;
import ru.yandex.practicum.scooter.api.model.order.OrderInfo;
import ru.yandex.practicum.scooter.api.model.order.Order;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class OrdersClient extends BaseApiClient {
    public static final String BASE_PATH_ORDERS = "/api/v1/orders/";

    // accept order messages
    public static final String ACCEPT_ORDER_NOT_ENOUGH_DATA_MESSAGE = "Недостаточно данных для поиска";

    @Step("Create order {order}")
    public Response createOrder(Order order) {
        return given()
                .spec(getReqSpec())
                .body(order)
                .when()
                .log().all()
                .post(BASE_URL + BASE_PATH_ORDERS);
    }

    @Step("Get order info by track = {track}")
    public OrderInfo getOrderInfo(int track) {
        return given()
                .spec(getReqSpec())
                .when()
                .log().all()
                .queryParam("t", track)
                .get(BASE_URL + BASE_PATH_ORDERS + "track")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .body()
                .jsonPath()
                .getObject("order", OrderInfo.class);
    }

    @Step("Get orders list")
    public List<OrderInfo> getOrdersList() {
        return
                given()
                        .spec(getReqSpec())
                        .when()
                        .log().all()
                        .get(BASE_URL + BASE_PATH_ORDERS)
                        .then()
                        .assertThat()
                        .statusCode(SC_OK)
                        .extract()
                        .body()
                        .jsonPath()
                        .getList("orders", OrderInfo.class);
    }

    @Step("Get order track")
    public int getOrderTrack(Response response) {
        CreateOrderResponse createOrderResponse = response.as(CreateOrderResponse.class);
        return createOrderResponse.getTrack();
    }

    //Seems that cancel order api doesn't work. The response code is always 404 even for existing orders.
    @Step("Cancel order, track = {orderTrack}")
    public void cancelOrder(int orderTrack) {
        given()
                .spec(getReqSpec())
                .when()
                .log().all()
                .put(BASE_URL + BASE_PATH_ORDERS + "cancel/" + orderTrack)
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Step("Accept order id={orderId} by courier id={courierId}")
    public Response acceptOrder(int orderId, int courierId) {
        return given()
                .spec(getReqSpec())
                .when()
                .log().all()
                .queryParam("courierId", courierId)
                .put(BASE_URL + BASE_PATH_ORDERS + "accept/" + orderId);
    }

}
