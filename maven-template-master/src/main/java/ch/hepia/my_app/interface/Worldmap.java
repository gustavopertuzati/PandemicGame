package ch.hepia.my_app;


import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
/*
import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import jdk.internal.org.jline.terminal.MouseEvent;
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
import javafx.scene.paint.Color;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

*/

class NewStage extends VBox{

    public static BorderPane createSidebarContent(Country country){

        new EventHandler<ActionEvent>(){
            @Override 
            public void handle( ActionEvent actionEvent ){
                Text countryDetail = new Text("\n\n" + country.toString());
            }
        };
        changeLyric.fire();
        final BorderPane lyricPane = new BorderPane();
        lyricPane.setTop(changeLyric);
        return lyricPane;
    }

    NewStage(final double expandedWidth, Node... nodes){

        getStyleClass().add("sidebar");
        this.setPrefWidth(expandedWidth);
        this.setMinWidth(0);

        setAlignment(Pos.CENTER);
        getChildren().addAll(nodes);

        new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent){
// create an animation to hide sidebar.
                    final Animation hideSidebar = new Transition(){{
                            setCycleDuration(Duration.millis(250));
                        }

                        @Override
                        protected void interpolate(double frac){
                            final double curWidth = expandedWidth * (1.0 - frac);
                            setPrefHeight(curWidth);
                            setTranslateY(-expandedWidth + curWidth);
                        }
                    };
                    hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent actionEvent){
                            setVisible(false);
                        }
                    });
// create an animation to show a sidebar.
                    final Animation showSidebar = new Transition(){{
                            setCycleDuration(Duration.millis(250));
                        }

                        @Override
                        protected void interpolate(double frac){
                            final double curWidth = expandedWidth * frac;
                            setPrefHeight(curWidth);
                            setTranslateY(-expandedWidth + curWidth);
                        }
                    };
                    if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED){
                        if (isVisible()){
                            hideSidebar.play();
                        }
                        else{
                            setVisible(true);
                            showSidebar.play();
                        }
                    }
                }
            };
    }
}

/*
        Stage detailStage = new Stage();
        detailStage.setTitle(c.CountryName());

        Text countryDetail = new Text("\n\n" + c.toString());

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

        VBox detailRoot = new VBox();
        
        detailRoot.getChildren().add(countryDetail);
        
        btn1.setLayoutX(900);
        btn1.setLayoutY(530);
        detailRoot.getChildren().add(btn1);
        
        btn2.setLayoutX(900);
        btn2.setLayoutY(630);
        detailRoot.getChildren().add(btn2);

        detailRoot.setAlignment(Pos.CENTER);
        VBox.setMargin(countryDetail, new Insets(10, 10, 10, 10));
        VBox.setMargin(btn2, new Insets(10, 10, 10, 10));
        VBox.setMargin(btn1, new Insets(10, 10, 10, 10));

        Scene detailScene = new Scene(detailRoot, 800, 400, Color.BLACK);
        
        detailStage.setScene(detailScene);
        detailStage.initModality(Modality.APPLICATION_MODAL);
        detailStage.initOwner(primaryStage);
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
 
        //set Stage boundaries to the lower right corner of the visible bounds of the main screen
        detailStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 400);
        detailStage.setY(primaryScreenBounds.getMaxY() - primaryScreenBounds.getHeight() - 300);
        detailStage.setWidth(400);
        detailStage.setHeight(1000);
        /*
        Stage newsStage = new Stage();
        newsStage.setTitle("Latest news from " + c.CountryName());
        
        Text news = new Text("Bad news...");
        
        VBox newsRoot = new VBox();
        
        newsRoot.getChildren().add(news);
        
        newsRoot.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(news, new Insets(10, 10, 10, 10));
        
        Scene newsScene = new Scene(newsRoot, 1024, 800);
        
        newsStage.setScene(newsScene);
        newsStage.initModality(Modality.APPLICATION_MODAL);
        newsStage.initOwner(primaryStage);
        
        newsStage.setX(primaryScreenBounds.getMaxX() + primaryScreenBounds.getWidth() - 400);
        newsStage.setY(primaryScreenBounds.getMinY() - primaryScreenBounds.getHeight() - 300);
        newsStage.setWidth(1000);
        newsStage.setHeight(400);
        detailStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                detailStage.hide();
                //newsStage.hide();
            }
        });
        
        detailStage.show();
        */
        //newsStage.show();