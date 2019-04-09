import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MouseExample extends Application {
  private Label last;  // most recent label that was colored
  
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Create a pane and set its properties
    VBox vbox = new VBox();
    vbox.setPadding(new Insets(25, 25, 25, 25));
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.CENTER);
    Label labelClicked = new Label("Mouse Clicked");
    Label labelEntered = new Label("Mouse Entered");
    Label labelExited = new Label("Mouse Exited");
    Label labelPressed = new Label("Mouse Pressed");
    Label labelReleased = new Label("Mouse Released");
    Label labelDragged = new Label("Mouse Dragged");
    Label labelCoords = new Label("-----");
    last = labelClicked;
    vbox.getChildren().addAll(labelClicked, labelEntered, labelExited,
			      labelPressed, labelReleased, labelDragged, labelCoords);

    vbox.setOnMousePressed(e -> {       
      last.setTextFill(Color.BLACK);
      last = labelPressed;
      labelPressed.setTextFill(Color.BLUE);
      String s = new String("["+(int)(e.getX())+","+(int)(e.getY())+"]");
      labelCoords.setText(s);
    });
    
    vbox.setOnMouseReleased(e -> {       
      last.setTextFill(Color.BLACK);
      last = labelReleased;
      labelReleased.setTextFill(Color.PINK);
      String s = new String("["+(int)(e.getX())+","+(int)(e.getY())+"]");
      labelCoords.setText(s);
    });
    
    vbox.setOnMouseClicked(e -> {       
      last.setTextFill(Color.BLACK);
      last = labelClicked;
      labelClicked.setTextFill(Color.PURPLE);
      String s = new String("["+(int)(e.getX())+","+(int)(e.getY())+"]");
      labelCoords.setText(s);
    });
    
    vbox.setOnMouseEntered(e -> {       
      last.setTextFill(Color.BLACK);
      last = labelEntered;
      labelEntered.setTextFill(Color.GREEN);
      String s = new String("["+(int)(e.getX())+","+(int)(e.getY())+"]");
      labelCoords.setText(s);
    });
    
    vbox.setOnMouseExited(e -> {       
      last.setTextFill(Color.BLACK);
      last = labelExited;
      labelExited.setTextFill(Color.RED);
      String s = new String("["+(int)(e.getX())+","+(int)(e.getY())+"]");
      labelCoords.setText(s);
    });
    
    vbox.setOnMouseDragged(e -> {       
      last.setTextFill(Color.BLACK);
      last = labelDragged;
      labelDragged.setTextFill(Color.BROWN);
      String s = new String("["+(int)(e.getX())+","+(int)(e.getY())+"]");
      labelCoords.setText(s);
    });
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(vbox);
    primaryStage.setTitle("MouseExample"); // Set the stage title
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

