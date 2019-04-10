
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

    private final Color[] palette = {
            Color.BLACK, Color.RED, Color.GREEN, Color.BLUE,
            Color.CYAN, Color.MAGENTA,
    };

    private int currentColorNum = 0;

    private double prevX, prevY;
    private boolean dragging;   // This is set to true while the user is drawing.

    private Canvas canvas;  // The canvas on which everything is drawn.

    private GraphicsContext g;  // For drawing on the canvas.
    private RadioButton drawBt;
    private RadioButton eraseBt;
    private RadioButton circleBt;


    public void start(Stage stage) {

        /* Create the canvans and draw its content for the first time. */

        canvas = new Canvas(650,450);
        g = canvas.getGraphicsContext2D();
        clearAndDrawPalette();


        canvas.setOnMousePressed( e -> mousePressed(e) );
        canvas.setOnMouseDragged( e -> mouseDragged(e) );
        canvas.setOnMouseReleased( e -> mouseReleased(e) );





        VBox paneForRadioButtons = new VBox(20);
        paneForRadioButtons.setPadding(new Insets(5, 5, 5, 5));

        drawBt = new RadioButton("Draw");
        drawBt.setSelected(true);
        eraseBt = new RadioButton("Erase");
        circleBt = new RadioButton("Circle");
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setLeft(new Label("Color:"));
        TextField tf = new TextField();
        paneForRadioButtons.setStyle("-fx-background-color: pink");
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);
        paneForRadioButtons.getChildren().addAll(drawBt, eraseBt, circleBt, paneForTextField, tf);

        ToggleGroup group = new ToggleGroup();
        drawBt.setToggleGroup(group);
        eraseBt.setToggleGroup(group);
        circleBt.setToggleGroup(group);

        // group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
        //     public void changed(ObservableValue<? extends Toggle> ov,
        //         Toggle old_toggle, Toggle new_toggle) {
        //         }
        // });





        BorderPane pane = new BorderPane();
        pane.setLeft(paneForRadioButtons);

        Pane mainPane = new Pane(canvas);


        HBox bottom = new HBox(10);
        bottom.setPadding(new Insets(5, 5, 5, 5));
        Text text = new Text(50, 50, "CSPaint");

        bottom.getChildren().add(text);
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


    /**
     * Fills the canvas with white and draws the color palette and (simulated)
     * "Clear" button on the right edge of the canvas.  This method is called when
     * the canvas is created and when the user clicks "Clear."
     */
    public void clearAndDrawPalette() {

        int width = (int)canvas.getWidth();    // Width of the canvas.
        int height = (int)canvas.getHeight();  // Height of the canvas.

        g.setFill(Color.WHITE);
        g.fillRect(0,0,width,height);

        g.setStroke(Color.WHITE);
        g.setLineWidth(2);

    } // end clearAndDrawPalette()


    /**
     * Change the drawing color after the user has clicked the
     * mouse on the color palette at a point with y-coordinate y.
     */
    private void changeColor(int y) {

        int width = (int)canvas.getWidth();
        int height = (int)canvas.getHeight();
        int colorSpacing = (height - 56) / 7;  // Space for one color rectangle.
        int newColor = y / colorSpacing;       // Which color number was clicked?

        if (newColor < 0 || newColor > 6)      // Make sure the color number is valid.
            return;

        /* Remove the highlight from the current color, by drawing over it in gray.
             Then change the current drawing color and draw a highlight around the
             new drawing color.  */

        g.setLineWidth(2);
        g.setStroke(Color.GRAY);
        g.strokeRect(width-54, 2 + currentColorNum*colorSpacing, 52, colorSpacing-1);
        currentColorNum = newColor;
        g.setStroke(Color.WHITE);
        g.strokeRect(width-54, 2 + currentColorNum*colorSpacing, 52, colorSpacing-1);

    } // end changeColor()



    /**
     * This is called when the user presses the mouse anywhere in the canvas.
     * There are three possible responses, depending on where the user clicked:
     * Change the current color, clear the drawing, or start drawing a curve.
     * (Or do nothing if user clicks on the border.)
     */
    public void mousePressed(MouseEvent evt) {
        if (drawBt.isSelected()) {
            if (dragging == true)  // Ignore mouse presses that occur
            return;            //    when user is already drawing a curve.
                               //    (This can happen if the user presses
                               //    two mouse buttons at the same time.)

        int x = (int)evt.getX();   // x-coordinate where the user clicked.
        int y = (int)evt.getY();   // y-coordinate where the user clicked.

        int width = (int)canvas.getWidth();    // Width of the canvas.
        int height = (int)canvas.getHeight();  // Height of the canvas.

            prevX = x;
            prevY = y;
            dragging = true;
            g.setLineWidth(2);  // Use a 2-pixel-wide line for drawing.
            g.setStroke( palette[currentColorNum] );
        }
        else if (eraseBt.isSelected()) {

        }
        else if (circleBt.isSelected()) {

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