package ch.hepia.my_app;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import java.util.Optional;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.canvas.Canvas;
import javafx.scene.Group;
import javafx.scene.Node;





import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.scene.shape.Circle;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Font;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;

import javafx.stage.Modality;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import javafx.scene.image.WritableImage;

import java.time.LocalDate;

public class App extends Application {
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
        int newWidth = 1200;
        int newHeight = (int)(originalHeight*newWidth/originalWidth);
        
        ImageView iV = new ImageView(worldImage); 
        iV.setOnMouseClicked(e -> {
            try{
                Optional<Country> opTmp = lol.getCountryByCoordinates(e.getX(), e.getY());
                if(!opTmp.isEmpty()){
                    NewStage ct = new NewStage(opTmp.get(), primaryStage, e.getScreenX(), e.getScreenY() );
                }
            }catch(Exception g){}
        });
        
        Group box = new Group();
        box.getChildren().add(iV);
        for( Country i : lol.listOfCountries()){
            if(i.slug().equals("france")){
                i.updateCountryHistory();
                System.out.println(i.getDailyCasesByDate(LocalDate.of(2020,12,15)));
                
            }
        }
        
        ZoomableScrollPane scroller = new ZoomableScrollPane(box, newWidth, newHeight);
        lol.listOfCountries().forEach( c -> box.getChildren().add(getCountryCircle(c, scroller)));
        scroller.addEventFilter(ScrollEvent.ANY, e->{
            removeCircles(box);
            e.consume();
            scroller.onScroll(e.getDeltaY(), new Point2D(e.getX(), e.getY()));
            lol.listOfCountries().forEach( c -> box.getChildren().add(getCountryCircle(c, scroller)));
        });
        scroller.setPrefSize(newWidth, newHeight);
        scroller.setPannable(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
               
        Scene scene = new Scene(scroller, newWidth, newHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void removeCircles(Group box){
        ArrayList<Node> lstCircles = new ArrayList<>();
        for(Node i : box.getChildren()){
            if(i.getClass() == Circle.class){
                lstCircles.add(i);
            }
        }
        lstCircles.forEach(i -> box.getChildren().remove(i));
    }

    public Circle getCountryCircle(Country c, ZoomableScrollPane pane) {
        Circle circ = new Circle(-1,-1,-1);
        if(pane.getZoomWidth() < c.size()*800){
            return circ;
        }
        circ = new Circle(c.coordinates()[0], c.coordinates()[1], 15 , c.getColorFromCountry());
        circ.setMouseTransparent(true);
        return circ;
    }
}