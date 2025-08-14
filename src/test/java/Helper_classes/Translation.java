package Helper_classes;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translation {

    public static List<String> translate(String title){

        String translatedHeader=null;
        List<String> theaders = new ArrayList<>();
        Map<String,String> headers = new HashMap<>();
        headers.put("x-rapidapi-host","rapid-translate-multi-traduction.p.rapidapi.com");
        headers.put("x-rapidapi-key","5d7367c490msha9096c7104da201p1bb01bjsna3aaefc9e7e4");

        Map<String,String> body = new HashMap<>();
        body.put("from","es");
        body.put("to","en");
        body.put("e","");
        body.put("q",title);
        try {
            Response response = RestAssured
                    .given()
                    .headers(headers)
                    .formParams(body)
                    .when()
                    .post("https://rapid-translate-multi-traduction.p.rapidapi.com/t")
                    .then()
                    .statusCode(200)
                    .extract().response();
            translatedHeader = response.jsonPath().getString("[0]");
            System.out.println("Translated article title is: "+translatedHeader);
        }
        catch (Exception e){
            System.out.println("Translation failed "+e.getMessage());
        }

        theaders.add(translatedHeader);
        return theaders;
    }
}
