package Kamil215691.ZPO.LAB5.Zad2;

import Kamil215691.ZPO.LAB5.Zad1.Book;

public class FantasyNovel extends Book {

    public FantasyNovel(String associatedAuthor, String title, Long numberOfPages) {
        super(associatedAuthor, title, numberOfPages);
    }
    @Override
    public String toString(){
        return super.toString() + " | Fantasy Novel ";
    }
}
