package Kamil215691.ZPO.LAB5.Zad2;

import Kamil215691.ZPO.LAB5.Zad1.Book;

public abstract class PublisherHouse {

    public static PublisherHouse getInstance(String author){
        String [] authorParts = author.split(" ");
        switch(authorParts[authorParts.length -1]){
            case "Pratchett" : case "Tolkien" : case "Brett":{
                return new FantasyPublisherHouse(author);
            }
            case "Kubiak": case "Kraszewski": case "Parandowski": {
                return new HistoricalPublisherHouse(author);
            }
            case "Doyle": case "Christie": case "Puzo": {
                return new CrimeNovelPublisherHouse(author);
            }
            default: {
                return null;
            }
        }
    }

    abstract public Book createBook(String title, Long numberOfPages);
}
