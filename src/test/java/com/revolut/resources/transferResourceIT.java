package com.revolut.resources;

import com.revolut.Scenario;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;


import static io.restassured.RestAssured.given;

public class transferResourceIT extends Scenario {

    @Test
    public void test_transferFunds() throws JSONException {

        String source = "177e41d3-cb1b-4426-aaea-3d3d5623ac6c";
        String destination = "e2a77256-09a4-4910-b646-d27614829abd";

        JSONObject requestParams = new JSONObject();
        requestParams.put("sourceAccountId", source);
        requestParams.put("destinationAccountId", destination);
        requestParams.put("amount", "100.0");

        Double initialSourceBalance = getBalance(source);
        Double initialDestinationBalance = getBalance(destination);

        given()
                .contentType("application/json")
                .when()
                .body(requestParams.toString())
                .post(baseUrl + "transfer")
                .then()
                .assertThat()
                .statusCode(200);

        Double newSourceBalance = getBalance(source);
        Double newDestinationBalance = getBalance(destination);

        assertThat(Math.abs(newSourceBalance - initialSourceBalance), Matchers.equalTo(100.0));
        assertThat(Math.abs(newDestinationBalance - initialDestinationBalance), Matchers.equalTo(100.0));

    }

    @Test
    public void test_getTransfer(){

        given()
                .contentType("application/json")
                .when()
                .get(baseUrl + "transfer/45803ac4-d9a2-44a7-ac88-ed95b51b6ec0")
                .then()
                .assertThat()
                .statusCode(200)
                .body("amount", Matchers.equalTo(200.0f),
                        "sourceAccountId", Matchers.equalTo("177e41d3-cb1b-4426-aaea-3d3d5623ac6c"),
                        "destinationAccountId", Matchers.equalTo("e2a77256-09a4-4910-b646-d27614829abd"));

    }

    public Double getBalance(String account){

        JsonPath response = given()
                .contentType("application/json")
                .when()
                .get(baseUrl + "account/" + account)
                .then()
                .assertThat()
                .statusCode(200).extract()
                .body().jsonPath();


        Double initialSourceBalance = Double.parseDouble(response.getString("balance"));
        return initialSourceBalance;
    }

}
