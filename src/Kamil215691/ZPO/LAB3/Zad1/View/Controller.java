package Kamil215691.ZPO.LAB3.Zad1.View;

import Kamil215691.ZPO.LAB3.Zad1.Main;
import Kamil215691.ZPO.LAB3.Zad1.Model.Cyclist;
import Kamil215691.ZPO.LAB3.Zad1.Model.Race;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Controller {
    private ObservableList<Cyclist> participants = FXCollections.observableArrayList();
    private ObservableList<Cyclist> leaders = FXCollections.observableArrayList();
    private AbstractQueue<Cyclist> cyclists = new PriorityQueue<>(Comparator.comparingInt(Cyclist::getStartNumber));
    private Integer elapsedTime = 0;

    public Label errorLabel;
    public TableView<Cyclist> leaderBoard;
    public TableColumn<Cyclist, String> leaderName;
    public TableColumn<Cyclist, Number> timeScore;
    public TableColumn<Cyclist, Number> rankColumn;
    public TableColumn<Cyclist, Number> noResColumn;

    public Label nameLabel;
    public Label startLabel;
    public Label scoreLabel;
    public Label rankLabel;

    public TableView<Cyclist> personTable;
    public TableColumn<Cyclist, String> lastNameColumn;
    public TableColumn<Cyclist, Number>  noColumn;
    public TableColumn<Cyclist, Number> startTime;
    public TableColumn<Cyclist, Number> currTime;

    private ExecutorService service = Executors.newFixedThreadPool(2);
    private volatile Race race;
    private volatile Logger logger;

    @FXML
    private void initialize(){
        lastNameColumn.setCellValueFactory(cellData-> cellData.getValue().lastNameProperty());
        noColumn.setCellValueFactory(cellData-> cellData.getValue().startNumberProperty());
        startTime.setCellValueFactory(cellData-> cellData.getValue().timeOfStartProperty());
        currTime.setCellValueFactory(cellData-> cellData.getValue().elapsedTimeProperty());

        leaderName.setCellValueFactory(cellData-> cellData.getValue().lastNameProperty());
        noResColumn.setCellValueFactory(cellData-> cellData.getValue().startNumberProperty());
        timeScore.setCellValueFactory(cellData-> cellData.getValue().timeProperty());
        rankColumn.setCellValueFactory(cellData-> cellData.getValue().finalPositionProperty());
    }

    public void closeApp(ActionEvent actionEvent) {
        Main.close();
    }

    public void runAnotherRace(ActionEvent actionEvent) throws Exception{
        if(race == null || Arrays.stream(race.getStatesOfCyclist()).allMatch(state->state == 1)) {
            race = new Race();
            errorLabel.setText("");
            logger = Logger.getLogger("Race logger");
            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd_HHmmss");
            logger.setUseParentHandlers(false);
            String name = "C:\\Users\\Public\\logs\\log_"+ format.format(Calendar.getInstance().getTime()) + ".txt";
            File file = new File(name);
            boolean var = file.createNewFile();
            if(!var){
                errorLabel.setText("Cannot create log file!");
            }
            FileHandler fileHandler = new FileHandler(file.getAbsolutePath());
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            elapsedTime = 0;
            leaders.clear();
            cyclists.clear();
            participants.clear();
            personTable.setItems(null);
            leaderBoard.setItems(null);
            personTable.setItems(participants);
            leaderBoard.setItems(leaders);
            nameLabel.setText("---------------------");
            rankLabel.setText("--");
            startLabel.setText("--");
            scoreLabel.setText("---");
            startRace();
        }

        else{
            errorLabel.setText("Cannot run another simulation, because the previous one hasn't finished yet");
        }
    }

    private void startRace(){
        participants.addAll(race.getParticipants());
        cyclists.clear();
        cyclists.addAll(participants);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (Arrays.stream(race.getStatesOfCyclist()).allMatch(integer -> integer == 1)) {
                    timer.cancel();
                    return;
                }

                if (elapsedTime % 60 == 0) {
                    runNextCyclist(elapsedTime);
                }

                ++elapsedTime;
                refreshParticipants();
            }
        }, 0,(int)(1000*race.getFactor()));

    }

    private void refreshParticipants() {
        for (int i = 0; i < participants.size(); ++i) {
            final int var = i;
            if (race.getStatesOfCyclist()[i] == 0) {
                Platform.runLater(()->participants.get(var).setElapsedTime(Math.min(participants.get(var).getTime(), elapsedTime - var * 60)));
            } else if (race.getStatesOfCyclist()[i] == 1) {
                Platform.runLater(()->participants.get(var).setElapsedTime(participants.get(var).getTime()));
            }
        }
    }

    private void runNextCyclist(int start){
        Timer timer = new Timer();
        Cyclist cyclist = cyclists.poll();

        if(cyclist != null) {
            service.submit(()->logger.info("Cyclist: \n\t" + cyclist.getLastName() + "\nhas already started the race"));
            participants.get(cyclist.getStartNumber() - 1).setTimeOfStart(start);
            race.getStatesOfCyclist()[cyclist.getStartNumber()-1] = 0;
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    race.addCyclistWhoFinishedRace(cyclist);
                    Integer position = race.getPosition(cyclist);
                    cyclist.setFinalPosition(position);
                    Platform.runLater(() -> {
                        nameLabel.setText(cyclist.getLastName());
                        rankLabel.setText(position.toString());
                        startLabel.setText(((Integer)cyclist.getStartNumber()).toString());
                        scoreLabel.setText(((Integer)cyclist.getTime()).toString());
                    });
                    race.getStatesOfCyclist()[cyclist.getStartNumber()-1] = 1;
                    updateLeaders();
                    service.submit(()->logger.info("Cyclist: \n\t" + cyclist.getLastName() + "\nhas already finished the race with time result: " + cyclist.getTime()));
                    timer.cancel();
                }
            };
            timer.schedule(timerTask,(int)((cyclist.getTime() * 1000) * race.getFactor()));
        }
    }

    private void updateLeaders(){
        Platform.runLater(()-> {
            leaders.clear();
            ArrayList<Cyclist> list = race.getFourHorsemanOfTheApocalypse();
            int i = 1;
            int repeats = 1;
            Cyclist prev = null;
            for (Cyclist cyclist : list) {
                if(!(prev != null && prev.getTime() == cyclist.getTime())) {
                    if(prev != null) {
                        i += repeats;
                        repeats = 1;
                    }
                }
                else{
                    ++repeats;
                }
                cyclist.setFinalPosition(i);
                prev = cyclist;
            }
            leaders.addAll(list);
        });
    }
}
