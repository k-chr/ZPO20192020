package Kamil215691.ZPO.LAB3.Zad1.Model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class Race {


    private volatile PriorityQueue<Cyclist> finishedQueue = new PriorityQueue<>(Comparator.comparingInt(Cyclist::getTime));
    private ArrayList<Cyclist> participants = new ArrayList<>();


    private volatile int[] statesOfCyclist = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

    public int[] getStatesOfCyclist() {
        return statesOfCyclist;
    }

    public double getFactor() {
        return 0.04;
    }

    public synchronized ArrayList<Cyclist> getFourHorsemanOfTheApocalypse(){
        PriorityQueue<Cyclist> tempQueue = new PriorityQueue<>(finishedQueue);
        ArrayList<Cyclist> outArrayList = new ArrayList<>();
        while(outArrayList.size() != 4 && !tempQueue.isEmpty()){
            outArrayList.add(tempQueue.poll());
        }
        return outArrayList;
    }


    public Race () throws Exception{
        randomizeParticipants();
    }

    private void randomizeParticipants() throws Exception{
        URL urlToTextDataBase = new URL("http://szgrabowski.kis.p.lodz.pl/zpo19/nazwiska.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(urlToTextDataBase.openStream()));
        Random random = new Random();
        String inputLine;
        ArrayList<String> lastNames = new ArrayList<>();

        while ((inputLine = in.readLine()) != null)
            lastNames.add(inputLine);

        while(participants.size() != 12){
            int idx = random.nextInt(Integer.MAX_VALUE)%(lastNames.size());
            String lastName = lastNames.get(idx);

            if(participants.stream().noneMatch(participant -> participant.getLastName().equals(lastName))){
                double time = random.nextGaussian()*40 + 290;
                participants.add(new Cyclist(lastName, time > 350 ? 350 : (time < 240 ? 240 : (int)Math.round(time)), participants.size() + 1));
            }
        }

        in.close();
    }

    public void addCyclistWhoFinishedRace(Cyclist cyclist){
        finishedQueue.add(cyclist);
    }

    public Integer getPosition(Cyclist cyclist){
        PriorityQueue<Cyclist> queue = new PriorityQueue<>(finishedQueue);
        int result = -1;
        int i = 1, repeats = 1;
        Cyclist prev = null;
        while (!queue.isEmpty()){
            Cyclist temp = queue.poll();
            if(prev != null && temp != null){
                if(prev.getTime() == temp.getTime()){
                    ++repeats;
                }
                else{
                    i += repeats;
                    repeats = 1;
                }
            }
            if(temp != null && cyclist.getLastName().equals(temp.getLastName())){
                result = i;
                break;
            }
            prev = temp;
        }
        return result;
    }

    public ArrayList<Cyclist> getParticipants(){
        return participants;
    }


}
