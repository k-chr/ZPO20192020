package Kamil215691.ZPO.LAB4.Zad2;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Test test = new Test();
        test.randomizeTest();
        test.runTest();
    }
}
