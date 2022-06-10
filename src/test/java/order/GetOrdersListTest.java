package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.OrdersClient;
import ru.yandex.practicum.scooter.api.model.order.Order;
import ru.yandex.practicum.scooter.api.model.order.OrderInfo;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static ru.yandex.practicum.scooter.api.model.order.Order.getRandomOrder;

public class GetOrdersListTest {
    Order order;
    OrdersClient ordersClient;
    static int orderTrack;

    @Before
    public void init() {
        ordersClient = new OrdersClient();
        order = getRandomOrder();
        Response createResponse = ordersClient.createOrder(order);
        orderTrack = ordersClient.getOrderTrack(createResponse);
    }

    @After
    public void clear() {
        ordersClient.cancelOrder(orderTrack);
    }

    @Test
    @DisplayName("Check orders list is displayed")
    public void getOrderListTest() {
        List<OrderInfo> ordersList = ordersClient.getOrdersList();
        assertFalse(ordersList.isEmpty());
    }
}
