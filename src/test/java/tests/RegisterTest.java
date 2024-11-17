package tests;

import io.qameta.allure.Owner;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@Owner("Denis Gubert")
public class RegisterTest extends BaseTest {

    String registerUrl = "register";

    @Test
    @DisplayName("Регистрация неопределенного пользователя")
    void undefinedUserTest() {
        given()
                    .body("{\n" +
                        "  \"username\": \"string\",\n" +
                        "  \"email\": \"string@mail.ru\",\n" +
                        "  \"password\": \"string\"\n" +
                        "}")
                    .contentType(ContentType.JSON)
                .when()
                    .post(registerUrl)
                .then()
                    .log().body()
                    .statusCode(400)
                    .body("error", is("Note: Only defined users succeed registration"));
    }
}
