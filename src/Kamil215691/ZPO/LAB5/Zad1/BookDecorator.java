package Kamil215691.ZPO.LAB5.Zad1;

import java.util.Arrays;
import java.util.HashSet;

public class BookDecorator implements Publication {
    private Publication publicationToDecorate;
    public HashSet<Class<?>> classesAndInterfaces = new HashSet<>();
    public BookDecorator(Publication publication){
        this.publicationToDecorate = publication;
        classesAndInterfaces.add(publication.getClass());
        classesAndInterfaces.addAll(Arrays.asList(publication.getClass().getClasses()));
        if(!(publication instanceof Book))
            classesAndInterfaces.addAll(((BookDecorator)publication).classesAndInterfaces);
    }
    @Override
    public String getAuthor() {
        return publicationToDecorate.getAuthor();
    }

    @Override
    public String getTitle() {
        return publicationToDecorate.getTitle();
    }

    @Override
    public Long getPages() {
        return publicationToDecorate.getPages();

    }
    @Override
    public String toString(){
        return publicationToDecorate.toString();
    }
}
