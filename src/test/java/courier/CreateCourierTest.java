package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.courier.Courier;
import ru.yandex.practicum.scooter.api.model.courier.CourierCredentials;
import ru.yandex.practicum.scooter.api.model.GeneralApiResponse;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.scooter.api.model.courier.Courier.getRandomCourier;

public class CreateCourierTest {
    int courierId;
    Courier courier;
    CourierClient courierClient;
    CourierCredentials courierCredentials;

    @Before
    public void init() {
        courier = getRandomCourier();
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        courierClient = new CourierClient();
    }

    @After
    public void clear() {
        courierId = courierClient.getCourierId(courierCredentials);
        courierClient.deleteCourierAfterTest(courierId);
    }

    @Test
    @DisplayName("Check create courier with correct data /api/v1/courier")
    public void createCourierWithAllDataTest() {
        Response responseCreate = courierClient.createCourier(courier);

        //Check response status code
        assertEquals(SC_CREATED, responseCreate.statusCode());

        GeneralApiResponse courierResponse = responseCreate.as(GeneralApiResponse.class);
        //Check response body
        assertTrue(courierResponse.getOk());

        //Check login with created courier
        Response responseLogin = courierClient.login(courierCredentials);
        assertEquals(SC_OK, responseLogin.statusCode());
    }

    @Test
    @DisplayName("Check create courier with existing name")
    public void createCourierWithExistingNameTest() {
        courierClient.createCourier(courier);
        Response responseCreate = courierClient.createCourier(courier);

        //Check response status code
        assertEquals(SC_CONFLICT, responseCreate.statusCode());

        //Check response body
        GeneralApiResponse courierResponse = responseCreate.as(GeneralApiResponse.class);
        assertEquals(CourierClient.COURIER_EXIST_MESSAGE, courierResponse.getMessage());
    }
}
