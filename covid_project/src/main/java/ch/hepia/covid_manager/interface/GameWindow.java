package ch.hepia.covid_manager;

import java.util.Map;
import java.util.Observable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.lang.Thread;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Priority;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Circle;
import javafx.geometry.Point2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.StageStyle;

/*
    comprendre pourquoi on commence avec des cas négatifs au début
    verifier que les updates des pays se font bien (cases, cured, deaths, actives, recovered) -> verifier que le ratio est ok et que les graphs sont bons
    modifier un peut la manière dont on determine la couleur du cercle pour le pays (checker si les ratios sont cohérents)
    faire en sorte que le vaccin fonctionne (modifier le constructeur Cure() + gerer les updates des champs aux bons endroits, la barre du bas avec l'observer...)
    verifier que les interractions avec la base de données sont bonnes (sauvegardes + chargement + fonctionnement des procédures / triggers / vues)
    populate correctement la base de données (les bons coefs pour les perks ajout des virus...)
    clean le code dans GameWindow (si possible)
    faire des tests pour les notions critiques
    uniformiser le nom des fonctions
    remplacer les cure et virus par getInstance()
    ajouter les décisions prises par les pays quand les facteurs sont trop hauts (si le nombre de nouveau cas est trop élevé on cut un peu les champs du virus)
*/

public class GameWindow extends Stage{
    
    LocalDate ld;
    
    public GameWindow(int idPlayer, String playerName, boolean newGame){
        ld = LocalDate.of(2020,01,22);
        System.out.println(idPlayer + ": " + playerName + ", " + newGame + "\n\n");
        
        
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost";
        DataBaseCommunicator dbc;
        try{
            dbc = new DataBaseCommunicator(driver, url, "root", "root");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        Perks perks = new Perks();
        perks.init();
        

        if(!newGame){
            try{
                dbc.loadVirus(new User(idPlayer, User.getUserById(idPlayer)), perks).get();
                ld = dbc.loadDate(ld, new User(idPlayer, User.getUserById(idPlayer))).get();
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }



        Virus v = Virus.getInstance();
        //System.out.println(perks.listOfPerks());
        
        

        Countries countries = new Countries();
        dbc.loadCountries(new User(idPlayer, User.getUserById(idPlayer)), newGame).thenAccept(c ->{
            c.listOfCountries().forEach(i -> {
                countries.addCountry(i);
            });
        });

        LinkedHashMap buttonsPerksmap = perks.buttonsPerksMap();
        
        APICountryManager test = new APICountryManager("https://api.covid19api.com");
        Image worldImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/final_map.png"));
        int newWidth = 1420;
        int newHeight = (int)(worldImage.getHeight() * newWidth / worldImage.getWidth());        

        CasesBar cb = new CasesBar(newWidth, newHeight, countries);

        DropdownMenu optionBox = new DropdownMenu(v, countries, idPlayer, ld);
        optionBox.setLayoutX(10);
        optionBox.setLayoutY(10);

        ImageView iV = new ImageView(worldImage);
        iV.setOnMouseClicked(e ->{
            cb.setSickBar((newWidth/(3.0 * countries.totalPop() / 10000.0)) * (countries.totalCases() / 10000.0), 16.0);
            cb.setDeathBar((newWidth/(3.0 * countries.totalPop() / 10000.0)) * (countries.totalDeaths() / 10000.0), 16.0 );
            cb.setVaccinatedBar((newWidth/(3.0 * countries.totalPop() / 10000.0)) * (countries.totalCured() / 10000.0), 16.0 );
            cb.setBarName("World");
            optionBox.removeItems();
        });

        Map<Country,Circle[]> countryCirclesMap = countries.getCountryCirclesMap((e, c) ->  {
                NewStage ct = new NewStage(c, this, e.getScreenX(), e.getScreenY(), ld, cb, countries, newWidth, newHeight);
                cb.setSickBar((newWidth/(3.0 * c.totalPopulation() / 10000.0)) * (c.playerTotalCases() / 10000.0), 16.0);
                cb.setDeathBar((newWidth/(3.0 * c.totalPopulation() / 10000.0)) * (c.playerTotalDeaths() / 10000.0), 16.0 );
                cb.setVaccinatedBar((newWidth/(3.0 * countries.totalPop() / 10000.0)) * (countries.totalPop()/*countries.totalCured()*/ / 10000.0), 16.0 );
                cb.setBarName(c.name());
                optionBox.removeItems();
            });;
        
        Group box = new Group();
        box.getChildren().add(iV);
        
        ZoomableScrollPane scroller = new ZoomableScrollPane(box, newWidth, newHeight);        
        adjustCircles(countryCirclesMap, scroller.getZoomWidth(),box );

        scroller.getContent().addEventHandler(ScrollEvent.ANY, e->{
            //Le zoom
            scroller.onScroll(e.getDeltaY(), new Point2D(e.getX(), e.getY()));
            //La suppression et l'ajout des cercles en fonction du niveau du zoom
            adjustCircles(countryCirclesMap, scroller.getZoomWidth(),box );
            e.consume();
        });
        scroller.setPrefSize(newWidth, newHeight);
        scroller.setPannable(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.getStyleClass().add("edge-to-edge");
        
        Group game = new Group();
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
            optionBox.removeItems();            
        });

        cureBtn.setOnAction(e ->{
            sbCure.animate(sbVirus.isAnimating(),sbVirus );
            ccm.refreshDisplay();
            optionBox.removeItems();
            optionBox.manageDisplay();
        });

        sbVirus.setTranslateX(newWidth);
        
        game.getChildren().addAll(sbVirus, cb.getBarName(), cb.getHealthyBar(), cb.getSickBar(), cb.getDeathBar(), cb.getVaccinatedBar(), sbCure);
        scroller.setTranslateX(0);

        VBox root = new VBox(game,btBar);
        Scene finalScene = new Scene(root, newWidth-1, newHeight+50);
        
        this.setScene(finalScene);
        this.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                this.setMaximized(false);
        });        

        int speed = 1;
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(speed), e ->{
                ld = ld.plusDays(1);
                elapseDayForGame(countries, btBar, ld, newWidth, newHeight, v, box);
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        v.addListener(btBar);
        this.initStyle(StageStyle.UNDECORATED);
        this.show();
    }

    public void elapseDayForGame(Countries c, BottomBar b, LocalDate ld, int w, int h, Virus v, Group box){
        //System.out.println(ld.toString());
        b.updateDate(ld);
        c.elapseDayForAllCountries();
        c.updateWorldHistory(ld);
        if(c.getTotalDailyPoints() > 1){
            v.addPoint();
        }
        if(ld.getDayOfYear() % 10 == 0){
            Rewards.addRewardCirclesToBox(box, c, v, 2, w, h);
        }
    }

    public void adjustCircles(Map<Country, Circle[]> map, double zoomWidth, Group container){
        for(Country c : map.keySet()){
            if(zoomWidth/400 < 1/(0.1*c.size())){
                if(container.getChildren().contains(map.get(c)[1]))
                container.getChildren().removeAll(map.get(c)[0], map.get(c)[1]);
            }else{
                if(!container.getChildren().contains(map.get(c)[1])){
                    container.getChildren().addAll(map.get(c)[0], map.get(c)[1]);
                }
            }
        }
    }
}
