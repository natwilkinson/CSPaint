import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class ColorChooser extends Application {

  private double redVal, greenVal, blueVal;
  
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    Rectangle rect = new Rectangle(20, 20, 200, 200);
    rect.setFill(Color.BLACK);
    
    VBox vbox = new VBox();
    vbox.setPadding(new Insets(25, 25, 25, 25));
    //vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);

    Label redLabel = new Label("Red");
    Label greenLabel = new Label("Green");
    Label blueLabel = new Label("Blue");
    
    Slider redslider = new Slider(0.0, 255.0, 0.0);
    redslider.setShowTickLabels(true);
    redslider.setShowTickMarks(true);    
    redslider.setMajorTickUnit(50.0);
    
    Slider greenslider = new Slider(0.0, 255.0, 0.0);
    greenslider.setShowTickLabels(true);
    greenslider.setShowTickMarks(true);    
    greenslider.setMajorTickUnit(50.0);
    
    Slider blueslider = new Slider(0.0, 255.0, 0.0);
    blueslider.setShowTickLabels(true);
    blueslider.setShowTickMarks(true);    
    blueslider.setMajorTickUnit(50.0);
    
    vbox.getChildren().addAll(redLabel, redslider, 
                              greenLabel, greenslider, 
                              blueLabel, blueslider);


    
    // Create a text in a pane
    Pane colorPane = new Pane();
    colorPane.getChildren().add(rect);
    
    // Create a border pane to hold text and scroll bars
    BorderPane pane = new BorderPane();
    pane.setCenter(colorPane);
    pane.setLeft(vbox);
    
    redslider.valueProperty().addListener(ov -> {
       redVal = redslider.getValue() / 255.0;
       rect.setFill(new Color(redVal, greenVal, blueVal, 1.0));
      }
    );
    
    greenslider.valueProperty().addListener(ov -> {
       greenVal = greenslider.getValue() / 255.0;
       rect.setFill(new Color(redVal, greenVal, blueVal, 1.0));
      }
    );
    
    blueslider.valueProperty().addListener(ov -> {
       blueVal = blueslider.getValue() / 255.0;
       rect.setFill(new Color(redVal, greenVal, blueVal, 1.0));
      }
    );
    
     
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 450, 250);
    primaryStage.setTitle("Color Chooser"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage   
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
