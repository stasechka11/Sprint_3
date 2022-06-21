package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.courier.Courier;
import ru.yandex.practicum.scooter.api.model.GeneralApiResponse;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateCourierParametrizedTest {
    Courier courier;
    CourierClient courierClient;

    @Before
    public void init() {
        courierClient = new CourierClient();
    }

    public CreateCourierParametrizedTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getCourierData() {
        return new Object[][]{
                {new Courier("", "", "")},
                {new Courier("", RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10))},
                {new Courier(RandomStringUtils.randomAlphabetic(10), "", RandomStringUtils.randomAlphabetic(10))},
               // {new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), "")}
        };
    }

    @Test
    @DisplayName("Check create courier without required fields")
    public void createCourierNotAllDataTest() {
        Response responseCreate = courierClient.createCourier(courier);
        //Check response status code
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());

        //Check response body
        GeneralApiResponse courierResponse = responseCreate.as(GeneralApiResponse.class);
        assertEquals(CourierClient.CREATE_COURIER_NOT_ENOUGH_DATA_MESSAGE, courierResponse.getMessage());
    }
}
