package ch.hepia.my_app;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

import javafx.animation.Animation;
import javafx.animation.Transition;

import javafx.application.Application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.Node;
import javafx.scene.text.Font;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.stage.Stage;
import javafx.scene.image.WritableImage;

import javafx.util.Duration;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class App extends Application{

    BorderPane bp = new BorderPane();
    public static void main(String[] args) {
        launch(args);
        
        /*APICountryManager test = new APICountryManager("https://api.covid19api.com");
        Countries lol = test.getCountries("summary");
        for(Country c : lol.listOfCountries()){
            System.out.println(c.toString());
        }*/
    }
    
    @Override
    public void start(Stage primaryStage) {

        bp.setStyle("-fx-background-color: #2f4f4f;");
        bp.setPrefSize(800, 600);
        
        APICountryManager test = new APICountryManager("https://api.covid19api.com");
        Countries lol = test.getCountries("summary");

        Image worldImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/final_map.png"));
        double width = worldImage.getWidth();
        double height = worldImage.getHeight();
        
        
        WritableImage writableImage =  new WritableImage((int)width, (int)height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        
        
        ScrollPane scroller = new ScrollPane();

        Map<String, Integer[]> myMap = new HashMap<>();
        try{
            Scanner sc = new Scanner(this.getClass().getClassLoader().getResourceAsStream("countrycoords.txt")); 
            while (sc.hasNextLine()) {
             String[] tmpStr = sc.nextLine().split("/"); 
             Integer[] tmpDbl = {Integer.parseInt(tmpStr[1]), Integer.parseInt(tmpStr[2])};
              myMap.put(tmpStr[0], tmpDbl);
            } 
        }catch(Exception e){
            e.printStackTrace();
        }

        PixelReader pixelReader = worldImage.getPixelReader();
        for (int i = 0; i < (int)width; i++ ){
            for (int j = 0; j < (int)height; j++){
                pixelReader.getColor(i, j);
                pixelWriter.setColor(i, j, pixelReader.getColor(i, j));
            }
        }
    
        for ( String k : myMap.keySet()){
            for(int i = 0; i < 7; i++){
                for(int j = 0; j < 7; j++){
                    Integer[] coords = myMap.get(k);
                    pixelWriter.setColor(coords[0]+i, coords[1]+j, Color.rgb(255,0,0));
                    pixelWriter.setColor(coords[0]+i, coords[1]-j, Color.rgb(255,0,0));
                    pixelWriter.setColor(coords[0]-i, coords[1]+j, Color.rgb(255,0,0));
                    pixelWriter.setColor(coords[0]-i, coords[1]-j, Color.rgb(255,0,0));
                }
            }
        }
        
        
        //lol.listOfCountries().forEach( (Country i) -> pixelWriter.setColor((int)(i.Coordinates()[1]+60)*10+200, (int)(i.Coordinates()[0]+180)*10+100,  Color.rgb(255,0,0)));
        
        ImageView worldImageView = new ImageView(writableImage);

        double newWidth = 1500;
        double newHeight = 1500*height/width;

        scroller.setPrefSize(newWidth, newHeight);
        scroller.setPannable(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Scene scene = new Scene(scroller, newWidth, newHeight);
        scroller.setContent(worldImageView);
        worldImageView.setPickOnBounds(true);

        final Pane menuPane = createSidebarContent();
        SideBar sidebar = new SideBar(250, menuPane);

        final BorderPane layout = new BorderPane();

        StackPane st = new StackPane();

        st.getChildren().addAll(scroller, sidebar.getControlButton());
        st.setAlignment(Pos.TOP_RIGHT);

        HBox sidePanel = new HBox(scroller);
        Label countryLabel = new Label("Select country to show details");
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-font-size: 6pt; -fx-text-fill:red;");
        countryLabel.setGraphic(closeButton);
        countryLabel.setContentDisplay(ContentDisplay.LEFT);
        countryLabel.setBackground( new Background (new BackgroundFill(Color.rgb(0, 0, 80, 0.7), new CornerRadii(5.0), new Insets(-5.0))));
        worldImageView.setOnMouseClicked(e -> {
            try{
                Country crt = lol.getCountryByCoordinates(e.getX(), e.getY());
                countryLabel.setText("Country detail:\n\n" + crt.toString());
                countryLabel.setFont(new Font("Arial", 23));
                countryLabel.setTextFill(Color.web("#ffffff"));
                countryLabel.setMinWidth(Region.USE_PREF_SIZE);
                //NewStage ct = new NewStage(crt, primaryStage);
            }catch(Exception oops){}
        });
        
        sidePanel.getChildren().addAll(countryLabel, st);
        closeButton.setOnAction(event -> sidePanel.getChildren().remove(countryLabel));
        layout.setBottom(sidebar);
        layout.setCenter(sidePanel);
        Scene scene2 = new Scene(layout, newWidth, newHeight);


        primaryStage.setScene(scene2);

        primaryStage.show();
    }

    private BorderPane createSidebarContent()
    {
// create some content to put in the sidebar.
        final Button menu = new Button("Menu");
        menu.getStyleClass().add("change-lyric");
        menu.setMaxWidth(Double.MAX_VALUE);
        menu.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                System.out.println("Some action");
            }
        });
        menu.fire();
        final BorderPane menuPane = new BorderPane();
        menuPane.setTop(menu);
        return menuPane;
    }

    class SideBar extends VBox
    {
        /**
         * @return a control button to hide and show the sidebar
         */
        public Button getControlButton()
        {
            return controlButton;
        }
        private final Button controlButton;

        /**
         * creates a sidebar containing a vertical alignment of the given nodes
         */
        SideBar(final double expandedWidth, Node... nodes)
        {
            getStyleClass().add("sidebar");
            this.setPrefWidth(expandedWidth);
            this.setMinWidth(0);

// create a bar to hide and show.
            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(nodes);

// create a button to hide and show the sidebar.
            controlButton = new Button("Collapse");
            controlButton.getStyleClass().add("hide-left");

// apply the animations when the button is pressed.
            controlButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent)
                {
// create an animation to hide sidebar.
                    final Animation hideSidebar = new Transition()
                    {
                        {
                            setCycleDuration(Duration.millis(250));
                        }

                        @Override
                        protected void interpolate(double frac)
                        {
                            final double curWidth = expandedWidth * (1.0 - frac);
                            setPrefHeight(curWidth);
                            setTranslateY(-expandedWidth + curWidth);
                        }
                    };
                    hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent actionEvent)
                        {
                            setVisible(false);
                            controlButton.setText("Show");
                            controlButton.getStyleClass().remove("hide-left");
                            controlButton.getStyleClass().add("show-right");
                        }
                    });
// create an animation to show a sidebar.
                    final Animation showSidebar = new Transition()
                    {
                        {
                            setCycleDuration(Duration.millis(250));
                        }

                        @Override
                        protected void interpolate(double frac)
                        {
                            final double curWidth = expandedWidth * frac;
                            setPrefHeight(curWidth);
                            setTranslateY(-expandedWidth + curWidth);
                        }
                    };
                    showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent actionEvent)
                        {
                            controlButton.setText("Collapse");
                            controlButton.getStyleClass().add("hide-left");
                            controlButton.getStyleClass().remove("show-right");
                        }
                    });
                    if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED)
                    {
                        if (isVisible())
                        {
                            hideSidebar.play();
                        }
                        else
                        {
                            setVisible(true);
                            showSidebar.play();
                        }
                    }
                }
            });
        }
    }
    
}
