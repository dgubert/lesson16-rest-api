package tests;

import io.qameta.allure.Owner;
import models.RegisterRequestModel;
import models.RegisterResponseErrorModel;
import models.RegisterResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static specs.RegisterSpec.*;

@Owner("Denis Gubert")
public class RegisterTest extends BaseTest {

    @Test
    @DisplayName("Регистрация неопределенного пользователя")
    void undefinedUserTest() {
        RegisterRequestModel requestModel = new RegisterRequestModel();
        requestModel.setUsername("username");
        requestModel.setEmail("email@email.com");
        requestModel.setPassword("password");

        RegisterResponseErrorModel responseModel = step("Делаем запрос", ()-> given()
                .spec(registerRequestSpec)
                .body(requestModel)
            .when()
                .post()
            .then()
                .spec(registerResponse400Spec)
                .extract().as(RegisterResponseErrorModel.class));

        step("Проверяем ответ", ()->
            assertThat(responseModel.getError(), equalTo("Note: Only defined users succeed registration")));
    }

    @Test
    @DisplayName("Регистрация неопределенного пользователя")
    void undefinedUserTest1() {
        RegisterRequestModel requestModel = new RegisterRequestModel();
        requestModel.setEmail("eve.holt@reqres.in");
        requestModel.setPassword("cityslicka");

        RegisterResponseModel responseModel = step("Делаем запрос", ()-> given()
                .spec(registerRequestSpec)
                .body(requestModel)
            .when()
                .post()
            .then()
                .spec(registerResponse200Spec)
                .extract().as(RegisterResponseModel.class));

        step("Проверяем ответ", ()-> {
            assertThat(responseModel.getId(), equalTo("4"));
            assertThat(responseModel.getToken(), equalTo("QpwL5tke4Pnpja7X4"));});
    }
}
