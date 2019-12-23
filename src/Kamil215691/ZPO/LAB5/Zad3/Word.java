package Kamil215691.ZPO.LAB5.Zad3;

public class Word implements Comparable{
    private String data;
    private long currDay;

    public Word (String data, long currDay){
        this.currDay = currDay;
        this.data = data;
    }

    public Word (Word other){
        this.data = other.data;
        this.currDay = other.currDay;
    }

    public String getData() {
        return data;
    }

    public long getCurrDay() {
        return currDay;
    }

    public void setCurrDay(long currDay) {
        this.currDay = currDay;
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof  Word)) return -1;
        return this.data.compareTo(((Word)o).data);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof  Word)) return false;
        return data.equals(((Word)o).data);
    }

    @Override
    public int hashCode(){
        return data.hashCode();
    }
}
