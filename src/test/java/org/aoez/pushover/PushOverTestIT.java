package org.aoez.pushover;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;


@QuarkusTest
public class PushOverTestIT {
    @Test
    public void testPushoverEndpoint() {
        PushoverService.PushoverNotification body = new PushoverService.PushoverNotification("test", "testtest", null, null, null, null,
                null, null, null, null, null, null);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/pushover")
                .then()
                .statusCode(200);
    }
}
