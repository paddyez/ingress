package org.paddy.rest.client;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GeoResourceTest {

    @Test
    public void testHelloEndpoint() {
        given().when().get("/ingress/geo").then().statusCode(200).body(is(GeoResource.HELLO));
    }

}