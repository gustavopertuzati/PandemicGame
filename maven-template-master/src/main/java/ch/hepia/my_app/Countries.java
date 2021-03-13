package ch.hepia.my_app;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Countries {

    private List<Country> countries = new ArrayList<>();
    
    public Countries(){}

    public Countries(List<Country> listOfCountries){
        countries = listOfCountries;
    }

    public void addCountry(Country c){
        this.countries.add(c);
    }

    public Country getCountryByCoordinates(double x, double y){
        int mapWidth = 3840;
        int mapHeight = 1958;
        for (Country c : this.countries){
            double[] coords = c.Coordinates();
            double calcX = (coords[1]+180)*(mapWidth/360);
            double latRad = coords[0]*Math.PI/180;   
            double mercN = Math.log(Math.tan((Math.PI/4)+(latRad/2)));
            double calcY = (mapHeight/2)-(mapWidth*mercN/(2*Math.PI));
            if(c.CountryName().equals("switzerland")){
                System.out.println("vraies: " + coords[0] + ";" + coords[1]);
                System.out.println("antwan: " + calcX + ";" + calcY);
                System.out.println("pedped: " + x + ";" + y);
            }
            if ( (Math.abs(calcX - x) <=10) && (Math.abs(calcY - y) <= 10) && !c.CountryName().equals("malta")){
                return c;
            }
        }
        throw new RuntimeException("impossible to find a country for x=" + x +" and y=" + y);
    }

    public Country getCountryDataByName(String name){
        for (Country c : this.countries){
            if (c.CountryName().equals(name)){
                return c;
            }
        }
        throw new RuntimeException("no country named " + name);
    }

    public List<Country> listOfCountries(){
        return this.countries;
    }
}
