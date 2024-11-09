package utils;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;

import javax.naming.ConfigurationException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Utils {

    public static String userFilePath="./src/test/resources/users.json";

    public static Properties props;
    public static FileInputStream file;


    public static int generateRandomNumber(int min, int max){
        double randomId= Math.random()*(max-min)+min;
        return (int) randomId;
    }


    public static void fileWriteProcess( JSONArray jsonArray ) throws IOException {
        FileWriter fileWriter=new FileWriter(userFilePath);
        fileWriter.write(jsonArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }

    public static String getLatestUserProperty(String key) throws IOException, ParseException {

        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader(userFilePath));
        JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.size() -1);

        String value = (String) jsonObject.get(key);
        return  value;

    }

    public static String geneateRandomEmail() throws IOException, ParseException {

        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader(userFilePath));
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        String emailCount = (String) jsonObject.get("emailCount");
        int num = Integer.parseInt(emailCount)+1;
        String originalEmail = "shouvik9292+" + num  + "@gmail.com";
        return originalEmail;
    }

    public static void increaseEmailCount() throws IOException, ParseException {

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray= (JSONArray) jsonParser.parse(new FileReader(userFilePath));
        JSONObject emailCountObj = (JSONObject) jsonArray.get(0);
        String emailCount = (String) emailCountObj.get("emailCount");
        int num = Integer.parseInt(emailCount)+1;

        emailCountObj.put("emailCount" , String.valueOf(num));

        fileWriteProcess(jsonArray);


    }

    public static void updateCreds(String field, String newData) throws IOException, ParseException {
        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray= (JSONArray) jsonParser.parse(new FileReader(userFilePath));
        JSONObject updatedUserObj = (JSONObject) jsonArray.get( jsonArray.size() -1 );
        updatedUserObj.put(field,newData);

        fileWriteProcess(jsonArray);



    }


    public static void saveUserInfo( JSONObject jsonObject) throws IOException, ParseException {
        increaseEmailCount();

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray= (JSONArray) jsonParser.parse(new FileReader(userFilePath));
        jsonArray.add(jsonObject);

        fileWriteProcess(jsonArray);
    }

    public static void clearLoginCreds(WebDriver driver){
        LoginPage loginPage =new LoginPage(driver);
        loginPage.txtEmail.sendKeys(Keys.CONTROL,"a");
        loginPage.txtEmail.sendKeys(Keys.BACK_SPACE);
        loginPage.txtPassword.sendKeys(Keys.CONTROL,"a");
        loginPage.txtPassword.sendKeys(Keys.BACK_SPACE);
    }


    public static void getToken(WebDriver driver) throws IOException {
        //wait until the authToken is available
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until((ExpectedCondition<Boolean>) wd -> js.executeScript("return window.localStorage.getItem('authToken')") != null);

        //get the authToken from the localstorage
        String authToken = (String) js.executeScript("return window.localStorage.getItem('authToken');");
        String authTokenData=(String) js.executeScript("return window.localStorage.getItem('authTokenData');");
        System.out.println("Auth Token Retrieved: " + authToken);
        System.out.println("Auth Token Data Retrieved: " + authTokenData);

        // Save the auth token to a localstorage.json file
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authToken", authToken);
        jsonObject.put("authTokenData", authTokenData);
        FileWriter writer=new FileWriter("./src/test/resources/localstorage.json");
        writer.write(jsonObject.toJSONString());
        writer.flush();
        writer.close();
    }
    public static void setAuth(WebDriver driver) throws ParseException, InterruptedException, IOException {
        JSONParser jsonParser=new JSONParser();
        JSONObject authObj= (JSONObject) jsonParser.parse(new FileReader( "./src/test/resources/localstorage.json"));
        String authToken= authObj.get("authToken").toString();
        String authTokenData= authObj.get("authTokenData").toString();
        System.out.println(authToken);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.setItem('authToken', arguments[0]);", authToken);
        js.executeScript("window.localStorage.setItem('authTokenData', arguments[0]);", authTokenData);
        Thread.sleep(2000);
    }
    public static void scroll(WebDriver driver , int w , int h){
        JavascriptExecutor js =  (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy("+w+","+h+")", "" );
    }

    // Email Reading Methods -
    public static void setEnvVar(String key, String value) throws org.apache.commons.configuration.ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();
    }




    public static   String getEmailList() throws IOException, ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        props = new Properties();
        file = new FileInputStream("./src/test/resources/config.properties");
        props.load(file);

        RestAssured.baseURI="https://gmail.googleapis.com";
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+props.getProperty("google_access_token"))
                .when().get("/gmail/v1/users/me/messages");
        JsonPath jsonPath=res.jsonPath();
        return jsonPath.get("messages[0].id");
    }

    public static String readLatestMail() throws IOException, ConfigurationException, org.apache.commons.configuration.ConfigurationException {
        String messageId= getEmailList();
        RestAssured.baseURI="https://gmail.googleapis.com";
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+props.getProperty("google_access_token"))
                .when().get("/gmail/v1/users/me/messages/"+messageId);

        JsonPath jsonPath=res.jsonPath();
        String message= jsonPath.get("snippet");

        return message;

    }

}
