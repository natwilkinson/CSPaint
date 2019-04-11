// Natalie Wilkinson
// Collaboration Statement:
// In order to help learn course concepts, I worked on the homework with
//[give the names of the people you
// worked with], discussed homework topics and issues with [provide names of people], and/or
// consulted related material that can be found at:
    // https://www.geeksforgeeks.org/overriding-equals-method-in-java/
    // https://www.java2s.com/Code/Java/JavaFX/ChangeLabeltextinButtonclickevent.htm
    // https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html
    // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/GraphicsContext.html#setLineWidth-double-
    // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/GraphicsContext.html
    // https://stackoverflow.com/questions/25252558/javafx-how-to-make-enter-key-submit-textarea/25252616
    // https://docs.oracle.com/javafx/2/api/javafx/scene/input/KeyCode.html
    // https://www.programcreek.com/java-api-examples/?class=javafx.scene.paint.Color&method=valueOf
    // http://java-buddy.blogspot.com/2013/04/save-canvas-to-png-file.html
    // http://computertechcode.blogspot.com/2016/02/package-javafxtest-import-javafx.html
    // https://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm

// Imports for javafx
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.ArcType;


/**
 *@author Natalie Wilkinson
 *@version 1.0
 */
public class CSPaint extends Application {
    /**
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    private Color currentColor = Color.BLACK; // keeps track of the current color the user chooses
    private double prevX, prevY; // used when drawing to create stroke to the new X and Y values
    private boolean dragging; // bool that indicated whether the mouse is currently being dragged
    private Canvas canvas; // the canvas where all drawing can be done
    private GraphicsContext gc; // used to issue draw calls to the canvas

    private VBox left; // creates a VBox
    private Button clear;
    // In the VBox there are a set of radio buttons and a TextField for choosing color
    // setof radio buttons for drawing, erasing, and inserting shapes:
    private RadioButton drawBt;
    private Slider drawSize;
    private Label drawSizeLabel;
    private double currentSize = 4;
    private RadioButton eraseBt;
    /* private Slider eraseSize;
    private Label eraseSizeLabel;
    private double currentEraseSize = 20; */
    private RadioButton circleBt;
    // a new textfield for the user to enter a color:
    private TextField colorTextBox;
    private Alert colorAlert; // new Alert object used to alert user if they entered an invalid color
    private BorderPane colorTextLabel;

    private RadioButton squareBt;
    private Rectangle square;
    private RadioButton arcBt;


    private HBox bottom; // creates an HBox
    // In the HBox there is the graphical coordinates of the mouse and the number of shapes:
    // creates a label to keep track of the number of shapes on the GUI
    private int shapeCount = 0; // keeps track of the number of shapes added
    private Label shapeCountLabel = new Label(Integer.toString(shapeCount));
    private Label xyLabel = new Label("(0, 0)");

    /**
     * @param stage the stage
     */
    public void start(Stage stage) {
        canvas = new Canvas(650, 450);
        gc = canvas.getGraphicsContext2D();
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);

        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseMoved(e -> mouseMoved(e));


        left = new VBox(20);
        left.setPadding(new Insets(5, 5, 5, 5));
        clear = new Button("Clear");
        clear.setOnMousePressed(e -> clearCanvas(e));
        drawBt = new RadioButton("Draw");
        // set up penSize slider
        drawSize = new Slider(0.1, 30, 4);
        drawSizeLabel = new Label("Slide to change pen size:\n" + "(radius = " + (int) currentSize / 2 + " pixels)");

        drawSize.setOnMouseReleased(e -> changeDrawSize(e));
        drawSize.setOnMouseReleased(e -> changeDrawSize(e));

        drawBt.setSelected(true);
        eraseBt = new RadioButton("Erase");
        circleBt = new RadioButton("Circle");
        squareBt = new RadioButton("Square");
        arcBt = new RadioButton("Arc");

        colorTextLabel = new BorderPane();
        colorTextLabel.setLeft(new Label("Color:\n(press Enter when done)"));
        colorTextBox = new TextField();

        left.setStyle("-fx-background-color: pink");
        colorTextBox.setAlignment(Pos.BOTTOM_RIGHT);
        colorTextLabel.setCenter(colorTextBox);
        left.getChildren().addAll(clear, drawBt, drawSizeLabel, drawSize, eraseBt,
            circleBt, squareBt, arcBt, colorTextLabel, colorTextBox);

        ToggleGroup group = new ToggleGroup();
        drawBt.setToggleGroup(group);
        eraseBt.setToggleGroup(group);
        circleBt.setToggleGroup(group);
        squareBt.setToggleGroup(group);
        arcBt.setToggleGroup(group);

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
        stage.setTitle("CSPaint");
        stage.show();
    }
    /**
     * Updates the number of shapes counted
     */
    public void updateShapeCount() {
        shapeCount++;
        shapeCountLabel.setText(Integer.toString(shapeCount));
    }
    /**
     * changes pen size
     * @param event changing draw slider size
     */
    public void changeDrawSize(MouseEvent event) {
        currentSize = drawSize.getValue();
        drawSizeLabel.setText("Slide to change pen size:\n" + "(radius = " + (int) currentSize / 2 + " pixels)");
        gc.setLineWidth(currentSize);
        //System.out.println(currentSize);
    }

    /**
     * clears canvas
     * @param event pressing "clear" button
     */
    public void clearCanvas(MouseEvent event) {
        gc.setFill(Color.WHITE);
        shapeCount = 0;
        shapeCountLabel.setText(Integer.toString(shapeCount));
        gc.fillRect(0, 0, 650, 450);
        //System.out.println(currentSize);
    }

    /**
    *
    * @param event event of key getting pressed
    */
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
    /**
     * @param event mouse getting moved
     */
    public void mouseMoved(MouseEvent event) {
        xyLabel.setText("(" + event.getX() + ", " + event.getY() + ")");

    }

    /**
     * @param event mouse getting pressed
     */
    public void mousePressed(MouseEvent event) {
        if (drawBt.isSelected()) {
            if (dragging) {
                return;
            }
            int x = (int) event.getX();
            int y = (int) event.getY();
            prevX = x;
            prevY = y;
            dragging = true;
            gc.setLineWidth(currentSize);
            //System.out.println(currentSize);
            gc.setStroke(currentColor);
            gc.strokeLine(x, y, x, y);
        } else if (eraseBt.isSelected()) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            prevX = x;
            prevY = y;
            dragging = true;
            gc.setLineWidth(20);
            gc.setStroke(Color.WHITE);
            gc.strokeLine(x, y, x, y);
        } else if (circleBt.isSelected()) {
            gc.setFill(currentColor);
            gc.fillOval((int) event.getX(), (int) event.getY(), 30, 30);
            updateShapeCount();
        } else if (squareBt.isSelected()) {
            gc.setFill(currentColor);
            gc.fillRect(event.getX(), event.getY(), 30.0, 30.0);
            updateShapeCount();
        } else if (arcBt.isSelected()) {
            gc.setFill(currentColor);
            gc.fillArc(event.getX(), event.getY(), 30, 30, 45, 240, ArcType.ROUND);
            updateShapeCount();
        }


    }
    /**
     * @param event mouse getting released
     */
    public void mouseReleased(MouseEvent event) {
        dragging = false;
    }
    /**
     *
     * @param event mouse getting dragged
     */
    public void mouseDragged(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        if (x >= 0 && x <= 650 && y >= 0 && y <= 450) {
            xyLabel.setText("(" + event.getX() + ", " + event.getY() + ")");
        }


        if (!dragging) {
            return;
        }


        gc.strokeLine(prevX, prevY, x, y);
        prevX = x;
        prevY = y;



    }


}