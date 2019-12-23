package Kamil215691.ZPO.LAB5.Zad1;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Publication k1 = new Book("Adam Mickiewicz", "Pan Tadeusz", 340); Publication k2 = new Book("Adam Mickiewicz", "Dziady", 130);

        Publication kk1 = new BookWithSoftCover(k1);

        Publication k3 =    new BookWithDedication(kk1, "Drogiej Hani - Adam Mickiewicz");

        Publication kk2 = new BookWithHardCover(k2);
        //System.out.println(kk2);
        //Publication fakeBook = new BookWithBinding(k1);  // wyjątek! Nie można obłożyć obwolutą książki, która nie posiada okładki

        Publication kkk2 = new BookWithBinding(kk2); // a tu OK

        //Publication odrzut = new BookWithBinding(kkk2);  // wyjątek! Obwoluta może być jedna

        Publication bookWithDedication =    new BookWithDedication(kkk2, "Drogiej Hani - Adam Mickiewicz");

        System.out.println(bookWithDedication); // wypisuje: | Adam Mickiewicz | Dziady    |  130 | Okładka twarda | Drogiej Hani - Adam Mickiewicz |

        Publication invalidBook = new BookWithDedication(   bookWithDedication, "Haniu, to nieprawda, Dziady napisałem ja, Julek Słowacki!");   // wyjątek! Autograf może być tylko jeden

    }
}
