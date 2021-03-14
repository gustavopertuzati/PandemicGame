package ch.hepia.my_app;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.shape.Circle;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.WritableImage;


public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        APICountryManager test = new APICountryManager("https://api.covid19api.com");
        Countries lol = test.getCountries("summary");


        Image worldImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/final_map.png"));
        double originalWidth = worldImage.getWidth();  
        double originalHeight = worldImage.getHeight();
        Group box = new Group();
        int newWidth = 1200;
        //Pour garder le ratio largeur hauteur originale
        int newHeight = (int)(originalHeight*newWidth/originalWidth);

        ImageView iV = new ImageView(worldImage);
        
        
        iV.setOnMouseClicked(e -> {
            try{
                Country crt = lol.getCountryByCoordinates(e.getX(), e.getY());
                new NewStage(crt, primaryStage);
            }catch(Exception oops){
                System.out.println(e.getX()+ "/" +e.getY());

            }
        });

        
        box.getChildren().add(iV);
        lol.listOfCountries().forEach( c -> box.getChildren().add(getCountryCircle(c)));
        
        ZoomableScrollPane scroller = new ZoomableScrollPane(box, newWidth, newHeight);
        scroller.setPrefSize(newWidth, newHeight);
        scroller.setPannable(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        Scene scene = new Scene(scroller, newWidth, newHeight);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public Circle getCountryCircle(Country c){
        Circle circ = new Circle(c.coordinates()[0], c.coordinates()[1], c.size()*6.5 , Color.RED);
        circ.setMouseTransparent(true);
        return circ;
    }

}