package Kamil215691.ZPO.LAB5.Zad1;

public class BookWithBinding extends BookDecorator {

    public BookWithBinding(Publication book){
        super(book);
        if(classesAndInterfaces.contains( BookWithBinding.class)){
            throw new UnsupportedOperationException("To provided book has already been applied binding");
        }
        if((!(classesAndInterfaces.contains( BookWithSoftCover.class)) && !(classesAndInterfaces.contains( BookWithHardCover.class)))){
            throw new UnsupportedOperationException("Provided book has no cover to apply binding");
        }
    }

    @Override
    public String toString(){
        return super.toString() + " | With binding ";
    }

}
