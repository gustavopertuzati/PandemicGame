package ch.hepia.my_app;
import java.util.Map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App {
    public static void main(String[] args) {
        APICountryManager test = new APICountryManager("https://api.covid19api.com");

        Map<String, Country> lol = test.getCountryMap("summary");
        lol.forEach( (k,v) -> System.out.println(v));

    }
}