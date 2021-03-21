package ch.hepia.my_app;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.Group;
import javafx.animation.*;
//import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.WritableImage;
import javafx.scene.control.*;

public class App extends Application{
    public static void main(String[] args) {
        launch(args);
        
        /*APICountryManager test = new APICountryManager("https://api.covid19api.com");
        Countries lol = test.getCountries("summary");
        for(Country c : lol.listOfCountries()){
            System.out.println(c.toString());
        }*/
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        APICountryManager test = new APICountryManager("https://api.covid19api.com");
        Countries lol = test.getCountries("summary");

        Image worldImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/final_map.png"));
        double width = worldImage.getWidth();
        double height = worldImage.getHeight();
        
        
        WritableImage writableImage =  new WritableImage((int)width, (int)height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        
        
        ScrollPane scroller = new ScrollPane();

        Map<String, Integer[]> myMap = new HashMap<>();
        try{
            Scanner sc = new Scanner(this.getClass().getClassLoader().getResourceAsStream("countrycoords.txt")); 
            while (sc.hasNextLine()) {
             String[] tmpStr = sc.nextLine().split("/"); 
             Integer[] tmpDbl = {Integer.parseInt(tmpStr[1]), Integer.parseInt(tmpStr[2])};
              myMap.put(tmpStr[0], tmpDbl);
            } 
        }catch(Exception e){
            e.printStackTrace();
        }

        PixelReader pixelReader = worldImage.getPixelReader();
        for (int i = 0; i < (int)width; i++ ){
            for (int j = 0; j < (int)height; j++){
                pixelReader.getColor(i, j);
                pixelWriter.setColor(i, j, pixelReader.getColor(i, j));
            }
        }
    
        for ( String k : myMap.keySet()){
            for(int i = 0; i < 7; i++){
                for(int j = 0; j < 7; j++){
                    Integer[] coords = myMap.get(k);
                    pixelWriter.setColor(coords[0]+i, coords[1]+j, Color.rgb(255,0,0));
                    pixelWriter.setColor(coords[0]+i, coords[1]-j, Color.rgb(255,0,0));
                    pixelWriter.setColor(coords[0]-i, coords[1]+j, Color.rgb(255,0,0));
                    pixelWriter.setColor(coords[0]-i, coords[1]-j, Color.rgb(255,0,0));
                }
            }
        }
        
        
        //lol.listOfCountries().forEach( (Country i) -> pixelWriter.setColor((int)(i.Coordinates()[1]+60)*10+200, (int)(i.Coordinates()[0]+180)*10+100,  Color.rgb(255,0,0)));
        
        ImageView worldImageView = new ImageView(writableImage);

        double newWidth = 1500;
        double newHeight = 1500*height/width;

        scroller.setPrefSize(newWidth, newHeight);
        scroller.setPannable(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Scene scene = new Scene(scroller, newWidth, newHeight);
        scroller.setContent(worldImageView);
        worldImageView.setPickOnBounds(true);

        final Pane sidePane = createSidebarContent();

        worldImageView.setOnMouseClicked(e -> {
            try{
                Country crt = lol.getCountryByCoordinates(e.getX(), e.getY());
                SideBar sidebar = new SideBar(250, sidePane);
            }catch(Exception oops){}
        });

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}