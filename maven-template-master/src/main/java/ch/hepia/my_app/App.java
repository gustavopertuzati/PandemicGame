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
        
        //Va contenir le pays ainsi que les coordonnées de son cercle, ainsi que l'index du cercle dans circles
        Map<Country, Integer[]> countryCircleCoords = new HashMap<>();

        List<Circle> circles = new ArrayList<>();

        //Il faut initialiser l'image à une taille "normale", et initialiser les cercles dessus aussi
        //Nouvlles coordonnées initiales du cercle: 
        //x=(int)X_coordonéePays*largeurImageResizée/largeurOriginale     
        //y=(int)Y_coordonéePays*hauteurImageResizée/hauteurOriginale



        Image worldImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/final_map.png"));
        double originalWidth = worldImage.getWidth();  
        double originalHeight = worldImage.getHeight();
        Group box = new Group();
        //Il faut essayer trouver la nouvelle taille de l'image en fonction de la résolution de l'écran du user.
        int newWidth = 1600;
        //Pour garder le ratio largeur hauteur originale
        int newHeight = (int)(originalHeight*newWidth/originalWidth);

        ScrollPane scroller = new ScrollPane();
        ImageView iV = new ImageView(worldImage);


        scroller.setPrefSize(newWidth, newHeight);
        scroller.setPannable(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        box.getChildren().add(iV);
        lol.listOfCountries().forEach( c -> box.getChildren().add(getCountryCircle(c)));
        scroller.setContent(box);
        Scene scene = new Scene(scroller, newWidth, newHeight);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public Circle getCountryCircle(Country c){
        return new Circle(c.coordinates()[0], c.coordinates()[1], c.size()*6.5 , Color.RED);
    }

}