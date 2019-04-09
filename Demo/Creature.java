import java.util.Random;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Creature extends Application {
    private final int WIDTH=400, HEIGHT=400;
    private double iconHeight, iconWidth;
    private double x,y;
    private Random generator;
    private ImageView devil;
    private Timeline animation;
    private boolean chasing = false;
    private boolean inTime = false;
    private int tryCount = 0;
    private int hitCount = 0;
    private double hitX,hitY;
    private static final int DELAY = 1000;  // one second
 

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    Pane pane = new Pane(); 

    generator = new Random();

    // Create the devil image
    Image image = new Image("devil.gif");
    devil = new ImageView(image);
    pane.getChildren().add(devil);

    // Create an animation for timer for clicking on the creature
    animation = new Timeline(
      new KeyFrame(Duration.millis(DELAY), e -> timeExpired()));
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.play(); // Start animation

    pane.setOnMousePressed(e -> mousePressed(e));

    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, WIDTH, HEIGHT);
    primaryStage.setTitle("Creature Chaser"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    iconWidth = image.getWidth();
    iconHeight = image.getHeight();
      
    // Must request focus after the primary stage is displayed
    pane.requestFocus();
  }

    private void timeExpired() {
      inTime = false;
    }

    public void mousePressed(MouseEvent e) {

        if (chasing == false) {  // Original click so move devil to new position
           x = generator.nextInt(WIDTH-(int)iconWidth) + 1;
           y = generator.nextInt(HEIGHT-(int)iconHeight) + 1;
           animation.play();
           devil.setTranslateX(x);
           devil.setTranslateY(y);
           chasing = true;
           inTime = true;
           }
        else {
           tryCount++;
           if (inTime == true) {
	            hitX = e.getX();
	            hitY = e.getY();
               if ((x <= hitX) && (hitX <= (x+iconWidth)) &&
                   (y <= hitY) && (hitY <= (y+iconHeight)))
                 hitCount++;
              }
           System.out.println(hitCount+" / "+tryCount);
           chasing = false;
           animation.stop();
           }
      }



  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
