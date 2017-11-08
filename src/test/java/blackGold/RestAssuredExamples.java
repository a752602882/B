package blackGold;

import com.jayway.restassured.builder.ResponseBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.RequestSpecificationImpl;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import org.apache.http.client.methods.RequestBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;

public class RestAssuredExamples {

    @Test
    public  void useQueryParametersSingleTestcase(){
        given().
                params("text","testcaseOne").
         when().
                get("http://md5.jsontest.com").
        then().
                body("md5",equalTo("4ff1c9b1d1f23c6def53f957b1ed827f"));

    }


    @Test
    public  void useMultipleQueryParameters(){
        given().
               param("keyword", "固态硬盘").
               param( "enc", "utf-8").
               param("suggest", "1.def.0.T16").
               param("wq","g").
               param("pvid","d71e769ac74f4406af140237b41ee75c").

         when().
                get("https://search.jd.com/Search").
         then().
                body("html.head.title",equalTo("固态硬盘 - 商品搜索 - 京东"))
        .log().body();
    }

    ResponseSpecification resp;

    @BeforeClass
    public void  requestConfig(){

         resp=new ResponseSpecBuilder().expectContentType(ContentType.JSON )
                 .build();
    }

    //https://hiswd.jd.com/?pvid=c957b96d1bfb43db8fbb68c52447e8bc&callback=jQuery5146647
    //因为 返回的值是json+text的形式 ，不能解析，暂时超出知识范围
    @Test
    public  void useSinglePathParameter(){
        given().
                param("pvid","c957b96d1bfb43db8fbb68c52447e8bc").
                 param("callback","jQuery5146647").
        when().
                get("https://hiswd.jd.com/").
        then().
               using() .parser("text/json", Parser.JSON).
                body("$",hasItem("索尼电视"))
                .log().body();
    }
}
