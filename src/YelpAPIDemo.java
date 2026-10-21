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

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
