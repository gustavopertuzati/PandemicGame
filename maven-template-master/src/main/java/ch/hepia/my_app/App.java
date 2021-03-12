package ch.hepia.my_app;

import java.util.Map;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) {
        launch(args);
        APICountryManager test = new APICountryManager("https://api.covid19api.com");

        Countries lol = test.getCountries("summary");
        for(Country c : lol.listOfCountries()){
            System.out.println(c.toString());
        }
    }
    
    @Override
    public void start(Stage primaryStage) {

        Image worldImage = new Image(getClass().getResourceAsStream("ressources/images/world.jpg"));
        double width = worldImage.getWidth();
        double height = worldImage.getHeight();
        
        ImageView worldImageView = new ImageView(worldImage);

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(worldImageView);

        worldImageView.setPickOnBounds(true);

        worldImageView.setOnMouseClicked(e -> {
            System.out.println("["+e.getX()+", "+e.getY()+"]");
        });

        Scene scene = new Scene(scroller, width, height);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}