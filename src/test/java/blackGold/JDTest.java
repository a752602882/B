package blackGold;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.jayway.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV3;
import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;



public class JDTest {

    static  String  dotaMatchUrl="https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?\n" +
            "start_at_match_id=76561198089183399&key=BAA464D3B432D062BEA99BA753214681";
    @Before
    public void setUp() throws Exception {


       // RestAssured.baseURI = "https://search.jd.com";

        RestAssured.port = 443;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetBook() {
        get("http://api.douban.com/v2/book/1220562").then().assertThat().body("code", equalTo(100));
    }

    @Test
    public void testParseJson() {
        get("https://search.jd.com/Search?keyword=固态硬盘&enc=utf-8&suggest=1.def.0.T16&wq=g&pvid=d71e769ac74f4406af140237b41ee75c").
                then().body("html.head.title",equalTo("京东(JD.COM)-正品低价、品质保障、配送及时、轻松购物！"));
    }
    @Test
   public  void  testMatch(){
       // get(dotaMatchUrl).then().body("result.matches.match_id", hasItems(3333156440L,3333156297L));
//        System.out.println(result.total_results);

        String response= get(dotaMatchUrl).asString();
        List<String> matchID=from(response).getList("result.matches.players.findAll{it.hero_id>10}.hero_id");

    }


    //错误
    @Test
    public  void  testMatchJson(){

       JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV3).freeze()).freeze();
       String url= JDTest.class.getClassLoader().getResource("match-schema.json").getPath();
       get(dotaMatchUrl).then().assertThat().body(matchesJsonSchemaInClasspath(url).using(jsonSchemaFactory));

    }
    @Test
    public  void testGetHeroXML(){
        String url="https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=BAA464D3B432D062BEA99BA753214681&format=xml" ;
       get(url).then().assertThat().body("result.heroes.name",hasItems("npc_dota_hero_antimage",1));
    }

    @Test
    public  void  test12306Inquire(){

        given()
                .param("key","BAA464D3B432D062BEA99BA753214681").
        when()
                .get("/GetHeroes/v0001/").
         then()
                .body("result.status",equalTo(200))
                .body(".count",equalTo(113));
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