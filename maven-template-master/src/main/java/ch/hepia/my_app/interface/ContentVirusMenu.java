package ch.hepia.my_app;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.*;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.Priority;


import javafx.scene.Group;

import javafx.scene.input.MouseEvent;

import javafx.scene.Parent;

import javafx.scene.shape.Line;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.util.Duration;

class ContentVirusMenu extends Group{

    private LinkedHashMap<Button, Perk> map;
    private List<Line> lines;

    private Button infectivity;
    private Button lethality;
    private Button resistance;
    private Label infos;

    private Virus virus;
    private int status;

    private HBox topSection;
    private VBox currentMenu;
    private VBox buttonMenu;

    ContentVirusMenu(LinkedHashMap<Button, Perk> map, Virus v, int width, int height){       
        this.lines = new ArrayList<>();
        this.topSection = new HBox();
        this.currentMenu = new VBox();
        this.buttonMenu = new VBox();
        this.status = 1;
        
        this.infos = new Label();
        this.infos.setWrapText(true);
        this.infos.setMinWidth(200);
        this.infos.setMinHeight(40);
        this.infos.setStyle("-fx-font-size: 2em;");
        //this.infos.setStyle("-fx-font-color: white;");   // marche pas je comprends pas pourquoi
        this.infos.setTranslateX(width / 2 - 125);
        this.infos.setTranslateY(height - 400);

        this.map = map;
        this.virus = v;

        for(Button b: map.keySet()){
            Tooltip t = new Tooltip(map.get(b).description());
            t.setShowDelay(new Duration(500));
            t.setHideDelay(new Duration(50));
            b.setTooltip(t);
        }

        this.generateMenuHeader();
        this.generatePerkButtons();
        this.generateLines();

        this.currentMenu.getChildren().add(this.buttonMenu);
        this.currentMenu.getChildren().add(this.infos);        
        this.getChildren().add(this.currentMenu);
    }

    private void generateLines(){
        // changer les valeurs ici -> rendre dynamique
        for(int i = 0; i < 4; i+=1){
            double x1 = (20 + 1*120);
            double x2 = (20 + 2*120);
            double x3 = (20 + 3*120);
            double y = (125 + i*160.5);

            Line lineb1b2 = new Line(x1,y, x2,y);
            lineb1b2.setStrokeWidth(5);
            lineb1b2.setViewOrder(10);
            
            Line lineb2b3 = new Line(x2,y,x3,y);
            lineb2b3.setStrokeWidth(5);  
            lineb2b3.setViewOrder(10);

            this.lines.add(lineb1b2);
            this.lines.add(lineb2b3);
        }
    }

    public void updateLines(){
        this.getChildren().removeAll(this.lines);
        int offset = 0;
        //Index des boutons sur la page
        for(int i = 0 ; i < 8 ; i+=2){
            this.colorLines(this.lines.get(i), this.lines.get(i+1), (this.status-1)*12 + i + offset);
            offset +=1;
        }
        this.getChildren().addAll(this.lines);
    }


    private void colorLines(Line l1, Line l2, int i){
        List<Button> buttons = generateButtonList();
        
        if(this.virus.hasPerk(this.map.get(buttons.get(i)))){
            l1.setStroke(Color.WHITE);
        }else{
            l1.setStroke(Color.BLACK);
        }

        if(this.virus.hasPerk(this.map.get(buttons.get(i+1)))){
            l1.setStroke(Color.GREEN);
            l2.setStroke(Color.WHITE);
        }else{
            l2.setStroke(Color.BLACK);
        }
        
        if(this.virus.hasPerk(this.map.get(buttons.get(i+2)))){
            l2.setStroke(Color.GREEN);
        }
        //Perk le plus a gauche est débloqué
    }

    private void updateButtonStates(){
        List<Button> buttons = generateButtonList();
        buttons.forEach(b->{
            if(this.virus.hasEnoughPoints(map.get(b))){
                b.setStyle("-fx-background-color: white;");
                b.setOnAction(e -> {
                    this.virus.upgrade(map.get(b));
                    this.generatePerkButtons();
                    this.refreshDisplay();
                    this.infos.setText("New perk purchased!");
                });
            //On n'a pas assez de points
            }else{
                b.setStyle("-fx-background-color: grey;");
                b.setOnAction(e -> {
                    this.infos.setText("Not enough points!");
                });
            }
            //On a déjà le perk
            if(this.virus.hasPerk(this.map.get(b))){
                b.setStyle("-fx-background-color: green;");
                b.setOnAction(e -> {
                    this.infos.setText("Perk already unlocked!");
                });
            }
            int index = buttons.indexOf(b);
            //Ce n'est pas un des boutons de gauche
            if(index % 3 != 0){
                if(!this.virus.hasPerk( map.get(buttons.get(index - 1)) ) ){
                    b.setStyle("-fx-background-color: grey;");
                    b.setOnAction(e -> {
                        this.infos.setText("Need to unlock previous perk!");
                    });
                }
            }
        });        
    }

    public void refreshDisplay(){
        this.updateButtonStates();
        this.updateLines();
    }



    private List<Button> generateButtonList(){
        List<Button> buttons = new ArrayList<>();
        for(Button b: map.keySet()){
            buttons.add(b);
        }
        return buttons;
    }


    private Button generateMenuButtons(String label, int pos){
        Button b = new Button(label);
        // marche pas je comprends pas pourquoi
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
                this.refreshDisplay();
            }
        });
        topSection.getChildren().add(this.infectivity);
        
        this.lethality = generateMenuButtons("Lethality", 2);
        this.lethality.setOnAction(e -> {
            if(this.status != 2){
                this.status = 2;
                this.generatePerkButtons();
                this.refreshDisplay();
            }
        });
        topSection.getChildren().add(this.lethality);
        
        this.resistance = generateMenuButtons("Resistance", 3);
        this.resistance.setOnAction(e -> {
            if(this.status != 3){
                this.status = 3;
                this.generatePerkButtons();
                this.refreshDisplay();
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
        this.buttonMenu.getChildren().add(this.infos);    
        this.buttonMenu.setViewOrder(0);
    }

    public void updateLabel(){
        this.infos.setText("");
    }
}