package blackGold;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

import static com.jayway.restassured.path.xml.XmlPath.from;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.OrderingComparison.greaterThan;


public class LocalHostTest {

    static  String  baseUrl="http://localhost:8088";
    @Before
    public void setUp() throws Exception {


       RestAssured.baseURI = "http://localhost:8088";

     //   RestAssured.port = 443;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @After
    public void tearDown() throws Exception {
    }


    //错误
    @Test
    public  void  testMatchJson(){


       


/*      JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze()).freeze();
        String url= this.getClass().getClassLoader().getResource("match-schema.json").getPath();*/

     ValidatableResponse response=   given()
                .param("key", "BAA464D3B432D062BEA99BA753214681")
                .when()
                .get("/GetHeroes/v0001/")
                .then();
    String json = response.toString();
/*
    expect().statusCode(200).given().auth().preemptive().basic("", "")
                .headers("Accept", "application/JSON").when().get("https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=BAA464D3B432D062BEA99BA753214681")
                .then().assertThat().body(matchesJsonSchemaInClasspath("match-schema.json"));*/

        assertThat(json, matchesJsonSchemaInClasspath("match-schema.json"));
    }
    @Test
    public  void testShoppingXML(){
     //   第一种方法
        ValidatableResponse response=given()
                .when()
                        .get("/test/Shopping.xml")
                .then()
                .body("shopping.category.find { it.@type == 'groceries' }.item",hasItems("Chocolate", "Coffee"));


   //   分割为2步
        String  response1  = get("/test/Shopping.xml").asString();
        List<String> groceries = from(response1).getList("shopping.category.find { it.@type == 'groceries' }.item");

       //第三种方法
        get("/test/Shopping.xml").then().body("**.find{it.@type == 'groceries'}",hasItems("Chocolate", "Coffee"));
   }

    @Test
    public  void testStoreJson(){
        /*
        "book":[
        {
            "author":"Nigel Rees",
            "category":"reference",
            "price":8.95,
            "title":"Sayings of the Century"
        },
        {
        ......
        },
        {
        ......
        },
        {
         ......
         }
    ]

    */
        ValidatableResponse response=given()
                .when()
                .get("/test/store.json")
                .then()
                .body("store.book.findAll { it.price<10 }.title" +
                        "",hasItems("Chocolate", "Coffee"));


       String  responseJson = given().when().get("/test/store.json").asString();
       List<String> bookTitles = from(responseJson).getList("store.book.findAll{it.price<10}.title");


       given().when().get("/test/store.json").then()
               .body("store.book.author.collect {it.length() }.sum()",greaterThan(50));

        Response response1 =given().when().get("/test/store.json").then()
                .body("store.book",hasItem(12.99f))
                .extract().response();


    }



}