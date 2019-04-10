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
import javafx.scene.canvas.*;
import javafx.scene.input.MouseEvent;

public class CSPaint extends Application {

    protected Text text = new Text(50, 50, "CSPaint");
    private GraphicsContext g;
    private Canvas canvas;
    private Pane mainPane;
    private int currentColorNum = 0;
    private final Color[] palette = {
        Color.BLACK, Color.RED, Color.GREEN, Color.BLUE,
        Color.CYAN, Color.MAGENTA, Color.color(0.95,0.9,0)};
    private double prevX, prevY;
    private boolean dragging;


    protected BorderPane getPane() {
        VBox paneForRadioButtons = new VBox(20);
        paneForRadioButtons.setPadding(new Insets(5, 5, 5, 5));

        //not sure what these do vvv

        //paneForRadioButtons.setStyle("-fx-border-color: black");
        //paneForRadioButtons.setStyle("-fx-border-width: 2px; -fx-border-color: black");
        RadioButton drawBt = new RadioButton("Draw");
        RadioButton eraseBt = new RadioButton("Erase");
        RadioButton circleBt = new RadioButton("Circle");
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setLeft(new Label("Color:"));
        TextField tf = new TextField();
        paneForRadioButtons.setStyle("-fx-background-color: pink");
        //tf.setAlignment(Pos.BOTTOM_RIGHT);
        //paneForTextField.setCenter(tf);
        //pane.setTop(paneForTextField);
        paneForRadioButtons.getChildren().addAll(drawBt, eraseBt, circleBt, paneForTextField, tf);

        ToggleGroup group = new ToggleGroup();
        drawBt.setToggleGroup(group);
        eraseBt.setToggleGroup(group);
        circleBt.setToggleGroup(group);

        BorderPane pane = new BorderPane();
        //pane.setBottom(bottomText);
        pane.setLeft(paneForRadioButtons);

        Pane mainPane = new Pane(canvas);
        mainPane.getChildren().add(text);
        pane.setCenter(mainPane);

        HBox bottom = new HBox(10);
        bottom.setPadding(new Insets(5, 5, 5, 5));
        //bottom.setStyle("-fx-border-color: black");
        //bottom.setStyle("-fx-border-width: 2px; -fx-border-color: black");
        bottom.getChildren().add(text);
        pane.setBottom(bottom);
        bottom.setStyle("-fx-background-color: pink");

        //btLeft.setOnAction(e -> text.setX(text.getX() - 10));
        //btRight.setOnAction(e -> text.setX(text.getX() + 10));
        mainPane.setOnMousePressed( e -> mousePressed(e) );
        mainPane.setOnMouseDragged( e -> mouseDragged(e) );
        mainPane.setOnMouseReleased( e -> mouseReleased(e) );
        return pane;
    }

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
      // Create a scene and place it in the stage
      canvas = new Canvas();
      g = canvas.getGraphicsContext2D();
      drawLines(g);
      Scene scene = new Scene(getPane(), 650, 450);
      primaryStage.setTitle("CSPaint"); // Set the stage title
      primaryStage.setScene(scene); // Place the scene in the stage
      primaryStage.show(); // Display the stage

      /* Create the canvans and draw its content for the first time. */
      clearAndDrawPalette();

      /* Respond to mouse events on the canvas, by calling methods in this class. */


    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
      launch(args);
    }




    public void clearAndDrawPalette() {

        int width = (int)canvas.getWidth();    // Width of the canvas.
        int height = (int)canvas.getHeight();  // Height of the canvas.

        //g.setFill(Color.WHITE);
        //g.fillRect(0,0,width,height);

        //int colorSpacing = (height - 56) / 7;
        // Distance between the top of one colored rectangle in the palette
        // and the top of the rectangle below it.  The height of the
        // rectangle will be colorSpacing - 3.  There are 7 colored rectangles,
        // so the available space is divided by 7.  The available space allows
        // for the gray border and the 50-by-50 CLEAR button.

        /* Draw a 3-pixel border around the canvas in gray.  This has to be
             done by drawing three rectangles of different sizes. */

        g.setStroke(Color.BLACK);
        g.setLineWidth(3);
        g.strokeRect(1.5, 1.5, width-3, height-3);

        /* Draw a 56-pixel wide gray rectangle along the right edge of the canvas.
             The color palette and Clear button will be drawn on top of this.
             (This covers some of the same area as the border I just drew. */

        //g.setFill(Color.BLACK);
        //g.fillRect(width - 56, 0, 56, height);

        /* Draw the "Clear button" as a 50-by-50 white rectangle in the lower right
             corner of the canvas, allowing for a 3-pixel border. */

        //g.setFill(Color.WHITE);
        //g.fillRect(width-53,  height-53, 50, 50);
        //g.setFill(Color.BLACK);
        //g.fillText("CLEAR", width-48, height-23);

        /* Draw the seven color rectangles. */

        // for (int N = 0; N < 7; N++) {
        //     g.setFill( palette[N] );
        //     g.fillRect(width-53, 3 + N*colorSpacing, 50, colorSpacing-3);
        // }

        /* Draw a 2-pixel white border around the color rectangle
             of the current drawing color. */

        //g.setStroke(Color.WHITE);
        //g.setLineWidth(2);
        //g.strokeRect(width-54, 2 + currentColorNum*colorSpacing, 52, colorSpacing-1);

    } // end clearAndDrawPalette()

    /**
     * This is called when the user presses the mouse anywhere in the canvas.
     * There are three possible responses, depending on where the user clicked:
     * Change the current color, clear the drawing, or start drawing a curve.
     * (Or do nothing if user clicks on the border.)
     */
    public void mousePressed(MouseEvent evt) {
        System.out.println("Mouse pressed");
        if (dragging == true)  // Ignore mouse presses that occur
            return;            //    when user is already drawing a curve.
                               //    (This can happen if the user presses
                               //    two mouse buttons at the same time.)

        int x = (int)evt.getX();   // x-coordinate where the user clicked.
        int y = (int)evt.getY();   // y-coordinate where the user clicked.

        int width = (int)canvas.getWidth();    // Width of the canvas.
        int height = (int)canvas.getHeight();  // Height of the canvas.

        // Start drawing a curve from the point (x,y).
        prevX = x;
        prevY = y;
        dragging = true;
        g.setLineWidth(2);  // Use a 2-pixel-wide line for drawing.
        g.setStroke(Color.BLACK);

    } // end mousePressed()

    /**
     * Called whenever the user releases the mouse button. Just sets
     * dragging to false.
     */
    public void mouseReleased(MouseEvent evt) {
        dragging = false;
        System.out.print("Mouse released");
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

    private void drawLines(GraphicsContext gc) {

        gc.beginPath();
        gc.moveTo(30.5, 30.5);
        gc.lineTo(150.5, 30.5);
        gc.lineTo(150.5, 150.5);
        gc.lineTo(30.5, 30.5);
        gc.stroke();
    }


}