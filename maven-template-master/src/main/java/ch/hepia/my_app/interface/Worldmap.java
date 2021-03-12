package ch.hepia.my_app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;

class NewStage {

    NewStage(Country c)
    {
        Stage subStage = new Stage();
        subStage.setTitle(c.CountryName());

        Text text = new Text("\n\n" + c.toString());

        Scene scene = new Scene(new Pane(text), 300, 200);
        
        subStage.setScene(scene);
        subStage.show();
    }
}