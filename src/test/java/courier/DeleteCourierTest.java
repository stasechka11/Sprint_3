package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.BaseApiClient;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.courier.Courier;
import ru.yandex.practicum.scooter.api.model.courier.CourierCredentials;
import ru.yandex.practicum.scooter.api.model.GeneralApiResponse;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.scooter.api.BaseApiClient.BASE_URL;
import static ru.yandex.practicum.scooter.api.CourierClient.BASE_PATH_COURIER;
import static ru.yandex.practicum.scooter.api.model.courier.Courier.getRandomCourier;

public class DeleteCourierTest {
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

    @Test
    @DisplayName("Check delete courier api")
    public void deleteCourierTest(){
        courierClient.createCourier(courier);
        courierId = courierClient.getCourierId(courierCredentials);
        Response deleteResponse = courierClient.deleteCourier(courierId);

        //Check response status code
        assertEquals(SC_OK, deleteResponse.statusCode());
        //Check response body
        GeneralApiResponse deleteCourierResponse = deleteResponse.as(GeneralApiResponse.class);
        assertTrue(deleteCourierResponse.getOk());
    }

   // @Test
   // @DisplayName("Check delete courier api with 0 id")
    public void deleteCourier0idTest(){
        Response deleteResponse = courierClient.deleteCourier(0);
        //Check response status code
        assertEquals(SC_NOT_FOUND, deleteResponse.statusCode());
        //Check response body
        GeneralApiResponse deleteCourierResponse = deleteResponse.as(GeneralApiResponse.class);
        assertEquals(CourierClient.DELETE_COURIER_NOT_FOUND_MESSAGE, deleteCourierResponse.getMessage());
    }

    //@Test
    //@DisplayName("Check delete courier api with not existing id")
    public void deleteCourierNotExistingIdTest(){
        Response deleteResponse = courierClient.deleteCourier(RandomUtils.nextInt());
        //Check response status code
        assertEquals(SC_NOT_FOUND, deleteResponse.statusCode());
        //Check response body
        GeneralApiResponse deleteCourierResponse = deleteResponse.as(GeneralApiResponse.class);
        assertEquals(CourierClient.DELETE_COURIER_NOT_FOUND_MESSAGE, deleteCourierResponse.getMessage());
    }

   // @Test
    //@DisplayName("Check delete courier api without id")
    public void deleteCourierWithoutIDTest(){
        Response deleteResponse = given()
                .spec(BaseApiClient.getReqSpec())
                .when()
                .log().all()
                .delete(BASE_URL + BASE_PATH_COURIER);

        //Check response status code
        assertEquals(SC_BAD_REQUEST, deleteResponse.statusCode());
        //Check response body
        GeneralApiResponse deleteCourierResponse = deleteResponse.as(GeneralApiResponse.class);
        assertEquals(CourierClient.DELETE_COURIER_NOT_ENOUGH_DATA_MESSAGE, deleteCourierResponse.getMessage());
    }
}
