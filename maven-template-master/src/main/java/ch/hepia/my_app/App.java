package ch.hepia.my_app;

import java.util.Scanner;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    /* TODO CE SOIR:

     * -> verifier qu'on peut refermer le menu de droite en cliquant sur le bouton de gauche pour ouvrir que 1 menu à la fois
     * -> swapper le menu virus a droite et vaccin à gauche (modifier aussi la bottom bar)

     * -> ajouter les boutons pour les onglets dans le menu du virus
     * -> faire bien les boutons avec les lignes qui les relient et qui changent de couleur
     * -> faire les boutons propre avec la description (voir le site de plague inc) et implémenter le menu de vaccin (à gauche)
     * -> changer les boutons de couleur quand on débloque la compétence et pareil pour les lignes
     * -> commencer à implémenter le fait qu'on puisse cliquer sur les boutons que si on a le nombre suffisant de points (addapter la progress bar dans la bottom bar)


     * -> clarifier le code dans le chantier
     * -> modifier un peu les classes pour que ce soit plus modulaire

     * -> commencer a faire un affichage dynamique (ex: l'espace entre les boutons dans le menu n'est plus une constante mais dépend de newWidth et newHeight)
     */

    @Override
    public void start(Stage primaryStage) {

        Virus v = new Virus();
        //primaryStage.setResizable(false);

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
        
/////////////////////////////////////////////////
        
        // Sidebar left
        
        // on va avoir besoin de ca pour les labels et tout dans cure
        //BorderPane virusContentPane = new BorderPane();
        //virusContentPane.getChildren().add();
        
        Button cureBtn = btBar.buttonCure();
        LeftSideBar sbCure = new LeftSideBar(newWidth/3,0, cureBtn, newHeight);
        Image cureImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/menuCure.png"));
        sbCure.setBackground(new Background(new BackgroundFill(new ImagePattern(cureImage), CornerRadii.EMPTY, Insets.EMPTY)));
        
        
        // Sidebar right
        Image virusImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/menuVirus.png"));        
        ImageView iVvirus = new ImageView(virusImage);
        iVvirus.setFitWidth(newWidth/3);
        iVvirus.setFitHeight(newHeight);
        Group g = new Group();
        g.getChildren().addAll(iVvirus, new ContentVirusMenu(buttonsPerksmap, v));

        Button virusBtn = btBar.buttonVirus();
        RightSideBar sbVirus = new RightSideBar(newWidth/3, newWidth-(newWidth/3), g);
        cureBtn.setOnAction(e -> sbVirus.animate( sbCure.isAnimating(), sbCure));
        virusBtn.setOnAction(e -> sbCure.animate(sbVirus.isAnimating(),sbVirus ));
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

        Rewards.addRewardCirclesToBox(box, countries, v, 4 );
        

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