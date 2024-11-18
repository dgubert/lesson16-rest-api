package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;

public class ResponseSpec {
    public static ResponseSpecification response400Spec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(ALL)
            .build();

    public static ResponseSpecification response200Spec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();
}
