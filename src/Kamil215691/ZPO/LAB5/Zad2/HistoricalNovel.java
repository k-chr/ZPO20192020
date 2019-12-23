package Kamil215691.ZPO.LAB5.Zad2;

import Kamil215691.ZPO.LAB5.Zad1.Book;

public class HistoricalNovel extends Book {

    public HistoricalNovel(String author, String title, long pages) {
        super(author, title, pages);
    }

    @Override
    public String toString(){
        return super.toString() + " | Historical Novel ";
    }
}
