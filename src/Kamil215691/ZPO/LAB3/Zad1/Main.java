package Kamil215691.ZPO.LAB3.Zad1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Tour de Pologne");
        this.primaryStage.setMinWidth(608);
        this.primaryStage.setMinHeight(400);
        addWindow();
    }

    public static void close(){
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void addWindow() throws Exception{
        BorderPane borderPane = FXMLLoader.load(getClass().getResource("View/rootLayout.fxml"));
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((event)->System.exit(0));
        primaryStage.show();
    }
}
