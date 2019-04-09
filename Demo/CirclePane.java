import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


class CirclePane extends StackPane {
  private Circle circle = new Circle(50); 
  
  public CirclePane() {
    getChildren().add(circle);
    circle.setStroke(Color.BLACK);
    circle.setFill(Color.WHITE);
  }
  
  public void enlarge() {
    circle.setRadius(circle.getRadius() + 2);
  }
  
  public void shrink() {
    circle.setRadius(circle.getRadius() > 2 ? 
      circle.getRadius() - 2 : circle.getRadius());
  }
}
