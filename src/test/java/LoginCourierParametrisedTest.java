import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.Courier;
import ru.yandex.practicum.scooter.api.model.CourierCredentials;
import ru.yandex.practicum.scooter.api.model.LoginCourierResponse;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static ru.yandex.practicum.scooter.api.model.Courier.getRandomCourier;

@RunWith(Parameterized.class)
public class LoginCourierParametrisedTest {
    public static Courier courier = getRandomCourier();
    CourierClient courierClient;
    CourierCredentials courierCredentials;
    CourierCredentials existingCourierCredentials;
    int status_code;
    int courierId;
    String message;

    @Before
    public void init() {
        existingCourierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        courierClient = new CourierClient();
        courierClient.createCourier(courier);
    }

    @After
    public void clear() {
        courierId = courierClient.getCourierId(existingCourierCredentials);
        courierClient.deleteCourier(courierId);
    }

    public LoginCourierParametrisedTest(CourierCredentials courierCredentials, int status_code, String message) {
        this.courierCredentials = courierCredentials;
        this.status_code = status_code;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {new CourierCredentials(courier.getLogin(), RandomStringUtils.randomAlphabetic(10)), SC_NOT_FOUND, CourierClient.loginCourierNotExistMessage},
                {new CourierCredentials(RandomStringUtils.randomAlphabetic(10), courier.getPassword()), SC_NOT_FOUND, CourierClient.loginCourierNotExistMessage},
                {new CourierCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10)), SC_NOT_FOUND, CourierClient.loginCourierNotExistMessage},
                {new CourierCredentials(courier.getLogin(), ""), SC_BAD_REQUEST, CourierClient.loginCourierNotEnoughDataMessage},
                {new CourierCredentials("", courier.getPassword()), SC_BAD_REQUEST, CourierClient.loginCourierNotEnoughDataMessage},
                {new CourierCredentials("", ""), SC_BAD_REQUEST, CourierClient.loginCourierNotEnoughDataMessage}
        };
    }

    @Test
    @DisplayName("Check login courier with not existing courier credentials and without login/password")
    public void loginCourierIncorrectCredentialsTest() {
        Response responseLogin = courierClient.login(courierCredentials);
        //Check response status code
        assertEquals(status_code, responseLogin.statusCode());
        //Check response body message
        LoginCourierResponse loginCourierResponse = responseLogin.as(LoginCourierResponse.class);
        assertEquals(message, loginCourierResponse.getMessage());
    }

}
