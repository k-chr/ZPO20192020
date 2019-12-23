package Kamil215691.ZPO.LAB8.Zad1;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class App {

    private static MaxSearchAlgorithms searchAlgorithms = new MaxSearchAlgorithms(new int[]{4, 10, 3, 7, 4, 1, 6, 2});

    public static void main(String[] args){

        Arrays.stream(searchAlgorithms.getClass().getDeclaredMethods()).filter(method -> method.getName().contains("scan")).forEach(method -> {
            try {
                System.out.println(method.getName() + " " + method.invoke(searchAlgorithms));
            } catch (IllegalAccessException |InvocationTargetException e) {
                e.printStackTrace();
            }
        });


    }
}
