package com.revolut.resources;

import com.revolut.Scenario;
import io.restassured.path.json.JsonPath;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;


public class AccountResourceIT extends Scenario {

    @Test
    public void test_getPing() {

        given()
                .contentType("application/json")
                .when()
                .get(baseUrl + "ping")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void test_getAllAccounts(){

        given()
                .contentType("application/json")
                .when()
                .get(baseUrl + "accounts")
                .then()
                .assertThat()
                .statusCode(200)
                .body("[0].balance", equalTo(1000.0f),
                        "[1].balance", equalTo(340.0f),
                                "[2].balance", equalTo(700.0f));

    }

    @Test
    public void test_getAccount(){

        given()
                .contentType("application/json")
                .when()
                .get(baseUrl + "account" + "/177e41d3-cb1b-4426-aaea-3d3d5623ac6c")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo("177e41d3-cb1b-4426-aaea-3d3d5623ac6c"),
                "balance", equalTo(1000.0f));

    }

    @Test
    public void test_getTransfersPerAccount(){

        given()
                .contentType("application/json")
                .when()
                .get(baseUrl + "account/177e41d3-cb1b-4426-aaea-3d3d5623ac6c/transfers")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", hasSize(2),
                        "amount", hasItems(200.0f, 40.0f));

    }

    @Test
    public void test_updateAccount(){

        JsonPath response = given()
                .contentType("application/json")
                .when()
                .queryParam("balance", 2000.0f)
                .post(baseUrl + "account/update/e2a77256-09a4-4910-b646-d27614829abd")
                .then()
                .assertThat()
                .statusCode(200).extract()
                .body().jsonPath();

        String accountId = response.getString("id");

        given()
                .contentType("application/json")
                .when()
                .get(baseUrl + "account/" + accountId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(accountId),
                        "balance", equalTo(2000.0f));
    }

    @Test
    public void test_createAccount() {

        given()
                .contentType("application/json")
                .when()
                .queryParam("balance", 3000.0f)
                .post(baseUrl + "account/create")
                .then()
                .assertThat()
                .statusCode(200)
                .body("balance", equalTo(3000.0f));
    }

}
