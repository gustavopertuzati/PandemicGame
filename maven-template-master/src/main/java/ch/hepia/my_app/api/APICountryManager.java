package ch.hepia.my_app;

import java.net.URL; 
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ProtocolException;
import java.net.MalformedURLException;
import java.text.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//import org.json.parser.JSONParser;

public class APICountryManager {
    
    private String apiLink; //https://api.covid19api.com

    public APICountryManager(String apiLink){
        //Si il y a un slash à la fin du string il faut l'enlever
        this.apiLink = apiLink;
    }
    
    public Map<String, Country> getCountryMap(String request){ //"countries"
        //Si il y a un slash au début du string, il faut l'enlever
        Map<String, Country> map = new HashMap<>();
    
        //Faire la requete
        try{
            URL url = new URL(this.apiLink+"/"+ request);
            HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
            connexion.setRequestMethod("GET");
            int response = connexion.getResponseCode();
            if(response != 200){
                throw new RuntimeException("HTTP Request failed with response code: " + response);
            }
            String content = "";
            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext()){
                content += sc.nextLine();
            }
            sc.close();
            
            JSONParser parser = new JSONParser();
            JSONObject data_obj = (JSONObject) parser.parse(content);
            JSONArray countries = (JSONArray) data_obj.get("Countries");
            
            for (int crt = 0; crt < countries.size(); crt+=1){
                JSONObject i =  (JSONObject)countries.get(crt);
                //On peut changer Country à Slug si jamais c'est chiant de gérer les espaces et maj
               /* String countryName = i.get("Country");
                Double latitude = i.get("kek");
                Double longitude = i.get("kek");
                int totalCases = i.get("TotalConfirmed");
                int dailyCases = i.get("NewConfirmed");
                int totalDeaths = i.get("TotalDeaths");
                int dailyDeaths = i.get("NewDeaths");
                int totalRecovered = i.get("TotalRecovered");
                int dailyRecovered = i.get("NewRecovered");

                Country currentCountry = new Country(countryName, latitude, longitude, totalCases, dailyCases, totalDeaths, dailyDeaths, totalRecovered, dailyRecovered);
                
                 map.put(countryName, currentCountry);
            */
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return map;
    }
    
    public Country getSpecificCountryTime(){
        throw new RuntimeException("Not implemented");
    }

    public static void main( String[] args ) {
        
    }
}
