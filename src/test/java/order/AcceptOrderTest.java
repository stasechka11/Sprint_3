package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.BaseApiClient;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.OrdersClient;
import ru.yandex.practicum.scooter.api.model.GeneralApiResponse;
import ru.yandex.practicum.scooter.api.model.courier.Courier;
import ru.yandex.practicum.scooter.api.model.courier.CourierCredentials;
import ru.yandex.practicum.scooter.api.model.order.Order;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.scooter.api.BaseApiClient.BASE_URL;
import static ru.yandex.practicum.scooter.api.OrdersClient.ACCEPT_ORDER_NOT_ENOUGH_DATA_MESSAGE;
import static ru.yandex.practicum.scooter.api.OrdersClient.BASE_PATH_ORDERS;
import static ru.yandex.practicum.scooter.api.model.courier.Courier.getRandomCourier;
import static ru.yandex.practicum.scooter.api.model.order.Order.getRandomOrder;

public class AcceptOrderTest {
    int courierId;
    Courier courier;
    CourierClient courierClient;
    CourierCredentials courierCredentials;

    Order order;
    OrdersClient ordersClient;
    int orderTrack;
    int orderId;

    @Before
    public void init() {
        courier = getRandomCourier();
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        courierClient = new CourierClient();

        ordersClient = new OrdersClient();
    }

    @After
    public void clear() {
        if (courierId != 0)
        courierClient.deleteCourierAfterTest(courierId);
    }

    @Test
    @DisplayName("Check accept order api")
    public void acceptOrderTest(){
        courierClient.createCourier(courier);
        courierId = courierClient.getCourierId(courierCredentials);

        order = getRandomOrder();
        Response responseCreate = ordersClient.createOrder(order);
        orderTrack = ordersClient.getOrderTrack(responseCreate);
        orderId = ordersClient.getOrderInfo(orderTrack).getId();

        //Check response code
        Response responseAccept = ordersClient.acceptOrder(orderId, courierId);
        assertEquals(SC_OK, responseAccept.statusCode());

        GeneralApiResponse acceptOrderResponse = responseAccept.as(GeneralApiResponse.class);
        //Check response body
        assertTrue(acceptOrderResponse.getOk());
    }

    @Test
    @DisplayName("Check accept api without order and courier id")
    public void acceptOrderWithoutIdsTest(){
        Response responseAccept = given()
                .spec(BaseApiClient.getReqSpec())
                .when()
                .log().all()
                .put(BASE_URL + BASE_PATH_ORDERS + "accept/");

        assertEquals(SC_BAD_REQUEST, responseAccept.statusCode());

        GeneralApiResponse acceptOrderResponse = responseAccept.as(GeneralApiResponse.class);
        //Check response body
        System.out.println(acceptOrderResponse.getMessage());
        assertEquals(ACCEPT_ORDER_NOT_ENOUGH_DATA_MESSAGE, acceptOrderResponse.getMessage());
    }
}
