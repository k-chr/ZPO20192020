package Kamil215691.ZPO.LAB5.Zad1;

public class Book implements Publication {

    private final Long pages;
    private final String author;
    private final String title;
    public Book(String author, String title, long pages){
        this.author = author;
        this.title = title;
        this.pages = pages;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Long getPages() {
        return pages;
    }

    @Override
    public String toString(){
        return String.format("| %s | %s | %s ", getAuthor(), getTitle(), getPages());
    }
}
