import javafx.application.Application;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;

public class JukeBox extends Application {

  private AudioClip[] music;
  private AudioClip current;

  // Create a combo box for selecting countries
  private ComboBox<String> cbo; 
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) throws Exception {
    // Set text description
       String[] musicNames = {"Make A Selection...", "Western Beat",
               "Classical Melody", "Jeopardy Theme", "New Age Rythm",
               "Eighties Jam", "Alfred Hitchcock's Theme"};

      current = null;

      music = new AudioClip[7];
      music[0] = null;  // Corresponds to "Make a Selection..."
      music[1] = new AudioClip(getClass().getResource("westernBeat.wav").toExternalForm());
      music[2] = new AudioClip(getClass().getResource("classical.wav").toExternalForm());
      music[3] = new AudioClip(getClass().getResource("jeopardy.wav").toExternalForm());
      music[4] = new AudioClip(getClass().getResource("newAgeRythm.wav").toExternalForm());
      music[5] = new AudioClip(getClass().getResource("eightiesJam.wav").toExternalForm());
      music[6] = new AudioClip(getClass().getResource("hitchcock.wav").toExternalForm());

    // Add combo box and description pane to the border pane
    BorderPane pane = new BorderPane();
    Label title = new Label("Java JukeBox");
    pane.setTop(title);
    
    cbo = new ComboBox<>(); 
    ObservableList<String> items = FXCollections.observableArrayList(musicNames);
    cbo.getItems().addAll(items);
    pane.setCenter(cbo);

    HBox hbox = new HBox();
    Button playBtn = new Button("Play");
    Button stopBtn = new Button("Stop");
    hbox.getChildren().addAll(playBtn,stopBtn);
    hbox.setAlignment(Pos.CENTER);
    pane.setBottom(hbox);
    
    playBtn.setOnAction(e -> {
       if (current != null) {
          current.play();
       }
    });

    stopBtn.setOnAction(e -> {
       if (current != null)
          current.stop();
    });

    cbo.setOnAction(e -> selectAudio(items.indexOf(cbo.getValue())));
    
    pane.setAlignment(title,Pos.CENTER);

    Scene scene = new Scene(pane);
    primaryStage.setTitle("Java JukeBox"); 
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /** Change to the selected music */
  public void selectAudio(int index) {
     if (current != null)
        current.stop();

     current = music[index];    
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
