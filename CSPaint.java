import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.Canvas;

public class CSPaint extends Application {

    protected Text text = new Text(50, 50, "CSPaint");

    protected BorderPane getPane() {
        VBox paneForRadioButtons = new VBox(20);
        paneForRadioButtons.setPadding(new Insets(5, 5, 5, 5));
        paneForRadioButtons.setStyle("-fx-border-color: black");
        paneForRadioButtons.setStyle
            ("-fx-border-width: 2px; -fx-border-color: black");
        RadioButton drawBt = new RadioButton("Draw");
        RadioButton eraseBt = new RadioButton("Erase");
        RadioButton circleBt = new RadioButton("Circle");
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setLeft(new Label("Color:"));
        TextField tf = new TextField();
        //tf.setAlignment(Pos.BOTTOM_RIGHT);
        //paneForTextField.setCenter(tf);
        //pane.setTop(paneForTextField);
        paneForRadioButtons.getChildren().addAll(drawBt, eraseBt, circleBt, paneForTextField, tf);

        ToggleGroup group = new ToggleGroup();
        drawBt.setToggleGroup(group);
        eraseBt.setToggleGroup(group);
        circleBt.setToggleGroup(group);

// The purpose of this was to add a horozontal bar at the bottom, but it has't really been working out.

        //HBox bottomText = new HBox(20);
        //Button btLeft = new Button("Left",
            //new ImageView("left.gif"));
        //Button btRight = new Button("Right",
            //new ImageView("right.gif"));
        //paneForButtons.getChildren().addAll(btLeft, btRight);
        //paneForButtons.setAlignment(Pos.CENTER);
        //paneForButtons.setPadding(new Insets(5, 5, 5, 5));
        //BorderPane bText = new BorderPane();
        //bText.setLeft(new Label("Hello"));
        //bottomText.getChildren().addAll(text);
        //bottomText.setStyle("-fx-border-color: black");
        //bottomText.setStyle
            //("-fx-border-width: 2px; -fx-border-color: black");
        //paneForButtons.setStyle("-fx-border-color: green");

        BorderPane pane = new BorderPane();
        //pane.setBottom(bottomText);
        pane.setLeft(paneForRadioButtons);

        Pane paneForText = new Pane();
        paneForText.getChildren().add(text);
        pane.setCenter(paneForText);

        Pane bottom = new HBox(10);
        bottom.getChildren().add(text);
        pane.setBottom(bottom);
        //btLeft.setOnAction(e -> text.setX(text.getX() - 10));
        //btRight.setOnAction(e -> text.setX(text.getX() + 10));

        return pane;
    }

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
      // Create a scene and place it in the stage
      Scene scene = new Scene(getPane(), 650, 450);
      primaryStage.setTitle("CSPaint"); // Set the stage title
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