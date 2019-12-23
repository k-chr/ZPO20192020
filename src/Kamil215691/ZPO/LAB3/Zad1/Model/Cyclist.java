package Kamil215691.ZPO.LAB3.Zad1.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cyclist {
    private final StringProperty lastName;
    private final IntegerProperty time;
    private IntegerProperty timeOfStart;
    private final IntegerProperty startNumber;
    private IntegerProperty finalPosition;


    private IntegerProperty elapsedTime;

    public Cyclist(String lastName, int time, int startNumber) {
        this.lastName = new SimpleStringProperty( lastName);
        this.time = new SimpleIntegerProperty(time);
        this.startNumber = new SimpleIntegerProperty(startNumber);
        this.timeOfStart = new SimpleIntegerProperty(-1);
        this.finalPosition = new SimpleIntegerProperty(-1);
        this.elapsedTime = new SimpleIntegerProperty(-1);
    }

    public String getLastName() {
        return lastName.get();
    }

    public int getTime() {
        return time.get();
    }

    public void setTimeOfStart(int timeOfStart) {
        this.timeOfStart.set(timeOfStart);
    }

    public IntegerProperty timeOfStartProperty() {
        return timeOfStart;
    }

    public IntegerProperty startNumberProperty() {
        return startNumber;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public IntegerProperty timeProperty() {
        return time;
    }

    public int getStartNumber() {
        return startNumber.get();
    }


    public IntegerProperty finalPositionProperty() {
        return finalPosition;
    }

    public void setFinalPosition(Integer finalPosition) {
        this.finalPosition.set(finalPosition);
    }


    public IntegerProperty elapsedTimeProperty() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime.set(elapsedTime);
    }
}
