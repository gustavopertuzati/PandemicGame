package ch.hepia.covid_manager;

import javafx.application.Application;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Screen;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;


import javafx.scene.text.Font;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.CornerRadii;


import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Insets;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class LoginWindow extends HBox{

    private User userT = new User(0, "ThomasKek");
    private User userA = new User(1, "Banantwan");
    private User userG = new User(2, "Amon-Gus");

    private Stage secondStage = new Stage();
    
    private boolean loggedIn = false;
    
    public LoginWindow(){}
    
    public void loginScene() {

        VBox loginT = this.createUserVBox(this.userT, "images/user1.png");
        VBox loginA = this.createUserVBox(this.userA, "images/user2.png");
        VBox loginG = this.createUserVBox(this.userG, "images/user3.png");

        loginT.setAlignment(Pos.CENTER);
        loginA.setAlignment(Pos.CENTER);
        loginG.setAlignment(Pos.CENTER);

        HBox loginHBox = new HBox(); 
        loginHBox.getChildren().addAll(loginT, loginA, loginG); 
        loginHBox.setAlignment(Pos.CENTER);

        Label label = new Label("Who's playing?");
        label.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 64px;");
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(1400);


        VBox loginVBox = new VBox();
        loginVBox.getChildren().addAll(label, loginHBox);
        loginVBox.setAlignment(Pos.CENTER);
        loginVBox.setSpacing(30);

        //////// Logo à mettre ici ////////

        Label logo = new Label("COVID");
        logo.setStyle("-fx-text-fill: #59117c; -fx-font-size: 64px;");
        logo.setAlignment(Pos.TOP_LEFT);

        BorderPane loginRoot = new BorderPane();
        loginRoot.setTop(logo);
        loginRoot.setCenter(loginVBox);
        loginRoot.setPrefSize(1200, 800);
        Color begin = Color.rgb(10,10,10);
        Color end = Color.rgb(20,20,20);
        Stop[] stops = new Stop[] { new Stop(0, begin), new Stop(1, end)};
        LinearGradient lg1 = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);
        loginRoot.setBackground(new Background(new BackgroundFill(lg1, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(loginRoot); 
        this.secondStage.setScene(scene); 
        this.secondStage.setTitle("login page");
        this.secondStage.show();

    }

    public VBox createUserVBox(User user, String path){

        VBox vbox = new VBox();

        Image img = new Image(this.getClass().getClassLoader().getResourceAsStream(path));
        ImageView icon = new ImageView(img);
        icon.setFitWidth(135);
        icon.setFitHeight(135);
        icon.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                secondStage.close();
                event.consume();
                GameWindow game = new GameWindow(user.getUserId());
            }
        });
        icon.setOpacity(0.75);
        
        Label label = new Label(user.getUsername());
        label.setStyle("-fx-text-fill: #808080; -fx-font-size: 16px;");
        
        vbox.getChildren().addAll(icon, label);
        
        return vbox;
        
    }

    public void loginClose(){
        secondStage.close();
    }
    
    public User getUserById(int id){
        switch(id){
            case 0:
                return userT;
            case 1:
                return userA;
            case 2:
                return userG;
            default:
                return userT;
        }
    }
}