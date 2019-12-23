package Kamil215691.ZPO.Test;

import java.io.Serializable;
import java.util.Objects;

public class Main {

    public static void main(String ... args) {
        MiędzyTwarz międzyTwarz = ( Object b) -> Objects.compare(new Object(), b, null);
    }

    interface MiędzyTwarz extends Serializable, Comparable{

    }
}
