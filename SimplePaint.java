// Natalie Wilkinson
// Collaboration Statement:
// In order to help learn course concepts, I worked on the homework with
//[give the names of the people you
// worked with], discussed homework topics and issues with [provide names of people], and/or
//consulted related material that can be found at [cite any other materials not provided as
// course materials for CS 1331 that assisted your learning].

// Imports for javafx
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.Toggle;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.canvas.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.collections.*;
import javafx.scene.text.Text.*;
import javafx.scene.text.*;
import javafx.beans.value.*;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class SimplePaint extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private Color currentColor = Color.BLACK; // keeps track of the current color the user chooses
    private double prevX, prevY; // used when drawing to create stroke to the new X and Y values
    private boolean dragging; // bool that indicated whether the mouse is currently being dragged
    private Canvas canvas; // the canvas where all drawing can be done
    private GraphicsContext gc; // used to issue draw calls to the canvas

    VBox left; // creates a VBox
    // In the VBox there are a set of radio buttons and a TextField for choosing color
    // setof radio buttons for drawing, erasing, and inserting shapes:
    private RadioButton drawBt;
    private RadioButton eraseBt;
    private RadioButton circleBt;
    // a new textfield for the user to enter a color:
    private TextField colorTextBox;
    Alert colorAlert; // new Alert object used to alert user if they entered an invalid color

    HBox bottom; // creates an HBox
    // In the HBox there is the graphical coordinates of the mouse and the number of shapes:
    // creates a label to keep track of the number of shapes on the GUI
    private int shapeCount = 0; // keeps track of the number of shapes added
    Label shapeCountLabel = new Label(Integer.toString(shapeCount));
    Label xyLabel = new Label("(0, 0)");


    public void start(Stage stage) {
        canvas = new Canvas(650,450);
        gc = canvas.getGraphicsContext2D();
        int width = (int)canvas.getWidth();
        int height = (int)canvas.getHeight();

        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,width,height);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);

        canvas.setOnMousePressed( e -> mousePressed(e) );
        canvas.setOnMouseDragged( e -> mouseDragged(e) );
        canvas.setOnMouseReleased( e -> mouseReleased(e) );
        canvas.setOnMouseMoved( e -> mouseMoved(e) );


        left = new VBox(20);
        left.setPadding(new Insets(5, 5, 5, 5));

        drawBt = new RadioButton("Draw");
        drawBt.setSelected(true);
        eraseBt = new RadioButton("Erase");
        circleBt = new RadioButton("Circle");
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setLeft(new Label("Color:\n(press Enter when done)"));
        colorTextBox = new TextField();

        left.setStyle("-fx-background-color: pink");
        colorTextBox.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(colorTextBox);
        left.getChildren().addAll(drawBt, eraseBt, circleBt, paneForTextField, colorTextBox);

        ToggleGroup group = new ToggleGroup();
        drawBt.setToggleGroup(group);
        eraseBt.setToggleGroup(group);
        circleBt.setToggleGroup(group);

        colorTextBox.setOnKeyPressed(e -> keyPressed(e));

        BorderPane pane = new BorderPane();
        pane.setLeft(left);

        Pane mainPane = new Pane(canvas);


        bottom = new HBox(10);
        bottom.setPadding(new Insets(5, 5, 5, 5));
        bottom.getChildren().add(xyLabel);
        Text text = new Text(50, 50, "Number of Shapes: ");
        bottom.getChildren().add(text);
        bottom.getChildren().add(shapeCountLabel);

        pane.setBottom(bottom);
        bottom.setStyle("-fx-background-color: pink");

        Pane root = new Pane(canvas);
        pane.setCenter(root);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Simple Paint");
        stage.show();
    }

    public void updateShapeCount() {
        shapeCount++;
        shapeCountLabel.setText(Integer.toString(shapeCount));
    }

    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            changeColor(colorTextBox.getText());
        }
    }

    private void changeColor(String textFieldColor) {
        if (textFieldColor != null) {
            try {
               currentColor = Color.valueOf(textFieldColor);
            } catch (IllegalArgumentException e) {
                colorAlert = new Alert(AlertType.WARNING);
                colorAlert.setTitle("Invalid Color Warning");
                colorAlert.setHeaderText("You've chosen an invalid color!");
                colorAlert.setContentText("Type in another color.");
                colorAlert.showAndWait();
            }
        } else {
            currentColor = Color.BLACK;
        }

    }

    public void mouseMoved(MouseEvent event) {
        xyLabel.setText("(" + event.getX() + ", " + event.getY() + ")");

    }


    public void mousePressed(MouseEvent evt) {
        if (drawBt.isSelected()) {
            if (dragging == true) {
                return;
            }
            int x = (int)evt.getX();
            int y = (int)evt.getY();
            prevX = x;
            prevY = y;
            dragging = true;
            gc.setLineWidth(4);
            gc.setStroke(currentColor);
        }
        else if (eraseBt.isSelected()) {
            int x = (int)evt.getX();
            int y = (int)evt.getY();
            prevX = x;
            prevY = y;
            dragging = true;
            gc.setLineWidth(20);
            gc.setStroke(Color.WHITE);
        }
        else if (circleBt.isSelected()) {
            gc.setFill(currentColor);
            gc.fillOval((int)evt.getX(), (int)evt.getY(), 30, 30);
            updateShapeCount();
        }


    }

    public void mouseReleased(MouseEvent evt) {
        dragging = false;
    }

    public void mouseDragged(MouseEvent evt) {
        xyLabel.setText("(" + evt.getX() + ", " + evt.getY() + ")");

        if (dragging == false) {
            return;
        }

        double x = evt.getX();
        double y = evt.getY();

        gc.strokeLine(prevX, prevY, x, y);
        prevX = x;
        prevY = y;



    }


}