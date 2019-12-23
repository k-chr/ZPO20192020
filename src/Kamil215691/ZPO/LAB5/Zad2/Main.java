package Kamil215691.ZPO.LAB5.Zad2;

import Kamil215691.ZPO.LAB5.Zad1.BookWithBinding;
import Kamil215691.ZPO.LAB5.Zad1.BookWithHardCover;
import Kamil215691.ZPO.LAB5.Zad1.Publication;

public class Main {

    public static void main(String[] args){
        PublisherHouse publisherHouse = PublisherHouse.getInstance("J.R.R. Tolkien");

        Publication publication = publisherHouse.createBook("Lord of the Rings - The Fellowship of the Ring", (long) 423);
        publication = new BookWithBinding( new BookWithHardCover(publication));

        System.out.println(publication);
    }
}
