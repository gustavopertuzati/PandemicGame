package ch.hepia.my_app;

import java.net.URL; 
import java.net.URLConnection;
import java.net.HttpURLConnection;
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

public class APICountryManager {
    
    private String apiLink; //https://api.covid19api.com

    public APICountryManager(String apiLink){
        //Si il y a un slash à la fin du string il faut l'enlever
        this.apiLink = apiLink;
    }
    
    public Countries getCountries(String request){ //"countries"
        //Si il y a un slash au début du string, il faut l'enlever
        Countries countries = new Countries();
    
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
            JSONArray jsonArrayCountries = (JSONArray) data_obj.get("Countries");
            
            for (int crt = 0; crt < jsonArrayCountries.size(); crt+=1){

                JSONObject i =  (JSONObject)jsonArrayCountries.get(crt);

                //On peut changer Country à Slug si jamais c'est chiant de gérer les espaces et maj
                String countryName = i.get("Slug").toString();
                Double latitude = 0.0;//Double.parseDouble(s).get("kek");
                Double longitude = 0.0;//i.get("kek");
                int totalCases = Integer.parseInt(i.get("TotalConfirmed").toString());
                int dailyCases = Integer.parseInt(i.get("NewConfirmed").toString());
                int totalDeaths = Integer.parseInt(i.get("TotalDeaths").toString());
                int dailyDeaths = Integer.parseInt(i.get("NewDeaths").toString());
                int totalRecovered = Integer.parseInt(i.get("TotalRecovered").toString());
                int dailyRecovered = Integer.parseInt(i.get("NewRecovered").toString());

                Country currentCountry = new Country(countryName, latitude, longitude, totalCases, dailyCases, totalDeaths, dailyDeaths, totalRecovered, dailyRecovered);
                
                countries.addCountry(currentCountry);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return countries;
    }
    
    public Country getSpecificCountryTime(){
        throw new RuntimeException("Not implemented");
    }
}
