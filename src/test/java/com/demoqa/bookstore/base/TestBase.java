package com.demoqa.bookstore.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.Config;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    static void startUp() {
        RestAssured.baseURI = "https://demoqa.com";
    }
}
