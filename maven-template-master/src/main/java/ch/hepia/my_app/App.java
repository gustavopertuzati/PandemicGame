package ch.hepia.my_app;

import java.util.Scanner;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

import javafx.animation.Animation;
import javafx.animation.Transition;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

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
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

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

    /* TODO:
     * -> faire les boutons propre avec la description (voir le site de plague inc) et implémenter le menu de vaccin (à gauche)
     * -> addapter la progress bar dans la bottom bar quand on a des points (design pattern observer)
     * -> belle mise en forme du menu (boutons propres + fond plus stylé)
     * -> revoir les boutons de la bottombar

     * -> clarifier le code dans le chantier
     * -> modifier un peu les classes pour que ce soit plus modulaire -> surtout contentVirusMenu #Maille
     * -> commencer a faire un affichage dynamique (ex: l'espace entre les boutons dans le menu n'est plus une constante mais dépend de newWidth et newHeight)

     * GUS -> faire la barre des cas
     */

    LocalDate ld;

    @Override
    public void start(Stage primaryStage) {
        //primaryStage.setResizable(false);
        Virus v = new Virus();
        ld = LocalDate.of(2020,01,22);

        // TEMPORAIRE : AJOUT DES PERKS ICI
        Perks perks = new Perks();
        perks.init();
        LinkedHashMap buttonsPerksmap = perks.buttonsPerksMap();
        //

        APICountryManager test = new APICountryManager("https://api.covid19api.com");
        Countries countries = test.getCountries("summary");

        Image worldImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/final_map.png"));
        int newWidth = 1420;
        int newHeight = (int)(worldImage.getHeight() * newWidth / worldImage.getWidth());
        
        ImageView iV = new ImageView(worldImage);
        

        //On récupère pour chaque pays, le cercle qui le représente ainsi que son cercle contour noir
        Map<Country,Circle[]> countryCirclesMap = countries.getCountryCirclesMap((e, c) ->  {
            NewStage ct = new NewStage(c, primaryStage, e.getScreenX(), e.getScreenY());
        });


        Group box = new Group();

        box.getChildren().add(iV);
        
        
        ZoomableScrollPane scroller = new ZoomableScrollPane(box, newWidth, newHeight);
        

        //On ajoute les cercles initiaux a la carte
        adjustCircles(countryCirclesMap, scroller.getZoomWidth(),box );

        scroller.getContent().addEventHandler(ScrollEvent.ANY, e->{
            //Le zoom
            scroller.onScroll(e.getDeltaY(), new Point2D(e.getX(), e.getY()));

            //La suppression et l'ajout des cercles en fonction du niveau du zoom
            adjustCircles(countryCirclesMap, scroller.getZoomWidth(),box );
            e.consume();
        });

        //Parametres du ZoomableScrollPane
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
        
/////////////////////////////////////////////////
        
        // Sidebar left
        
        // on va avoir besoin de ca pour les labels et tout dans cure
        //BorderPane virusContentPane = new BorderPane();
        //virusContentPane.getChildren().add();
        
        Button cureBtn = btBar.buttonCure();
        LeftSideBar sbCure = new LeftSideBar(newWidth/3,0, cureBtn, newHeight);
        Image cureImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/menuCure.png"));
        //cureImage.setOpacity(0.9);

        sbCure.setBackground(new Background(new BackgroundFill(new ImagePattern(cureImage), CornerRadii.EMPTY, Insets.EMPTY)));
        
        
        // Sidebar right
        Image virusImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/menuVirus.png"));        
        ImageView iVvirus = new ImageView(virusImage);
        iVvirus.setFitWidth(newWidth/3);
        iVvirus.setFitHeight(newHeight);
        iVvirus.setOpacity(0.9);
        Group g = new Group();

        ContentVirusMenu cvm = new ContentVirusMenu(buttonsPerksmap, v, newWidth/3, newHeight);
        g.getChildren().addAll(iVvirus, cvm);

        Button virusBtn = btBar.buttonVirus();
        RightSideBar sbVirus = new RightSideBar(newWidth/3, newWidth-(newWidth/3), g);

        virusBtn.setOnAction(e -> {
            sbVirus.animate( sbCure.isAnimating(), sbCure);
            cvm.updateLabel();
            cvm.refreshDisplay();
            
        });
        cureBtn.setOnAction(e ->{
            sbCure.animate(sbVirus.isAnimating(),sbVirus );
            //cvm.updateMenuContent();
        });
        sbVirus.setTranslateX(newWidth);
        
////////////////////////////////////////////////

        game.getChildren().addAll(sbVirus, sbCure);
        scroller.setTranslateX(0);
        
        VBox root = new VBox(game, btBar);
        
        newHeight += 50;
        Scene finalScene = new Scene(root, newWidth, newHeight);
        primaryStage.setScene(finalScene);
        /*primaryStage.setResizable(false);
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                primaryStage.setMaximized(false);
        });*/

        Rewards.addRewardCirclesToBox(box, countries, v, 15 );
        

        //How many seconds for a day to elapse in seconds
        int speed = 10;

        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(1), e ->{
                ld = ld.plusDays(1);
                elapseDayForGame(countries, btBar, ld);
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();


        primaryStage.show();
    }


    public void elapseDayForGame(Countries c, BottomBar b, LocalDate ld){
        b.updateDate(ld);
        c.elapseDayForAllCountries();
    }


    public void adjustCircles(Map<Country, Circle[]> map, double zoomWidth, Group container){
        for(Country c : map.keySet()){
            if(zoomWidth/400 < 1/(0.1*c.size())){
                //Si le pays n'est pas assez grand pour etre affiché, et qu'on l'affiche, on l'enlève
                if(container.getChildren().contains(map.get(c)[1]))
                    container.getChildren().removeAll(map.get(c)[0], map.get(c)[1]);
            }else{
                //Si le pays est assez grand pour etre affiché, et qu'il n'est pas déjà affiché, on l'affiche
                if(!container.getChildren().contains(map.get(c)[1])){
                    container.getChildren().addAll(map.get(c)[0], map.get(c)[1]);
                }
            }
        }
    }

}