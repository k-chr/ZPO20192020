package Kamil215691.ZPO.LAB5.Zad2;

import Kamil215691.ZPO.LAB5.Zad1.Book;

public class HistoricalPublisherHouse extends PublisherHouse {

    private final String associatedAuthor;

    public HistoricalPublisherHouse(String author){
        super();
        this.associatedAuthor = author;
    }

    @Override
    public Book createBook(String title, Long numberOfPages){
        return new HistoricalNovel(associatedAuthor, title, numberOfPages);
    }
}
