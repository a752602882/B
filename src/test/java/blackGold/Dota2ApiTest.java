package blackGold;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;


public class Dota2ApiTest {

    static  String  dotaMatchUrl="https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?\n" +
            "start_at_match_id=76561198089183399&key=BAA464D3B432D062BEA99BA753214681";
    @Before
    public void setUp() throws Exception {


       RestAssured.baseURI = "https://api.steampowered.com/IEconDOTA2_570";

        RestAssured.port = 443;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public  void  getHero() {

       given()
                .param("key","BAA464D3B432D062BEA99BA753214681").
        when()
                .get("/GetHeroes/v0001/").
         then()
                .body("result.status", equalTo(200),
                        "result.count", equalTo(113));
//*********************************************************************************************//
        ValidatableResponse resp =     given()
                .param("key","BAA464D3B432D062BEA99BA753214681").
                        when()
                .get("/GetHeroes/v0001/").
                        then();
        //判断返回Json数据的title
        resp.body("result.status", equalTo(200));
        //判断二级属性rating.max的值
        resp.body("result.count", equalTo(113));

    }
    @Test
   public  void  testMatch(){
        String response= get(dotaMatchUrl).asString();
        List<String> matchID=from(response).getList("result.matches.players.findAll{it.hero_id>10}.hero_id");

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
    public  void testGetHeroXML(){
     //   String url="https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=BAA464D3B432D062BEA99BA753214681&format=xml" ;
        given().param("key","BAA464D3B432D062BEA99BA753214681")
                .when()
                .get("/GetHeroes/v0001")
                .then().assertThat().body("result.heroes.hero.name.find{ it.value == 'npc_dota_hero_antimage'}",equalTo("npc_dota_hero_antimage"));
//       get(url).then().assertThat().body("result.heroes.name",hasItems("npc_dota_hero_antimage",1));
    }

    @Test
    public  void  test12306Inquire(){

     /*   given()
                .param("key","BAA464D3B432D062BEA99BA753214681").
        when()
                .get("/GetHeroes/v0001/").
         then()
                .body("result.status", equalTo(200),
                        "result.count", equalTo(113));*/



       /* given().param("_json_att","",


                        "leftTicketDTO.from_station_name","成都" ,
                        "leftTicketDTO.to_station_name" ,"杭州",
                        "leftTicketDTO.from_station","CDW",
                        "leftTicketDTO.to_station","HZH",
                        "leftTicketDTO.train_date ","2017-07-29",
                        "back_train_date","2017-07-26",
                         "flag","dc")
                .when().post("https://kyfw.12306.cn").then().body("")*/
    }

}