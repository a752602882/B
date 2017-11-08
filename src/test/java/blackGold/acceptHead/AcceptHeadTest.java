package blackGold.acceptHead;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AcceptHeadTest {

    static  String  baseUrl="http://localhost:8080";

//java -jar wiremock-standalone-2.1.10.jar --proxy-all="http://127.0.0.1:8088" --record-mappings --verbose

    public void initPath(){
        RestAssured.baseURI = baseUrl;
    }


    /*******************************************************
     使用String参数的accept方法只是头接受的别名
     ******************************************************/
    @Test
    public void accept_method_with_string_parameter_is_just_an_alias_for_header_accept() {

        /*
        request 我们希望获得application/xml数据，实际上response 返回的是json
        这里需要从服务器做判断，因为我没有服务器，所有 这里accpect是没用的
        */
           given().
                    accept("application/xml").
                    body("{ \"MRData.limit\" : \"70\"}").
           when().
                 get("/api/f1/2016/drivers.json").
           then().
                   body("MRData.limit",equalTo("30"));






        }



}