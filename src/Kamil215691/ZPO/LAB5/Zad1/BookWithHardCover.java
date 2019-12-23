package Kamil215691.ZPO.LAB5.Zad1;

public class BookWithHardCover extends BookDecorator{

    public BookWithHardCover(Publication book){
        super(book);
        if(((classesAndInterfaces.contains(BookWithSoftCover.class) )|| (classesAndInterfaces.contains(BookWithHardCover.class) ))){
            throw new UnsupportedOperationException("Provided book to decorate already has one cover");
        }
    }

    @Override
    public String toString(){
        return super.toString() + " | Hard cover ";
    }

}
