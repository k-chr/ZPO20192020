package Kamil215691.ZPO.LAB5.Zad1;

public class BookWithDedication extends BookDecorator{
    private String dedication;

    public BookWithDedication(Publication book, String dedication){
        super(book);
        if(classesAndInterfaces.contains(BookWithDedication.class)){
            throw new UnsupportedOperationException("Provided book already has one dedication");
        }
        this.dedication = dedication;
    }


    @Override
    public String toString(){
        return super.toString() + " | " + dedication;
    }

}
