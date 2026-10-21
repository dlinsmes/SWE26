import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.awt.Desktop;

import org.json.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class SpotifyDemo {
    
    static String client_id = "419a7354744b47acba017ab450083ab3";
    static String client_secret = "b0882ce8bf73455984842a8916347316";
    static String redirect_uri = "http://127.0.0.1:8000";
    static HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {

        //see documentation at
        // https://developer.spotify.com/documentation/web-api/concepts/authorization

        //Authorization options:
        //-Client Credentials: access to Spotify database
        //-Authorization Code: Spotify user needs to approve the app to access their account 
        // to allow access to user account-specific items: playlists, history, etc
        // see scopes: https://developer.spotify.com/documentation/web-api/concepts/scopes

        String accessToken;

        accessToken = getAccessToken_ClientCreds();

        System.out.println("Client Credentials Access Token:\n" + accessToken);

        Scanner sc = new Scanner(System.in);
        System.out.println("\nPress enter to demo process for Authorization Code authentication...");
        sc.nextLine();

        accessToken = getAccessToken_AuthCode();

        System.out.println("Client Credentials Access Token:\n" + accessToken);

        System.out.println();

        ArrayList<String> userTopAristists = getUserTopArtists(accessToken);
        System.out.println("Your top artists:");
        for (String artist: userTopAristists)
            System.out.println("\t" +artist);

    }

    public static String getAccessToken_ClientCreds() {

        String requestBody = "grant_type=client_credentials";
        return getAccessToken(requestBody);

    }

    public static String getAccessToken_AuthCode() {
        try {
            System.out.println("When you press enter to continue, a Spotify link will open.");
            System.out.println("Log in to your Spotify account and allow access to this app.");
            System.out.println("A page with url " + redirect_uri + " will attempt to load.");
            System.out.println("(The page will be unavailable.)");
            System.out.println("Copy and paste the full url into the console to extract the authorization code.");


            System.out.println("Press enter to proceed...");

            Scanner s = new Scanner(System.in);
            s.nextLine();
            
            URI userAuthURI = new URI("https://accounts.spotify.com/authorize?"
                + "client_id=" + client_id
                + "&response_type=code"
                + "&redirect_uri=" + redirect_uri
                + "&scope=user-top-read%20user-read-recently-played"
            );

            Desktop.getDesktop().browse(userAuthURI);


            System.out.println("Paste full url beginning with " + redirect_uri + " and press enter");
            String codeURI = s.nextLine();
            String code = codeURI.split("code=")[1].split("&")[0];

            String requestBody = "grant_type=authorization_code";
            requestBody += "&code="+code;
            requestBody += "&redirect_uri="+redirect_uri;
            return getAccessToken(requestBody);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public static String getAccessToken (String requestBody) {
        try {

            URI accessTokenURI = new URI("https://accounts.spotify.com/api/token");
            String idSec = client_id + ":" + client_secret;
            String encodedString = Base64.getEncoder().encodeToString(idSec.getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(accessTokenURI)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Authorization", "basic " + encodedString)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            System.out.println();
            System.out.println(response);

            JSONObject authResp = new JSONObject(response.body());
            String accessToken = authResp.get("access_token").toString();
            return accessToken;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //https://developer.spotify.com/documentation/web-api/reference/get-users-top-artists-and-tracks
    public static ArrayList<String> getUserTopArtists(String accessToken) {
        try {
            ArrayList <String> artistList = new ArrayList<String>();
            URI uri = new URI("https://api.spotify.com/v1/me/top/artists");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .header("authorization", "Bearer " + accessToken)
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            JSONObject respObj = new JSONObject(response.body());
            JSONArray itemArray = respObj.getJSONArray("items");
            for (Object obj:itemArray) {
                JSONObject AristObj = (JSONObject) (obj);
                String artistName = AristObj.getString("name");
                artistList.add(artistName);
            }

            return artistList;
        }
        catch (Exception e) {
            return null;
        }
    }
    
}
