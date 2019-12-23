package Kamil215691.ZPO.LAB10.Zad3;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.text.DateFormatter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App extends Application {
    private VBox contents = new VBox();
    public static void main(String[] args) {
        launch(args);
    }
    private ObservableList<String> observableList = FXCollections.observableArrayList();
    private ObservableList<String> observableListActions = FXCollections.observableArrayList();
    @Override
    public void start(Stage primaryStage) throws Exception {
        observableList.add("Lotto");
        observableList.add("LottoPlus");
        observableList.add("MiniLotto");
        observableList.add("MultiMulti");
        observableList.add("EuroJackPot");
        observableList.add("EkstraPensja");
        observableList.add("EkstraPremia");
        observableList.add("SuperSzansa");
        VBox box = new VBox();
        Scene scene = new Scene(box);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        ComboBox<String> games = new ComboBox<>();
        games.setMaxWidth(Double.MAX_VALUE);
        games.setItems(observableList);
        games.getSelectionModel().select(0);
        box.setFillWidth(true);
        box.setMinHeight(500);
        Label text1 = new Label("Choose game");
        text1.setMaxWidth(Double.MAX_VALUE);
        Label text = new Label("Choose action");
        text.setMaxWidth(Double.MAX_VALUE);
        observableListActions.add("Get results in chosen date");
        observableListActions.add("Get histogram of drawn numbers during chosen year");
        observableListActions.add("Get histogram of drawn numbers between chosen dates");
        Label command = new Label("Choose date");
        Label command1 = new Label("Choose two dates");
        Label command2 = new Label("Choose year");
        DatePicker oneDate = new DatePicker();
        oneDate.setEditable(false);
        command.setMaxWidth(Double.MAX_VALUE);
        command1.setMaxWidth(Double.MAX_VALUE);
        command2.setMaxWidth(Double.MAX_VALUE);
        command.setTextAlignment(TextAlignment.CENTER);
        command1.setTextAlignment(TextAlignment.CENTER);
        command2.setTextAlignment(TextAlignment.CENTER);
        DatePicker datePicker = new DatePicker();
        datePicker.setEditable(false);
        DatePicker datePicker1 = new DatePicker();
        datePicker1.setEditable(false);
        datePicker.setMaxWidth(Double.MAX_VALUE);
        datePicker1.setMaxWidth(Double.MAX_VALUE);
        oneDate.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> chooseAction = new ComboBox<>();
        chooseAction.setMaxWidth(Double.MAX_VALUE);
        GridPane hBox = new GridPane();
        hBox.setMaxWidth(Double.MAX_VALUE);
        ColumnConstraints constraint = new ColumnConstraints();
        constraint.setHgrow(Priority.ALWAYS);
        hBox.getColumnConstraints().add(constraint);
        chooseAction.setItems(observableListActions);
        VBox vBox1 = new VBox();
        vBox1.getChildren().addAll(command,oneDate);
        hBox.add(vBox1,0,0);
        VBox vBox2 = new VBox();
        vBox2.setFillWidth(true);
        VBox vBox3 = new VBox();
        vBox3.setFillWidth(true);
        HBox hBox1 = new HBox();
        vBox2.setMaxWidth(Double.MAX_VALUE);
        vBox3.setMaxWidth(Double.MAX_VALUE);
        hBox1.setMaxWidth(Double.MAX_VALUE);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(command1,hBox1);
        hBox1.getChildren().addAll(vBox2,vBox3);
        vBox2.getChildren().addAll( datePicker);
        vBox3.getChildren().add(datePicker1);
        hBox.add(vBox,0,0);
        box.getChildren().add(text1);
        box.getChildren().add(games);
        box.getChildren().add(text);
        box.getChildren().add(chooseAction);
        box.getChildren().add(hBox);
        Button apply = new Button("Apply");
        HBox hBox2 = new HBox();
        box.getChildren().addAll(hBox2);
        hBox2.setAlignment(Pos.BOTTOM_RIGHT);
        hBox2.getChildren().addAll(apply);
        primaryStage.setTitle("Lotto parser");
        ComboBox<Integer> years = new ComboBox<>();
        List<Integer> list =  IntStream.range(0,9999).boxed().collect(Collectors.toList());
        ObservableList<Integer> integers = FXCollections.observableArrayList();
        integers.addAll(list);
        years.setItems(integers);
        VBox vBox4 = new VBox();
        hBox.add(vBox4,0,0);
        vBox4.getChildren().addAll(command2, years);
        years.getSelectionModel().select(2019);
        years.setMaxWidth(Double.MAX_VALUE);
        ScrollPane field = new ScrollPane();

        chooseAction.setOnAction(event -> {
            switch (((ComboBox)event.getSource()).getSelectionModel().getSelectedIndex()){
                case 0:{
                    oneDate.setVisible(true);
                    command.setVisible(true);
                    datePicker.setVisible(false);
                    datePicker1.setVisible(false);
                    command1.setVisible(false);
                    command2.setVisible(false);
                    years.setVisible(false);
                    vBox1.toFront();
                    vBox4.toBack();
                    vBox.toBack();
                    break;
                }
                case 1:{
                    oneDate.setVisible(false);
                    command.setVisible(false);
                    datePicker.setVisible(false);
                    datePicker1.setVisible(false);
                    command1.setVisible(false);
                    command2.setVisible(true);
                    vBox1.toBack();
                    vBox.toBack();
                    vBox4.toFront();
                    years.setVisible(true);
                    break;
                }
                case 2: {
                    oneDate.setVisible(false);
                    command.setVisible(false);
                    datePicker.setVisible(true);
                    datePicker1.setVisible(true);
                    command1.setVisible(true);
                    command2.setVisible(false);
                    years.setVisible(false);
                    vBox1.toBack();
                    vBox.toFront();
                    vBox4.toBack();
                    break;
                }
            }
        });
        chooseAction.getSelectionModel().select(0);
        box.getChildren().add(field);
        field.setMinHeight(500);
        field.setMaxWidth(Double.MAX_VALUE);

        field.setPadding(new Insets(5,5,5,5));
        field.setContent(contents);
        chooseAction.fireEvent(new ActionEvent());
        apply.setOnAction(event -> {
            String className = games.getSelectionModel().getSelectedItem();
            try {
                LottoParser parser = LottoParser.of(className);
                StringBuilder msg = new StringBuilder(className);
                LocalDateTime now = LocalDateTime.now();

                msg.append(" ").append(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                msg.append(" wyniki/histogram:\n");
                switch(chooseAction.getSelectionModel().getSelectedIndex()){
                    case 0:{
                        LocalDate date = oneDate.getValue();
                        if(date == null){
                            throw new LottoParser.IncorrectDateException("Date cannot be null");
                        }
                        msg.append(date).append(":\n\t");
                        msg.append(parser.getResultFromDate(date));
                        addLog(msg.toString(), Color.STEELBLUE, box.getWidth() - 25);
                        break;
                    }
                    case 1:{
                        int year = years.getSelectionModel().getSelectedItem();
                        msg.append(year).append(":\n\t");
                        msg.append(parser.getHistogramFromYear(year));
                        addLog(msg.toString(), Color.PALEVIOLETRED, box.getWidth() - 25);
                        break;
                    }
                    case 2:{
                        LocalDate date = datePicker.getValue();
                        LocalDate date1 = datePicker1.getValue();
                        if(date == null || date1 == null){
                            throw new LottoParser.IncorrectDateException("Date cannot be null");
                        }
                        msg.append(date).append(" <-> ").append(date1).append(":\n\t");
                        msg.append(parser.getHistogramBetweenDates(date,date1));
                        addLog(msg.toString(), Color.FORESTGREEN, box.getWidth() - 25);
                        break;
                    }
                }
            } catch (Exception e) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream stream = new PrintStream(byteArrayOutputStream);
                e.printStackTrace(stream);
                addLog(new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8), Color.RED, box.getWidth() - 25);
            }
        });
        box.setMinWidth(500);
        primaryStage.show();
    }
    private void addLog(String msg, Color color, double width){
        Text text = new Text(msg);
        text.setWrappingWidth(width);
        text.setFill(color);
        Platform.runLater(()-> contents.getChildren().addAll(text));
    }
}
