package com.demoqa.bookstore.tests;

import com.demoqa.bookstore.base.TestBase;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static com.demoqa.bookstore.helpers.CustomAllureListener.withCustomTemplate;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@Feature("Book API")
public class BookstoreTests extends TestBase {

    @Test
    void getBooksTest() {
        given()
                .filter(withCustomTemplate())
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().asString();
    }

    @Test
    void getBookByISBN() {
        given()
                .filter(withCustomTemplate())
                .when()
                .get("/BookStore/v1/Book?ISBN=9781449331818")
                .then()
                .statusCode(200)
                .body("isbn", is("9781449331818"))
                .body("title", is("Learning JavaScript Design Patterns"))
                .body("author", is("Addy Osmani"));
    }

    @Test
    void errorAddBook() {
        String data = "{   \"userId\": \"575a1959-4b5d-4e73-b2e0-530c93a7d931\",   " +
                "\"collectionOfIsbns\": [     {       \"isbn\": \"9781593277574\"     }   ] }";

        given()
                .filter(withCustomTemplate())
                .header("Authorization", "Basic a2F0ZToxMjM0NTZhWiE=")
                .contentType(ContentType.JSON)
                .body(data)
                .log().uri()
                .log().body()
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("code", is("1210"))
                .body("message", is("ISBN already present in the User's Collection!"));
    }
}
