package blackGold;



import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.testng.Assert.assertEquals;


public class RestAssuredAnswers1 {

    static  String  baseUrl="http://localhost:8080";


    public void initPath(){
        RestAssured.baseURI = baseUrl;
    }


    /*******************************************************
     * Send a GET request to /api/f1/2016/drivers.json
     * and check that the answer has HTTP status code 200
     ******************************************************/

    @Test
    public void checkResponseCodeForCorrectRequest() {


        Response response=
           given().
           when().
                 get("/api/f1/2016/drivers.json");


      //  response.getBody().prettyPrint();

        response.then().statusCode(200);


        }

    /*******************************************************
     * Send a GET request to /api/f1/incorrect.json
     * and check that the answer has HTTP status code 500
     ******************************************************/

    @Test
    public void checkResponseCodeForIncorrectRequest() {
      Response response=
       given().
       when().
               get("/api/f1/incorrect.json");
       response.then().statusCode(500);

    }

    /*******************************************************
     * Send a GET request to /api/f1/2016/drivers.json
     * and check that the response is in JSON format
     ******************************************************/

    @Test
    public void checkResponseContentTypeJson() {
        Response response=
        given().
         when().
                 get("/api/f1/2016/drivers.json");

        response.then().contentType("application/json");

        }

    /***********************************************
     * Retrieve circuit information for the first race
     * of the 2014 season and check the circuitId equals
     * albert_park
     * Use /api/f1/2014/1/circuits.json
     **********************************************/

    @Test
    public void checkTheFirstRaceOf2014WasAtAlbertPark() {
        Response response=
        given().
        when().
                get();
        response.then().body("MRData.CircuitTable.Circuits.circuitId[0]",equalTo("albert_park"));
    }

    /***********************************************
     * Retrieve the list of circuits for the 2014
     * season and check that it contains silverstone
     * Use /api/f1/2014/circuits.json
     **********************************************/

    @Test
    public void checkThereWasARaceAtSilverstoneIn2014() {
       given().
       when().
               get("/api/f1/2014/circuits.json").
        then().
               assertThat().
               body("MRData.CircuitTable.Circuits.circuitId",hasItem("silverstone"));
    }

    /***********************************************
     * Retrieve the list of circuits for the 2014
     * season and check that it does not contain
     * nurburgring
     * USe /api/f1/2014/circuits.json
     **********************************************/

    @Test
    public void checkThereWasNoRaceAtNurburgringIn2014() {

       given().
       when().
               get("/api/f1/2014/circuits.json").
        then().
               assertThat().
               body("MRData.CircuitTable.Circuits.circuitI",not(hasItem("nurburgring")));

    }



}