package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.scooter.api.BaseApiClient;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.OrdersClient;
import ru.yandex.practicum.scooter.api.model.GeneralApiResponse;
import ru.yandex.practicum.scooter.api.model.courier.Courier;
import ru.yandex.practicum.scooter.api.model.courier.CourierCredentials;
import ru.yandex.practicum.scooter.api.model.order.Order;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static ru.yandex.practicum.scooter.api.BaseApiClient.BASE_URL;
import static ru.yandex.practicum.scooter.api.OrdersClient.*;
import static ru.yandex.practicum.scooter.api.model.courier.Courier.getRandomCourier;
import static ru.yandex.practicum.scooter.api.model.order.Order.getRandomOrder;

@RunWith(Parameterized.class)
public class AcceptOrderParameterizedTest {
    OrdersClient ordersClient;
    String orderId;
    String courierId;
    int status_code;
    String expectedMessage;

    @Before
    public void init() {
        ordersClient = new OrdersClient();
    }

    public AcceptOrderParameterizedTest(String orderId, String courierId, int status_code, String expectedMessage) {
        this.orderId = orderId;
        this.courierId = courierId;
        this.status_code = status_code;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
               // {"", "", SC_BAD_REQUEST, ACCEPT_ORDER_NOT_ENOUGH_DATA_MESSAGE},
                //{"", RandomStringUtils.randomNumeric(5), SC_BAD_REQUEST, ACCEPT_ORDER_NOT_ENOUGH_DATA_MESSAGE},
                {RandomStringUtils.randomNumeric(5), "", SC_BAD_REQUEST, ACCEPT_ORDER_NOT_ENOUGH_DATA_MESSAGE},
                {RandomStringUtils.randomNumeric(5), RandomStringUtils.randomNumeric(5), SC_NOT_FOUND, ACCEPT_ORDER_COURIER_ID_NOT_FOUND},
                {"0", RandomStringUtils.randomNumeric(5), SC_NOT_FOUND, ACCEPT_ORDER_COURIER_ID_NOT_FOUND},
                {RandomStringUtils.randomNumeric(5), "0" , SC_NOT_FOUND, ACCEPT_ORDER_COURIER_ID_NOT_FOUND}
        };
    }

    @Test
    @DisplayName("Check accept order api with incorrect order and courier id")
    public void acceptOrderParameterizedTest() {
        Response responseAccept = given()
                .spec(BaseApiClient.getReqSpec())
                .when()
                .log().all()
                .queryParam("courierId", courierId)
                .put(BASE_URL + BASE_PATH_ORDERS + "accept/" + orderId);

        assertEquals(status_code, responseAccept.statusCode());

        GeneralApiResponse acceptOrderResponse = responseAccept.as(GeneralApiResponse.class);
        //Check response body
        assertEquals(expectedMessage, acceptOrderResponse.getMessage());
    }

}
