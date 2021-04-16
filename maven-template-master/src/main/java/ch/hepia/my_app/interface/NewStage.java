package ch.hepia.my_app;

import javafx.application.Application;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Screen;
import javafx.stage.Modality;
import javafx.stage.Window;

import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Insets;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

class NewStage {

    NewStage(Country c, Stage primaryStage, double x, double y){
        Stage detailStage = new Stage();
        detailStage.setTitle(c.countryName());

        Text countryDetail = new Text("\n\n" + c.toString());
        Text countryMoreDetail = new Text("\n\n" + c.toString());

        Boolean sw = true;
        Button btn1 = new Button();
        btn1.setText("More informations");
        btn1.setOnAction(aevent -> { toggleStage(detailStage); });

        Button btn2 = new Button();
        btn2.setText("X");
        btn2.setStyle("-fx-background-color: transparent; -fx-font-weight: bold");
        btn2.setOnAction(aevent -> {
            detailStage.hide();
        });
        

        VBox detailRoot = new VBox();
        detailRoot.getChildren().add(btn2);
        detailRoot.getChildren().add(countryDetail);
        detailRoot.getChildren().add(btn1);
        detailRoot.getChildren().add(countryMoreDetail);

        VBox.setMargin(btn2, new Insets(5, 5, 0, 220));
        VBox.setMargin(btn1, new Insets(5, 0, 0, 5));
        VBox.setMargin(countryDetail, new Insets(-45, 0, -15, 5));
        VBox.setMargin(countryMoreDetail, new Insets(0, 0, 0, 5));

        Scene detailScene = new Scene(detailRoot, 0, 0, Color.BLACK);

        detailStage.setScene(detailScene);
        detailStage.initModality(Modality.APPLICATION_MODAL);
        detailStage.initOwner(primaryStage);
        detailStage.setX(x);
        detailStage.setY(y);
        detailStage.setWidth(250);
        detailStage.setHeight(130);
        detailStage.initStyle(StageStyle.UNDECORATED);
        detailStage.setOpacity(0.8);
        
        detailStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                detailStage.hide();
            }
        });

        detailStage.show();
    }

    void toggleStage(Stage detailStage){ 
        if(detailStage.getHeight() == 260){
            detailStage.setHeight(130);
        }else{
            detailStage.setHeight(260);
        }
    }
}