package ch.hepia.my_app;

import java.net.URL; 
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

import java.io.BufferedReader;
import java.io.File;
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
        Map<String, Country> map = new HashMap<>();
        Countries countries = new Countries();
    
        //Faire la requete
        try{
            Map<String, Integer[]> coords = readCoordinates("countrycoords.txt");
            URL url = new URL(this.apiLink+"/"+ request);
            HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
            connexion.setRequestMethod("GET");
            
            int response = connexion.getResponseCode();
            if(response != 200){
                System.out.println("HTTP Request failed with response code: " + response + "\n Data will be taken form contrycords.txt");
                coords.forEach( (k,v) -> countries.addCountry(new Country(k, v[0], v[1], 0,0,0,0,0,0, v[2])));                
                return countries;
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
                if(!coords.keySet().contains(i.get("Slug").toString())){
                    continue;
                }

                //On peut changer Country à Slug si jamais c'est chiant de gérer les espaces et maj
                String countryName = i.get("Slug").toString();
                int totalCases = Integer.parseInt(i.get("TotalConfirmed").toString());
                int dailyCases = Integer.parseInt(i.get("NewConfirmed").toString());
                int totalDeaths = Integer.parseInt(i.get("TotalDeaths").toString());
                int dailyDeaths = Integer.parseInt(i.get("NewDeaths").toString());
                int totalRecovered = Integer.parseInt(i.get("TotalRecovered").toString());
                int dailyRecovered = Integer.parseInt(i.get("NewRecovered").toString());
                Integer[] tmp = coords.get(countryName);
                Integer latitude = tmp[0];
                Integer longitude = tmp[1];
                int size = tmp[2];
                Country currentCountry = new Country(countryName, latitude, longitude, totalCases, dailyCases, totalDeaths, dailyDeaths, totalRecovered, dailyRecovered, size);
                
                countries.addCountry(currentCountry);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return countries;
    }


    private Map<String, Integer[]> readCoordinates(String path){
        Map<String, Integer[]> myMap = new HashMap<>();
        try{
            Scanner sc = new Scanner(this.getClass().getClassLoader().getResourceAsStream(path)); 
            while (sc.hasNextLine()) {
             String[] tmpStr = sc.nextLine().split("/"); 
             Integer[] tmpInt = {Integer.parseInt(tmpStr[1]), Integer.parseInt(tmpStr[2]), Integer.parseInt(tmpStr[3])};
            myMap.put(tmpStr[0], tmpInt);
            } 
        }catch(Exception e){
            e.printStackTrace();
        }
        return myMap;
    }


    //Utilisé à la main lentement pour récupérer les coordonnées
    //Provoque l'erreur Too Many Requests, donc fait à la main 
    //Puis stocké dans le fichier ressources/countrycoords
    private double[] getCountryCoord(String slug){

        double[] coords = new double[2];
        try{
            URL url = new URL(this.apiLink+"/dayone/country/"+ slug + "/status/confirmed");
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
                JSONArray data_obj = (JSONArray) parser.parse(content);

                JSONObject i =  (JSONObject)data_obj.get(0);

                coords[0] = Double.parseDouble(i.get("Lat").toString());
                coords[1] = Double.parseDouble(i.get("Lon").toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }
            return coords;
    }
    
    public Country getSpecificCountryTime(){
        throw new RuntimeException("Not implemented");
    }
}
