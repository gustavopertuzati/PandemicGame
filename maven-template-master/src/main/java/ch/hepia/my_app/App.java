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

import javafx.scene.Parent;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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
        //primaryStage.setResizable(false);
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
        countries.listOfCountries().forEach( c -> box.getChildren().addAll(getCountryCircle(c, scroller)[0], getCountryCircle(c, scroller)[1]));
        


        scroller.getContent().addEventHandler(ScrollEvent.ANY, e->{
            scroller.onScroll(e.getDeltaY(), new Point2D(e.getX(), e.getY()));
            removeCircles(box);
            countries.listOfCountries().forEach( c -> box.getChildren().addAll(getCountryCircle(c, scroller)[0], getCountryCircle(c, scroller)[1]));
            e.consume();
        });

        scroller.setPrefSize(newWidth, newHeight);
        scroller.setPannable(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        Group game = new Group();
        game.getChildren().add(scroller);
        //game.getChildren().add("barre des cas");

        BottomBar btBar = new BottomBar();
        btBar.fill();      
        btBar.setSpacing(30);        
        btBar.setAlignment(Pos.BOTTOM_CENTER);
        btBar.setPrefWidth(newWidth);
        btBar.setMinHeight(40);
        btBar.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));        
        
/////////////////////////////////////////////////

        //MEGA CHANTIER!!!

        Button virusBtn = btBar.buttonVirus();
        
        Pane virusContentPane = new BorderPane();
        LeftSideBar sbVirus = new LeftSideBar(newWidth/3,0, virusBtn, newHeight, virusContentPane);
        sbVirus.setBackground(new Background(new BackgroundFill(Color.color(0.2,0.2,0.2,0.75), CornerRadii.EMPTY, Insets.EMPTY)));
        
        Button cureBtn = btBar.buttonCure();
        Rectangle bgC = new Rectangle(newWidth/3, newHeight, Color.color(0.2,0.2,0.2,0.75));
        RightSideBar sbCure = new RightSideBar(newWidth/3,newWidth-(newWidth/3), bgC);
        cureBtn.setOnAction(e -> sbCure.animate());

        sbVirus.setTranslateX(0);
        sbCure.setTranslateX(newWidth);

        game.getChildren().addAll(sbVirus, sbCure);
        game.setTranslateX(0);
        
        VBox root = new VBox(game, btBar);

        //SORTIE DU MEGA CHANTIER

////////////////////////////////////////////////
        
        newHeight += 50;
        Scene finalScene = new Scene(root, newWidth, newHeight);
        primaryStage.setScene(finalScene);
        /*primaryStage.setResizable(false);
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                primaryStage.setMaximized(false);
        });*/
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

    public Circle[] getCountryCircle(Country c, ZoomableScrollPane pane) {
        Circle[] circ = new Circle[2];
        circ[0] = new Circle(-1,-1,-1);
        circ[1] = new Circle(-1,-1,-1);

        if(pane.getZoomWidth()/400 < 1/(0.1*c.size())){
            return circ;
        }
        circ[0] = new Circle(c.coordinates()[0], c.coordinates()[1], c.getCircleWidth() + 3 , Color.BLACK );
        circ[1] = new Circle(c.coordinates()[0], c.coordinates()[1], c.getCircleWidth() , c.getColorFromCountry());

        circ[0].setMouseTransparent(true);
        circ[1].setMouseTransparent(true);

        return circ;
    }
}
