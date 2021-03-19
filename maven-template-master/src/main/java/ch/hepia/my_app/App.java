package ch.hepia.my_app;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;

import javafx.geometry.Rectangle2D;
import javafx.geometry.Point2D;

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
import javafx.scene.input.ScrollEvent;


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
        
        ScrollPane scroller = new ScrollPane();
        scroller.setContent(box);
        scroller.setPrefSize(newWidth, newHeight);
        scroller.setPannable(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        iV.setOnScroll(e->{});

        scroller.addEventHandler(ScrollEvent.ANY, e -> {
            double delta = e.getDeltaY();
            //Rectangle2D viewport = iV.getViewport();
            System.out.println("lol :D");
            double min = Math.min(100 / scroller.getHvalue(), 100 / scroller.getVvalue());
            double max = Math.min(newWidth / scroller.getHvalue(), newHeight / scroller.getVvalue());
            double scale = Math.min(Math.max(Math.pow(1.01, delta), min), max);

            Point2D mouse = new Point2D(e.getX(), e.getY());// imageViewToImage(iV, new Point2D(e.getX(), e.getY()));
            double kek1 = scroller.getHvalue() * scale;
            double kek2 = scroller.getVvalue() * scale;

            min = mouse.getX() - (mouse.getX() - scroller.getHmin()) * scale;
            max = newWidth - kek1;
            double newMinX = Math.min(Math.max(0, min), max);
            min = mouse.getY() - (mouse.getY() - scroller.getVmin()) * scale;
            max = newHeight - kek2;
            double newMinY = Math.min(Math.max(0, min), max);

            iV.setViewport(new Rectangle2D(newMinX, newMinY, kek1, kek2));
        });

        
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