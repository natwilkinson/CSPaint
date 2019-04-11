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
import javafx.scene.control.Button;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritablePixelFormat;

public class TrySnapshot extends Application {
 @Override
 public void start(Stage primaryStage) {
   final VBox vbox = new VBox(2);
   final Button btn = new Button();
   vbox.getChildren().add(btn);
   btn.setText("Say 'Hello World'");
   btn.setOnAction(new EventHandler<ActionEvent>() {
     @Override
     public void handle(ActionEvent event) {
       // here we make image from vbox and add it to scene, can be repeated :)
       WritableImage snapshot = vbox.snapshot(new SnapshotParameters(), null);
       vbox.getChildren().add(new ImageView(snapshot));
       System.out.println(vbox.getChildren().size());
     }
   });
   Scene scene = new Scene(new Group(vbox), 300, 250);
   primaryStage.setScene(scene);
   primaryStage.show();
 }
}