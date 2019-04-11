
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;




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
import javafx.scene.control.Toggle;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.collections.*;
import javafx.stage.Stage;
import javafx.scene.text.Text.*;
import javafx.scene.text.*;
import javafx.beans.value.*;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



/**
 * A simple program where the user can sketch curves in a variety of
 * colors.  A color palette is shown along the right edge of the canvas.
 * The user can select a drawing color by clicking on a color in the
 * palette.  Under the colors is a "Clear button" that the user
 * can click to clear the sketch.  The user draws by clicking and
 * dragging in a large white area that occupies most of the canvas.
 */
public class SimplePaint extends Application {

    /**
     * This main routine allows this class to be run as a program.
     */
    public static void main(String[] args) {
        launch(args);
    }
    private Color currentColor = Color.BLACK;
    private double prevX, prevY;
    private boolean dragging;   // This is set to true while the user is drawing.
    private TextField tf;
    private Canvas canvas;  // The canvas on which everything is drawn.
    Alert alert;

    private GraphicsContext g;  // For drawing on the canvas.
    private RadioButton drawBt;
    private RadioButton eraseBt;
    private RadioButton circleBt;
    private int shapeCount = 0;
    HBox bottom;
    Label shapeCountLabel = new Label(Integer.toString(shapeCount));
    Label xyLabel = new Label("(0, 0)");
    ArrayList<Object[]> shapeList = new ArrayList<Object[]>();


    public void start(Stage stage) {

        /* Create the canvans and draw its content for the first time. */

        canvas = new Canvas(650,450);
        g = canvas.getGraphicsContext2D();
        int width = (int)canvas.getWidth();    // Width of the canvas.
        int height = (int)canvas.getHeight();  // Height of the canvas.

        g.setFill(Color.WHITE);
        g.fillRect(0,0,width,height);

        g.setStroke(Color.WHITE);
        g.setLineWidth(2);

        canvas.setOnMousePressed( e -> mousePressed(e) );
        canvas.setOnMouseDragged( e -> mouseDragged(e) );
        canvas.setOnMouseReleased( e -> mouseReleased(e) );
        canvas.setOnMouseMoved( e -> mouseMoved(e) );


        VBox paneForRadioButtons = new VBox(20);
        paneForRadioButtons.setPadding(new Insets(5, 5, 5, 5));

        drawBt = new RadioButton("Draw");
        drawBt.setSelected(true);
        eraseBt = new RadioButton("Erase");
        circleBt = new RadioButton("Circle");
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setLeft(new Label("Color:"));
        tf = new TextField();
        paneForRadioButtons.setStyle("-fx-background-color: pink");
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);
        paneForRadioButtons.getChildren().addAll(drawBt, eraseBt, circleBt, paneForTextField, tf);

        ToggleGroup group = new ToggleGroup();
        drawBt.setToggleGroup(group);
        eraseBt.setToggleGroup(group);
        circleBt.setToggleGroup(group);

        tf.setOnKeyPressed(e -> keyPressed(e));

        // group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
        //     public void changed(ObservableValue<? extends Toggle> ov,
        //         Toggle old_toggle, Toggle new_toggle) {
        //         }
        // });

        BorderPane pane = new BorderPane();
        pane.setLeft(paneForRadioButtons);

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
        //Text shapeText = new Text(50, 50, "Number of Shapes: " + shapeCount);
        //bottom.getChildren().add(shapeText);
    }

    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            changeColor(tf.getText());
            //System.out.println(tf.getText());
        }
    }


    /**
     * Change the drawing color after the user has clicked the
     * mouse on the color palette at a point with y-coordinate y.
     */
    private void changeColor(String textFieldColor) {
        if (textFieldColor != null) {
            try {
               currentColor = Color.valueOf(textFieldColor);
            } catch (IllegalArgumentException e) {
                alert = new Alert(AlertType.WARNING);
                alert.setTitle("Invalid Color Warning");
                alert.setHeaderText("You've chosen an invalid color!");
                alert.setContentText("Type in another color.");
                alert.showAndWait();
                //System.out.println("bad color");
            }
        } else {
            currentColor = Color.BLACK;
        }

    } // end changeColor()

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

            int width = (int)canvas.getWidth();    // Width of the canvas.
            int height = (int)canvas.getHeight();  // Height of the canvas.

                prevX = x;
                prevY = y;
                dragging = true;
                g.setLineWidth(4);  // Use a 2-pixel-wide line for drawing.
                g.setStroke(currentColor);
        }
        else if (eraseBt.isSelected()) {
            int x = (int)evt.getX();   // x-coordinate where the user clicked.
            int y = (int)evt.getY();   // y-coordinate where the user clicked.
            Integer[] point = {x, y};
            //System.out.println((int)evt.getX());
            //shapeList.add(point);
            //if (shapeList.contains(point)) {
            //    System.out.println("erased");
            //}
            int width = (int)canvas.getWidth();    // Width of the canvas.
            int height = (int)canvas.getHeight();  // Height of the canvas.

                prevX = x;
                prevY = y;
                dragging = true;
                g.setLineWidth(20);
                g.setStroke( Color.WHITE );

        }
        else if (circleBt.isSelected()) {
            g.setFill(currentColor);
            g.fillOval((int)evt.getX(), (int)evt.getY(), 30, 30);
            //Integer[] point = {(int)evt.getX(), (int)evt.getY()};
            //System.out.println((int)evt.getX());
            //shapeList.add(point);
            updateShapeCount();
            //System.out.println(shapeList);
        }


    } // end mousePressed()


    /**
     * Called whenever the user releases the mouse button. Just sets
     * dragging to false.
     */
    public void mouseReleased(MouseEvent evt) {
        dragging = false;
    }


    /**
     * Called whenever the user moves the mouse while a mouse button is held down.
     * If the user is drawing, draw a line segment from the previous mouse location
     * to the current mouse location, and set up prevX and prevY for the next call.
     * Note that in case the user drags outside of the drawing area, the values of
     * x and y are "clamped" to lie within this area.  This avoids drawing on the color
     * palette or clear button.
     */
    public void mouseDragged(MouseEvent evt) {

        if (dragging == false)
            return;  // Nothing to do because the user isn't drawing.

        double x = evt.getX();   // x-coordinate of mouse.
        double y = evt.getY();   // y-coordinate of mouse.

        g.strokeLine(prevX, prevY, x, y);  // Draw the line.

        prevX = x;  // Get ready for the next line segment in the curve.
        prevY = y;

    } // end mouseDragged()


} // end class SimplePaint