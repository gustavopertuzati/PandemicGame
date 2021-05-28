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
import javafx.scene.layout.GridPane;

class ContentVirusMenu extends HBox{
    
    private LinkedHashMap<Button, Perk> map;
    private Button infectivity;
    private Button lethality;
    private Button resistance;

    ContentVirusMenu(LinkedHashMap<Button, Perk> map, Virus v){        
        this.map = map;

        List<Button> buttons = new ArrayList<>();
        for(Button b: map.keySet()){
            buttons.add(b);
            b.setDisable(!v.hasEnoughPoints(map.get(b)));
            b.setOnAction(e -> 
                //Acquerir le perk
                v.update(map.get(b))
            );
        }

        GridPane gridPane = new GridPane();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                gridPane.add(buttons.get(j*3+i), j, i, 1, 1);
            }
        }
        gridPane.setHgap(60);
        gridPane.setVgap(80);
        this.getChildren().add(gridPane);
    }
}