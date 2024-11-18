package tests;

import io.qameta.allure.Owner;
import models.LoginRequestModel;
import models.LoginResponseModel;
import models.ResponseErrorModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static specs.RequestSpec.requestSpec;
import static specs.ResponseSpec.response200Spec;
import static specs.ResponseSpec.response400Spec;

@Owner("Denis Gubert")
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Запрос с некорректными данными авторизации")
    void userNotFoundTest() {
        LoginRequestModel requestModel = new LoginRequestModel();
        requestModel.setUsername("username");
        requestModel.setEmail("email@email.com");
        requestModel.setPassword("password");

        ResponseErrorModel responseModel = step("Делаем запрос", ()-> given()
                .spec(requestSpec)
                .body(requestModel)
            .when()
                .post("/login")
            .then()
                .spec(response400Spec)
                .extract().as(ResponseErrorModel.class));

        step("Проверяем ответ", ()->
            assertThat(responseModel.getError(), equalTo("user not found")));
    }

    @Test
    @DisplayName("Запрос без указания body")
    void missingEmailOrUsernameTest() {
        LoginRequestModel requestModel = new LoginRequestModel();

        ResponseErrorModel responseModel = step("Делаем запрос", ()-> given()
                .spec(requestSpec)
                .body(requestModel)
            .when()
                .post("/login")
            .then()
                .spec(response400Spec)
                .extract().as(ResponseErrorModel.class));

        step("Проверяем ответ", ()->
            assertThat(responseModel.getError(), equalTo("Missing email or username")));
    }

    @Test
    @DisplayName("Запрос с корректными данными")
    void successfulLoginTest() {
        LoginRequestModel requestModel = new LoginRequestModel();
        requestModel.setEmail("eve.holt@reqres.in");
        requestModel.setPassword("cityslicka");

        LoginResponseModel responseModel = step("Делаем запрос", ()-> given()
                .spec(requestSpec)
                .body(requestModel)
            .when()
                .post("/login")
            .then()
                .spec(response200Spec)
                .extract().as(LoginResponseModel.class));

        step("Проверяем ответ", ()->
            assertThat(responseModel.getToken(), notNullValue()));
    }
}
