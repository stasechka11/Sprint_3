package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.scooter.api.OrdersClient;
import ru.yandex.practicum.scooter.api.model.order.CreateOrderResponse;
import ru.yandex.practicum.scooter.api.model.order.Order;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static ru.yandex.practicum.scooter.api.model.order.Order.*;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {
    Order order;
    OrdersClient ordersClient;
    static int orderTrack;

    @Before
    public void init() {
        ordersClient = new OrdersClient();
    }

    @After
    public void clear(){
        //ordersClient.cancelOrder(orderTrack);
    }

    public CreateOrderParameterizedTest(Order order, int orderTrack) {
        this.order = order;
        this.orderTrack = orderTrack;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {getRandomOrder(List.of("BLACK", "GREY")), orderTrack},
                {getRandomOrder(List.of("BLACK")), orderTrack},
                {getRandomOrder(List.of("GREY")), orderTrack},
                {getRandomOrder(List.of()), orderTrack}
        };
    }

    @Test
    @DisplayName("Create order test with different colors")
    public void createOrderTest() {
        Response responseCreate = ordersClient.createOrder(order);
        //Check response status code
        assertEquals(SC_CREATED, responseCreate.statusCode());

        //Check response body
        orderTrack = ordersClient.getOrderTrack(responseCreate);
        assertNotEquals(0, orderTrack);

    }
}
