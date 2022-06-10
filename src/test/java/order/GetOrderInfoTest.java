package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.OrdersClient;
import ru.yandex.practicum.scooter.api.model.order.Order;
import ru.yandex.practicum.scooter.api.model.order.OrderInfo;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.yandex.practicum.scooter.api.model.order.Order.getRandomOrder;

public class GetOrderInfoTest {
    Order order;
    OrdersClient ordersClient;
    static int orderTrack;

    @Before
    public void init() {
        ordersClient = new OrdersClient();
    }

    @After
    public void clear(){
        ordersClient.cancelOrder(orderTrack);
    }

    @Test
    @DisplayName("Check get order info by track")
    public void getOrderInfoTest(){
        order = getRandomOrder();
        Response createResponse = ordersClient.createOrder(order);
        orderTrack = ordersClient.getOrderTrack(createResponse);

        OrderInfo orderInfo = ordersClient.getOrderInfo(orderTrack);
        assertNotNull(orderInfo);
    }
}
