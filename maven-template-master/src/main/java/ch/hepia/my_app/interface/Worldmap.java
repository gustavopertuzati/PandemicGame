package ch.hepia.my_app;

import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.util.Duration;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

class NewStage {

    NewStage(Country c, Stage primaryStage)
    {
        Stage subStage = new Stage();
        subStage.setTitle(c.CountryName());

        Text text = new Text("\n\n" + c.toString());

        Button btn1 = new Button();
        btn1.setText("More options");
        
        Button btn2 = new Button();
        btn2.setText("Exit");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);;
            }
        });
        
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("To Be Coded...");
            }
        });

        VBox root = new VBox();
        
        root.getChildren().add(text);
        
        btn1.setLayoutX(900);
        btn1.setLayoutY(530);
        root.getChildren().add(btn1);
        
        btn2.setLayoutX(900);
        btn2.setLayoutY(630);
        root.getChildren().add(btn2);

        root.setAlignment(Pos.CENTER);
        VBox.setMargin(text, new Insets(10, 10, 10, 10));
        VBox.setMargin(btn2, new Insets(10, 10, 10, 10));
        VBox.setMargin(btn1, new Insets(10, 10, 10, 10));

        Scene scene = new Scene(root, 1024, 800);
        
        subStage.setScene(scene);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.initOwner(primaryStage);
        subStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                subStage.hide();
            }
        });

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
 
        //set Stage boundaries to the lower right corner of the visible bounds of the main screen
        subStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 400);
        subStage.setY(primaryScreenBounds.getMaxY() - primaryScreenBounds.getHeight() - 300);
        subStage.setWidth(400);
        subStage.setHeight(900);

        subStage.show();
    }
}