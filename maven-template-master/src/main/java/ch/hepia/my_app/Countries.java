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
        for (Country c : this.countries){
            if ( (Math.abs(c.coordinates()[0] - x) <= c.size()*6.5) && (Math.abs(c.coordinates()[1] - y) <= c.size()*6.5)){
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
