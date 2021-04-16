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

import javafx.scene.shape.Circle;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Font;

import javafx.geometry.Insets;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;

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

        Group box = new Group();

        int newWidth = 1200;
        int newHeight = (int)(originalHeight*newWidth/originalWidth);

        ImageView iV = new ImageView(worldImage);        
        Label countryLabel = new Label("Select country to show details");
        Button closeButton = new Button("X");

        closeButton.setStyle("-fx-font-size: 6pt; -fx-text-fill:red;");
        countryLabel.setGraphic(closeButton);
        countryLabel.setContentDisplay(ContentDisplay.LEFT);
        countryLabel.setBackground( new Background (new BackgroundFill(Color.rgb(0, 0, 80, 0.7), new CornerRadii(5.0), new Insets(-5.0))));
        iV.setOnMouseClicked(e -> {
            try{

                Optional<Country> opTmp = lol.getCountryByCoordinates(e.getX(), e.getY());
                if(!opTmp.isEmpty()){
                    Country crt = opTmp.get();
                    countryLabel.setLayoutX(e.getX());
                    countryLabel.setLayoutY(e.getY());
                    countryLabel.setText("       Country detail:\n\n" + crt.toString());
                    countryLabel.setFont(new Font("Arial", 23));
                    countryLabel.setTextFill(Color.web("#ffffff"));
                    countryLabel.setMinWidth(Region.USE_PREF_SIZE);
                    //NewStage ct = new NewStage(crt, primaryStage);
                    box.getChildren().addAll(countryLabel);
                    closeButton.setOnAction(event -> box.getChildren().remove(countryLabel));
                }


            }catch(Exception oops){
            }


        });

        box.getChildren().add(iV);
        lol.listOfCountries().forEach( c -> box.getChildren().add(getCountryCircle(c)));
        int sum = 0;
        for( Country i : lol.listOfCountries()){
            sum += i.totalPopulation();
            if(i.slug().equals("france")){
                i.updateCountryHistory();
                System.out.println(i.getDailyCasesByDate(LocalDate.of(2020,12,15)));

            }
        }

        //System.out.println("Lol thanos snap: " + sum);




        ZoomableScrollPane scroller = new ZoomableScrollPane(box, newWidth, newHeight);
        scroller.setPrefSize(newWidth, newHeight);
        scroller.setPannable(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
               
        Scene scene = new Scene(scroller, newWidth, newHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public Circle getCountryCircle(Country c) {
        Circle circ = new Circle(c.coordinates()[0], c.coordinates()[1], c.size() * 6.5, c.getColorFromCountry());
        circ.setMouseTransparent(true);
        return circ;
    }
}