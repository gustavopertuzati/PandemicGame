package ch.hepia.my_app;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.animation.Animation;
import javafx.animation.Transition;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.FlowPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javafx.scene.shape.Circle;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.geometry.Rectangle2D;
import javafx.geometry.Point2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.util.Duration;

import java.time.LocalDate;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        APICountryManager test = new APICountryManager("https://api.covid19api.com");
        Countries countries = test.getCountries("summary");

        Image worldImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/final_map.png"));
        int newWidth = 1420;
        int newHeight = (int)(worldImage.getHeight() * newWidth / worldImage.getWidth());
        
        ImageView iV = new ImageView(worldImage); 
        iV.setOnMouseClicked(e -> {
            try{
                Optional<Country> op = countries.getCountryByCoordinates(e.getX(), e.getY());
                if(!op.isEmpty()){
                    NewStage ct = new NewStage(op.get(), primaryStage, e.getScreenX(), e.getScreenY() );
                }
            }catch(Exception any){}
        });
        
        Group box = new Group();
        box.getChildren().add(iV);
        for( Country i : countries.listOfCountries()){
            if(i.slug().equals("france")){
                i.updateCountryHistory();
                System.out.println(i.getDailyCasesByDate(LocalDate.of(2020,12,15)));
                
            }
        }
        
        ZoomableScrollPane scroller = new ZoomableScrollPane(box, newWidth, newHeight);
        countries.listOfCountries().forEach( c -> box.getChildren().add(getCountryCircle(c, scroller)));

        scroller.addEventFilter(ScrollEvent.ANY, e->{
            removeCircles(box);
            scroller.onScroll(e.getDeltaY(), new Point2D(e.getX(), e.getY()));
            System.out.println(scroller.getZoomWidth());
            countries.listOfCountries().forEach( c -> box.getChildren().add(getCountryCircle(c, scroller)));
        });

        scroller.setPrefSize(newWidth, newHeight);
        scroller.setPannable(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        Group game = new Group();
        game.getChildren().add(scroller);
        
        HBox bottomBar = createBottomHBox();        
        bottomBar.setAlignment(Pos.BOTTOM_CENTER);
        bottomBar.setPrefWidth(newWidth);
        bottomBar.setMinHeight(40);
        bottomBar.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
//
        AnchorPane root = new AnchorPane(game);

        HBox bottomCures = new HBox();
        bottomCures.setStyle("-fx-background-color: green;");
        bottomCures.setMaxWidth(newWidth-200);
        bottomCures.setMaxWidth(newWidth-200);
        bottomCures.setMinHeight(160);

        HBox bottomSick = new HBox();
        bottomCures.setStyle("-fx-background-color: red;");
        bottomCures.setMaxWidth(newWidth-700);
        bottomCures.setMinHeight(160);

        HBox bottomHbox = new HBox(bottomCures, bottomSick);

        AnchorPane.setBottomAnchor(bottomHbox, 50d);
        AnchorPane.setLeftAnchor(bottomHbox, 75d);
        AnchorPane.setRightAnchor(bottomHbox, 75d);

        root.getChildren().add(bottomHbox);

        VBox.setMargin(bottomHbox, new Insets(0,10,10,10));
        game.getChildren().add(bottomHbox);
                
        VBox.setVgrow(game, Priority.ALWAYS);
        VBox.setVgrow(bottomBar, Priority.ALWAYS);

        VBox gameBox = new VBox(root, bottomBar);

        // pour la side bar on doit pouvoir utiliser le dernier commit dans la branche "menu"
        
        newHeight += 50;
        Scene finalScene = new Scene(gameBox, newWidth, newHeight);
        primaryStage.setScene(finalScene);
        primaryStage.setResizable(false);
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                primaryStage.setMaximized(false);
        });
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

        if(pane.getZoomWidth()/400 < 1/(0.1*c.size())){
            return circ;
        }
        circ = new Circle(c.coordinates()[0], c.coordinates()[1], 15 , c.getColorFromCountry());
        circ.setMouseTransparent(true);
        return circ;
    }

    private HBox createBottomHBox(){

        final Button virusBtn = new Button("virus");
        virusBtn.setWrapText(true);
        virusBtn.setMinWidth(120);
        virusBtn.setMinHeight(40);
        virusBtn.setStyle("-fx-font-size: 1.0em;");

        ProgressBar pbVirus = new ProgressBar(0);
        pbVirus.setProgress(0.1);
        pbVirus.setPrefSize(450, 40);
        pbVirus.setStyle("-fx-accent: green;");
        
        Label date = new Label(LocalDate.now().toString());
        date.setWrapText(true);
        date.setMinWidth(150);
        date.setMinHeight(40);
        date.setStyle("-fx-font-size: 2em;");

        ProgressBar pbCure = new ProgressBar(0);
        pbCure.setProgress(0.1);
        pbCure.setPrefSize(450, 40);
        pbCure.setStyle("-fx-accent: blue;");
        
        final Button cureBtn = new Button("cure");
        cureBtn.setWrapText(true);
        cureBtn.setMinWidth(120);
        cureBtn.setMinHeight(40);
        cureBtn.setStyle("-fx-font-size: 1.0em;");

        HBox bottom = new HBox(20, virusBtn, pbVirus, date, pbCure, cureBtn);
        bottom.setSpacing(30);

        virusBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pbVirus.setProgress(0.5); // a virer
                // afficher le menu
            }
        });

        cureBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pbCure.setProgress(0.5); // a virer
                // afficher le menu
            }
        });

        return bottom;
    }
}