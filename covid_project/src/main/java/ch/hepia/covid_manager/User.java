package ch.hepia.covid_manager;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Insets;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class User{

    private int id;
    private String username;
    private Button button = new Button();

    public User(int id, String username){

        this.id = id;
        this.username = username;

    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public int getUserId(){
        return this.id;
    }

}