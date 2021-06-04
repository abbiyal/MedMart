package medinventory.mapsapi;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class DistanceTime {



   private static final String API_KEY="AIzaSyDBuSpS5OrXPM0r2mH1JyBr0vMfX7qlQnw";
   OkHttpClient client = new OkHttpClient();


 public String calculate(String source ,String destination) throws IOException {
        String url="https://maps.googleapis.com/maps/api/distancematrix/json?origins="+source+"&destinations="+destination+"&key="+ API_KEY;
        System.out.println(url);
           Request request = new Request.Builder()
               .url(url)
               .build();
           
           Response response = client.newCall(request).execute();
           String distance="";
           JSONParser parser = new JSONParser();
           try {

            Object obj = parser.parse(response.body().string());
            JSONObject jsonobj=(JSONObject)obj;

            JSONArray dist=(JSONArray)jsonobj.get("rows");
            JSONObject obj2 = (JSONObject)dist.get(0);
            JSONArray disting=(JSONArray)obj2.get("elements");
            JSONObject obj3 = (JSONObject)disting.get(0);
            JSONObject obj4=(JSONObject)obj3.get("distance");
            JSONObject obj5=(JSONObject)obj3.get("duration");
            distance = obj4.get("text").toString();
            
           }catch(Exception e) {
        	   System.out.println("Mapp error");
        	    e.printStackTrace();
           }
          return distance;
      }


}
