package Kamil215691.ZPO.LAB11.Zad1;

import java.io.Serializable;
import java.net.ServerSocket;

public class Credentials implements Serializable {

    public String getName() {
        return this.identification.getName();
    }

    public void setName(String name) {
        this.identification.setName(name);
    }

    public String getLastName() {
        return this.identification.getLastName();
    }

    public void setLastName(String lastName) {
        this.identification.setLastName(lastName);
    }

    public Stage getStatus() {
        return status;
    }

    public void setStatus(Stage status) {
        this.status = status;
    }

    private Identification identification;
    private Stage status;

    public static class Identification implements Serializable {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        private String name;
        private String lastName;
    }

    public enum Stage implements Serializable{
        START(0xFFFFFFFF),
        FIRST_IS(0xFFFFFFFD),
        SECOND_IS(0xFFFFFFFB),
        THIRD_IS(0xFFFFFFF9),
        FOURTH_IS(0xFFFFFFF7),
        FIFTH_IS(0xFFFFFFF5),
        SIXTH_IS(0xFFFFFFF3),
        FINNISH(0xFFFFFFF1);
        Stage(int num){
            value = num;
        }
        private int value;

        public int getValue() {
            return value;
        }
        public static Stage valueOf(int num){
            Stage stage = null;
            switch (num){
                case 0xFFFFFFFF:
                    stage = START;
                    break;
                case 0xFFFFFFFD:
                    stage = FIRST_IS;
                    break;
                case 0xFFFFFFFB:
                    stage = SECOND_IS;
                    break;
                case 0xFFFFFFF9:
                    stage = THIRD_IS;
                    break;
                case 0xFFFFFFF7:
                    stage = FOURTH_IS;
                    break;
                case 0xFFFFFFF5:
                    stage = FIFTH_IS;
                    break;
                case 0xFFFFFFF3:
                    stage = SIXTH_IS;
                    break;
                case 0xFFFFFFF1:
                    stage = FINNISH;
                    break;
            }
            return stage;
        }
    }
}
