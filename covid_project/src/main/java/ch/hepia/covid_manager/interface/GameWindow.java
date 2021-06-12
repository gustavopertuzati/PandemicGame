package ch.hepia.covid_manager;

import java.util.Scanner;

import java.util.Map;
import java.util.Observable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ComboBox;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.skin.ComboBoxListViewSkin;


import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

import javafx.animation.Animation;
import javafx.animation.Transition;

import javafx.application.Application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.binding.DoubleBinding;

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
import javafx.scene.control.ListView;

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

import javafx.scene.control.MenuButton;

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

import java.sql.ResultSet;

import javafx.util.Duration;

import javafx.scene.control.Label;
import java.time.LocalDate;

import javafx.stage.StageStyle;

import java.lang.Thread;

import javafx.collections.FXCollections;

public class GameWindow extends Stage{

    LocalDate ld;
    
    public GameWindow(int idPlayer, String playerName){
        System.out.println("\n\n\n=======\n" + idPlayer + "\n=======\n\n\n");
        //this.setResizable(false);
        Virus v = Virus.getInstance();
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
        
        //Barre des cas sains
        Rectangle healthyBar = new Rectangle(newWidth/3, 20.0, Color.GREEN);
        healthyBar.opacityProperty().set(0.75);
        healthyBar.setStroke(Color.WHITE);
        healthyBar.setStrokeWidth(3.0);
        healthyBar.setTranslateX(newWidth/3);
        healthyBar.setTranslateY(newHeight-47);
        healthyBar.setArcWidth(30.0);        
        healthyBar.setArcHeight(20.0);
        
        //Barre des cas infectés
        Region sickBar = new Region();
        sickBar.opacityProperty().set(0.75);
        sickBar.setPrefSize((newWidth/(3.0 * countries.totalPop() / 100000.0)) * (countries.totalCases() / 100000.0), 16.0);
        sickBar.setStyle("-fx-background-color: red; -fx-background-radius: 10 0 0 10");
        sickBar.setTranslateX(newWidth/3 + 2);
        sickBar.setTranslateY(newHeight-45);
        
        //Barre des cas morts
        Region deathBar = new Region();
        deathBar.setPrefSize((newWidth/(3.0 * countries.totalPop() / 100000.0)) * (countries.totalDeaths() / 100000.0), 16.0);
        deathBar.setStyle("-fx-background-color: black; -fx-background-radius: 10 0 0 10");
        deathBar.setTranslateX(newWidth/3 + 2);
        deathBar.setTranslateY(newHeight-45);

        //Barre des cas vaccinés
        Region vaccinatedBar = new Region();
        vaccinatedBar.opacityProperty().set(0.75);
        vaccinatedBar.setPrefSize(0.0, 16.0); //je mets 0 parce qu'on a pas de données
        vaccinatedBar.setStyle("-fx-background-color: blue; -fx-background-radius: 0 10 10 0");
        vaccinatedBar.setTranslateX(2*newWidth/3 - 62.0);
        vaccinatedBar.setTranslateY(newHeight-45);

        Label barName = new Label("World");
        barName.setStyle("-fx-font-size: 1.4em;");
        barName.setTextFill(Color.WHITE);
        barName.setTranslateX(newWidth/3 + 10);
        barName.setTranslateY(newHeight - 73);

        //On récupère pour chaque pays, le cercle qui le représente ainsi que son cercle contour noir
        Map<Country,Circle[]> countryCirclesMap = countries.getCountryCirclesMap((e, c) ->  {
            NewStage ct = new NewStage(c, this, e.getScreenX(), e.getScreenY(), ld);
            sickBar.setPrefSize((newWidth/(3.0 * c.totalPopulation() / 10000.0)) * (c.playerTotalCases() / 10000.0), 16.0);
            deathBar.setPrefSize((newWidth/(3.0 * c.totalPopulation() / 10000.0)) * (c.playerTotalDeaths() / 10000.0), 16.0 );
            barName.setText(c.countryName());
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

        //String options[] = {"Save", "Save and exit", "Exit"};
        MenuItem menuItem1 = new MenuItem("Save");
        menuItem1.setStyle("-fx-background-color: transparent; padding:100%;");
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //DataBaseCommunicator.save(v, countries, idPlayer);
            }
        });

        MenuItem menuItem2 = new MenuItem("Save and exit");
        menuItem2.setStyle("-fx-background-color: transparent;");
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //DataBaseCommunicator.save(v, countries, idPlayer);
                System.exit(0);
            }
        });

        MenuItem menuItem3 = new MenuItem("Exit");
        menuItem3.setStyle("-fx-background-color: transparent;");
        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });

        MenuButton optionBox = new MenuButton("", null, menuItem1, menuItem2, menuItem3);
        Image img = new Image(this.getClass().getClassLoader().getResourceAsStream("images/bars.png"));
        ImageView menuIcon = new ImageView(img);
        menuIcon.setFitWidth(50);
        menuIcon.setFitHeight(50);
        optionBox.setWrapText(true);
        optionBox.setMinWidth(75);
        optionBox.setMinHeight(75);
        optionBox.setGraphic(menuIcon);
        optionBox.setStyle("-fx-background-color: transparent;");

        game.getChildren().addAll(scroller, optionBox);

        BottomBar btBar = new BottomBar();
        btBar.setSpacing(30);        
        btBar.setAlignment(Pos.BOTTOM_CENTER);
        btBar.setPrefWidth(newWidth);
        btBar.setMinHeight(40);
                
        ContentCureMenu ccm = new ContentCureMenu(countries, v, newWidth/3, newHeight);
        Button cureBtn = btBar.buttonCure();
        LeftSideBar sbCure = new LeftSideBar(newWidth/3,0, cureBtn, newHeight, ccm);
        Image cureImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/menuCure.png"));
        sbCure.setBackground(new Background(new BackgroundFill(new ImagePattern(cureImage), CornerRadii.EMPTY, Insets.EMPTY)));
        
        ContentVirusMenu cvm = new ContentVirusMenu(buttonsPerksmap, v, newWidth/3, newHeight);
        Image virusImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/menuVirus.png"));        
        ImageView iVvirus = new ImageView(virusImage);
        iVvirus.setFitWidth(newWidth/3);
        iVvirus.setFitHeight(newHeight);
        iVvirus.setOpacity(0.9);
        Group g = new Group();
        g.getChildren().addAll(iVvirus, cvm);
        Button virusBtn = btBar.buttonVirus();
        RightSideBar sbVirus = new RightSideBar(newWidth/3, newWidth-(newWidth/3), g);

        virusBtn.setOnAction(e -> {
            sbVirus.animate(sbCure.isAnimating(), sbCure);
            cvm.refreshDisplay();
            
        });
        cureBtn.setOnAction(e ->{
            sbCure.animate(sbVirus.isAnimating(),sbVirus );
            // je sais pas comment faire mais le add va pas la sinon on ajoute a chaque fois
            // il faudra faire des updates ici -> pour les labels et les graphs
            ccm.refreshDisplay();
        });
        sbVirus.setTranslateX(newWidth);
        
        game.getChildren().addAll(sbVirus, barName, healthyBar, sickBar, deathBar, vaccinatedBar, sbCure);
        scroller.setTranslateX(0);

        VBox root = new VBox(game,btBar);
        newHeight += 50;
        Scene finalScene = new Scene(root, newWidth, newHeight);
        
        this.setScene(finalScene);
        //this.setResizable(false);
        this.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                this.setMaximized(false);
        });

        Rewards.addRewardCirclesToBox(box, countries, v, 15 );
        

        //How many seconds for a day to elapse in seconds
        int speed = 10;

        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(1), e ->{
                ld = ld.plusDays(1);
                elapseDayForGame(countries, btBar, ld);
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();

        v.addListener(btBar);

        v.addPoint();
        v.addPoint();
        v.addPoint();
        v.addPoint();
        v.addPoint();

        /*
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/";
        try{
            DataBaseCommunicator dbc = new DataBaseCommunicator(driver, url, "root", "root");
            System.out.println(dbc.executeQuery("USE covid"));
            // faire une transaction si on veut insert
            ResultSet rs = dbc.executeQuery("SELECT * FROM Country");
            if (rs.next()){
                System.out.println(rs.getString(1));
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        */
        this.initStyle(StageStyle.UNDECORATED);

        this.show();
    }

    public void elapseDayForGame(Countries c, BottomBar b, LocalDate ld){
        b.updateDate(ld);
        c.elapseDayForAllCountries();
        c.updateWorldHistory(ld);
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