package stepdefs;



import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.annotations.BeforeClass;


import java.util.Map;

import static org.hamcrest.Matchers.*;

/**
	  And 返回值属性匹配
		  | totalItems 	 		| 1 					|
		  | kind					| books#volumes			|
* */

public class HttpMethod {

    static  String  baseUrl="http://localhost:8080";
    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    @BeforeClass
    public void initPath(){
        RestAssured.baseURI = baseUrl;
    }


    /*******************************************************
     * Send a GET request to /api/f1/2016/drivers.json
     * and check that the answer has HTTP status code 200
     ******************************************************/

    @Given("请求头不带参数")
    public  void  given_not_param() {
        request = RestAssured.given();
    }

    @Given("提交鉴权请求(.*),(.*)")
    public  void  given_set_auth(String username,String  password){
      request.auth().preemptive().basic(username,password);
    }

    @Given("使用鉴权oauth2通过验证(.*)")
    public  void  given_allow_auth2(String access_token){
        request.auth().oauth2(access_token);
    }


    @When("请求地址 (.*)")
    public void set_url(String url){
            response = request.when().get(url);
       //     System.out.println("response: " + response.prettyPrint());
     }

     @Then("校验状态码 (\\d+)")
     public void verify_status_code(int status){
            json = response.then().statusCode(status);

    }
    @Then("校验返回类型 (.*)")
    public void verify_Type(String status){
        json = response.then().contentType(status);

    }

    @Then("打印请求内容")
    public void printf_body(){
        json = response.then().log().body();

    }

      @And("^Int返回值属性匹配$")
      public  void   verify_then_int(Map<String,Integer> responseFields){


        for (Map.Entry<String,Integer> entry : responseFields.entrySet()) {
            response.then().body(entry.getKey(), equalTo(entry.getValue()));
        }
      }
    @And("^String返回值属性匹配$")
    public  void   verify_then_String(Map<String,String> responseFields){


        for (Map.Entry<String,String> entry : responseFields.entrySet()) {
            response.then().body(entry.getKey(), equalTo(entry.getValue()));
        }
    }
    @And("^返回值属性包含$")
    public  void   verify_then_include(Map<String,Integer> responseFields){


        for (Map.Entry<String, Integer> entry : responseFields.entrySet()) {
            response.then().assertThat().body(entry.getKey(), hasSize(entry.getValue()));
        }
    }



}