package Kamil215691.ZPO.LAB5.Zad2;

import Kamil215691.ZPO.LAB5.Zad1.Book;

public class FantasyPublisherHouse extends PublisherHouse {

    private String associatedAuthor;
    public FantasyPublisherHouse(String author){
        associatedAuthor = author;
    }

    @Override
    public Book createBook(String title, Long numberOfPages){
        return new FantasyNovel(associatedAuthor, title, numberOfPages);
    }
}
