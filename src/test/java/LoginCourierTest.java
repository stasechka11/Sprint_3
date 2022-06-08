import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.courier.Courier;
import ru.yandex.practicum.scooter.api.model.courier.CourierCredentials;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static ru.yandex.practicum.scooter.api.model.courier.Courier.getRandomCourier;

public class LoginCourierTest {
    int courierId;
    Courier courier;
    CourierClient courierClient;
    CourierCredentials courierCredentials;

    @Before
    public void init() {
        courier = getRandomCourier();
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        courierClient = new CourierClient();
        courierClient.createCourier(courier);
    }

    @After
    public void clear() {
            courierId = courierClient.getCourierId(courierCredentials);
            courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Check login courier api with existing courier")
    public void loginCourierWithCorrectCredentialsTest() {
        Response responseLogin = courierClient.login(courierCredentials);
        //Check response status code
        assertEquals(SC_OK, responseLogin.statusCode());

        //Check id is returned
        responseLogin.then().assertThat().body("id", notNullValue());
    }
}
