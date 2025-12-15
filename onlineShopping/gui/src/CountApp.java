//Write an GUI application(Counter) as shown in the Fig 11.6. Each time the “Count”
//button is clicked, the counter value shall increase by 6


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CountApp extends Application {
    private int count = 0;
    private Label countLabel;

    @Override
    public void start(Stage primaryStage) {
        countLabel = new Label("Count: 0");
        Button countButton = new Button("Count");

        countButton.setOnAction(event -> {
            count += 6;
            countLabel.setText("Count: " + count);
        });

        VBox vbox = new VBox(10, countLabel, countButton);
        vbox.setStyle("-fx-padding: 10;");

        Scene scene = new Scene(vbox, 200, 100);
        primaryStage.setTitle("Counter Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

