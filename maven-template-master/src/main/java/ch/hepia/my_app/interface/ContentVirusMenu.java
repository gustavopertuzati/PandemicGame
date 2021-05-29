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

import javafx.scene.Group;


import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javafx.scene.Parent;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.geometry.Rectangle2D;
import javafx.geometry.Point2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Bounds;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.util.Duration;

import java.time.LocalDate;
import javafx.scene.layout.GridPane;

class ContentVirusMenu extends Group{
    
    private LinkedHashMap<Button, Perk> map;

    private Button infectivity;
    private Button lethality;
    private Button resistance;
    
    private Virus virus;
    private int status = 1;

    private HBox topSection = new HBox();
    private VBox currentMenu = new VBox();
    private VBox buttonMenu = new VBox();

    ContentVirusMenu(LinkedHashMap<Button, Perk> map, Virus v){        
        this.map = map;
        this.virus = v;

        List<Button> buttons = this.generateButtonList();
        this.generateMenuHeader();


        this.generatePerkButtons();

        this.currentMenu.getChildren().add(this.buttonMenu);
        this.getChildren().add(this.currentMenu);
    }
    
    // peut etre stocker les lignes dans une liste de ligne au début et on 
    // aura juste à modifier la couleur au lieu de calculer les coordonnées
    public void updateMenuContent(){
        List<Button> buttons = this.generateButtonList();        
        for(int i = 12*(this.status-1); i < 12*(this.status); i+=3){
            double x1 = (20 + 1*(buttons.get(i).getWidth()) );
            double x2 = (20 + 2*(buttons.get(i+1).getWidth()) );
            double x3 = (20 + 3*(buttons.get(i+2).getWidth()) );
            double y = (125 + 53.5*(i%12));

            if(x1 == x2 && x2 == x3){
                continue;
            }

            Line lineb1b2 = new Line(x1,y, x2,y);
            lineb1b2.setStroke(Color.BLACK);
            lineb1b2.setStrokeWidth(5);
            lineb1b2.setViewOrder(10);
            
            Line lineb2b3 = new Line(x2,y,x3,y);
            lineb2b3.setStroke(Color.WHITE);
            lineb2b3.setStrokeWidth(5);  
            lineb2b3.setViewOrder(10);
            this.getChildren().addAll(lineb1b2, lineb2b3);
        }
    }

    private List<Button> generateButtonList(){
        List<Button> buttons = new ArrayList<>();
        for(Button b: map.keySet()){
            buttons.add(b);
            b.setDisable(this.virus.hasEnoughPoints(map.get(b)));
            b.setOnAction(e -> 
                this.virus.update(map.get(b))
            );
        }
        return buttons;
    }
    
    private Button generateMenuButtons(String label, int pos){
        Button b = new Button(label);
        b.setStyle("-fx-border-color: #fff;");
        b.setStyle("-fx-border-width: 0;");
        b.setStyle("-fx-background-radius: 0;");
        b.setStyle("-fx-background-color: transparent;");
        b.setStyle("-fx-font-size: 1.5em;");
        b.setStyle("-fx-font-color: #fff;");
        b.setStyle("-fx-border-radius: 5px;");
        b.setTranslateX(50 * pos);    
        return b;
    }

    private void generateMenuHeader(){
        this.infectivity = generateMenuButtons("Transmission", 1);
        this.infectivity.setOnAction(e -> {
            if(this.status != 1){
                this.status = 1;
                this.generatePerkButtons();
                this.updateMenuContent();
            }
        });
        topSection.getChildren().add(this.infectivity);
        
        this.lethality = generateMenuButtons("Lethality", 2);
        this.lethality.setOnAction(e -> {
            if(this.status != 2){
                this.status = 2;
                this.generatePerkButtons();
                this.updateMenuContent();
            }
        });
        topSection.getChildren().add(this.lethality);
        
        this.resistance = generateMenuButtons("Resistance", 3);
        this.resistance.setOnAction(e -> {
            if(this.status != 3){
                this.status = 3;
                this.generatePerkButtons();
                this.updateMenuContent();
            }
        });
        topSection.getChildren().add(this.resistance);
        this.currentMenu.getChildren().add(topSection);
        this.currentMenu.setMargin(topSection, new Insets(10, 0, 60, 30));
    }



    private void generatePerkButtons(){
        List<Button> buttons = this.generateButtonList();
        this.buttonMenu.getChildren().clear();

        for(int i = 0; i < 4; i++){
            HBox tmp = new HBox();
            for(int j = 0; j < 3; j++){
                Button b = buttons.get(i*3+j + 12*(this.status - 1));
                if(b.getTranslateX() == 0.0 && b.getTranslateY() == 0){
                    b.setTranslateX((b.getWidth() + 30 * j)+1);
                    b.setTranslateY((b.getHeight() + 100 * i)+1);
                }
                tmp.getChildren().add(b);
            }
            this.buttonMenu.getChildren().add(tmp);
            this.buttonMenu.setMargin(tmp, new Insets(0, 0, 0, 30));
        }
        this.buttonMenu.setViewOrder(0);
    }
}