package tests;

import io.qameta.allure.Owner;
import models.LoginRequestModel;
import models.RegisterResponseModel;
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
public class RegisterTest extends BaseTest {

    @Test
    @DisplayName("Регистрация неопределенного пользователя")
    void undefinedUserTest() {
        LoginRequestModel requestModel = new LoginRequestModel();
        requestModel.setUsername("username");
        requestModel.setEmail("email@email.com");
        requestModel.setPassword("password");

        ResponseErrorModel responseModel = step("Делаем запрос", ()-> given()
                .spec(requestSpec)
                .body(requestModel)
            .when()
                .post("/register")
            .then()
                .spec(response400Spec)
                .extract().as(ResponseErrorModel.class));

        step("Проверяем ответ", ()->
            assertThat(responseModel.getError(), equalTo("Note: Only defined users succeed registration")));
    }

    @Test
    @DisplayName("Регистрация неопределенного пользователя")
    void undefinedUserTest1() {
        LoginRequestModel requestModel = new LoginRequestModel();
        requestModel.setEmail("eve.holt@reqres.in");
        requestModel.setPassword("cityslicka");

        RegisterResponseModel responseModel = step("Делаем запрос", ()-> given()
                .spec(requestSpec)
                .body(requestModel)
            .when()
                .post("/register")
            .then()
                .spec(response200Spec)
                .extract().as(RegisterResponseModel.class));

        step("Проверяем ответ", ()-> {
            assertThat(responseModel.getId(), equalTo("4"));
            assertThat(responseModel.getToken(), notNullValue());});
    }
}
