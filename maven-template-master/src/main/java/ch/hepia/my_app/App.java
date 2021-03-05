package ch.hepia.my_app;
import java.util.Map;

public class App {
    public static void main( String[] args ) {
        APICountryManager test = new APICountryManager("https://api.covid19api.com");

        Map<String, Country> lol = test.getCountryMap("summary");
        lol.forEach( (k,v) -> System.out.println(v));
    }
}
