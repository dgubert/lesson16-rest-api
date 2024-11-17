package tests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Owner("Denis Gubert")
public class UsersTest extends BaseTest {

    String usersUrl = "users";

    @Test
    @DisplayName("Проверка получения списка пользователей")
    void successfulGetUsers() {
        Map<String, Object> user = new HashMap<String, Object>();
        user.put("id", 2);
        user.put("email", "janet.weaver@reqres.in");
        user.put("first_name", "Janet");
        user.put("last_name", "Weaver");
        user.put("avatar", "https://reqres.in/img/faces/2-image.jpg");

        given()
                .when()
                    .get(usersUrl)
                .then()
                    .log().body()
                    .statusCode(200)
                    .body("total", is(12))
                    .body("total_pages", is(2))
                    .body("data", hasItem(user));
    }
}
