package blackGold;


import com.jayway.restassured.RestAssured;
import org.hamcrest.Matchers;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;


public class RestAssuredAnswers3 {

    static  String  baseUrl="http://localhost:8080";

    @BeforeClass
    public  void initpath(){
        RestAssured.baseURI="http://localhost:8080";
    }


    /*******************************************************
     * Request an authentication token API and write the
     * response to the console. Use preemptive Basic authentication:
     * username = oauth
     * password = gimmeatoken
     * Use /v1/oauth2/token
     ******************************************************/

    @BeforeClass
    public void retrieveOAuthToken() {
         given().
                 auth().
                 preemptive().
                 basic("oauth","gimmeatoken").
         when().
                 get("/v1/oauth2/token").
          then().
                 log().
                 body();

    }

    /*******************************************************
     * Request a list of payments for this account and check
     * that the number of payments made equals 4.
     * Use OAuth2 authentication with the previously retrieved
     * authentication token.
     * Use /v1/payments/payment/
     * Value to be retrieved is in the paymentsCount field
     ******************************************************/

    @Test
    public void checkNumberOfPayments() {
        given().
                auth().
                oauth2("A101.Z9Ld87BSuMFSxUxGiUL3FCabpcnr-yURg2S7HYngOc6104_4c0-RIC3CAqyrCjAD.ndfJOqSUk6dDNbGirW7EHU0mtZy").
         when().
                get("/v1/payments/payment/").
         then().
                assertThat().
                body("paymentsCount", Matchers.equalTo(4));

    }

    /*******************************************************
     * Request the list of all circuits that hosted a
     * Formula 1 race in 2014 and check that this request is
     * answered within 100 ms
     * Use /api/f1/2014/circuits.json
     ******************************************************/

    @Test
    public void checkResponseTimeFor2014CircuitList() {

        given().
          when().
                get("/api/f1/2014/circuits.json").
        then().
                assertThat().
                time(lessThan(100L), TimeUnit.MILLISECONDS);
    }



}