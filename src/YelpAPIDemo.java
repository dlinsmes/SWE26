import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;

//search json jar - maven link - github link - download jar file, copy/paste to lib/ 
import org.json.*;

public class YelpAPIDemo {

    //apis - application programming interface
    //-allow you to access web resources and apps via code

    public static void main(String[] args) throws Exception {

        try {
        
            //api key is used to authenticate access to the yelp api
            String API_key = "";
            
            URI uri = new URI("https://api.yelp.com/v3/businesses/search?"
                    + "term=taco"
                    + "&location=irvine"
                    + "&radius=5000" 
                    //radius in meters - per documentation
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .header("authorization", "bearer " + API_key)
                    .build();

            HttpClient client = HttpClient.newHttpClient();    
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            System.out.println(response);

            //JSON - javascript object notation
            
            JSONObject responseObj = new JSONObject(response.body());
            System.out.println(responseObj);

            //data is labeled using keys (like indices for arrays)
            System.out.println(responseObj.keySet());

            int total = responseObj.getInt("total");
            System.out.println(total);

            System.out.println(responseObj.get("region"));

            //isolate the reg JSON object
            JSONObject regObj = responseObj.getJSONObject("region");
            JSONObject ctrObj = regObj.getJSONObject("center");
            double lat = ctrObj.getDouble("latitude");
            System.out.println(lat);

            //can string together multiple method calls to isolate data
            double lon = responseObj.getJSONObject("region").getJSONObject("center").getDouble("longitude");
            System.out.println(lon);

            JSONArray bArray = responseObj.getJSONArray("businesses");
            
            //the request will return 20 results at a time 
            // (even though there are more than that total)
            System.out.println(bArray.length());

            System.out.println(bArray.get(5));
            
            //need to cast each element of the array into a JSONObject
            JSONObject exResult = (JSONObject)bArray.get(5);

            String exName = exResult.getString("name");
            System.out.println(exName);

            //loop through bArray to print names of all results
            for (Object o: bArray) {
                JSONObject obj = (JSONObject) o;
                System.out.println(obj.getString("name"));
            }

            for (int i = 0; i < bArray.length(); i++) {
                JSONObject obj = (JSONObject)bArray.get(i);
                System.out.println(obj.get("categories"));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
