package Kamil215691.ZPO.LAB7.Zad1;

public class Main {
    public enum Countries {
        POLAND(0),
        GERMANY(1),
        CZECHIA(2),
        SLOVAKIA(3),
        AUSTRIA(4),
        HUNGARY(5),
        UKRAINE(6),
        BELARUS(7),
        DENMARK(8);
        public int value;
        Countries(int i) {
            value = i;
        }
        static String legend(){
            StringBuilder stringBuilder = new StringBuilder("LEGEND OF COUNTRIES:\n");
            for(Countries value : Countries.values()){
                stringBuilder.append(value.value + 1);
                stringBuilder.append(". " + value.name() + '\n');
            }
            return stringBuilder.toString();
        }
        public static Countries find(int value){
            for(Countries country : values()){
                if(country.value == value) return country;
            }
            return null;
        }
    }
    public static void main(String[] args){
        Graph graph = new Graph(9);
        graph.set(Countries.POLAND.value,Countries.GERMANY.value, true );
        graph.set(Countries.POLAND.value,Countries.UKRAINE.value, true );
        graph.set(Countries.POLAND.value,Countries.BELARUS.value, true );
        graph.set(Countries.POLAND.value,Countries.SLOVAKIA.value, true );
        graph.set(Countries.POLAND.value,Countries.CZECHIA.value, true );
        graph.set(Countries.GERMANY.value,Countries.POLAND.value, true );
        graph.set(Countries.GERMANY.value,Countries.AUSTRIA.value, true );
        graph.set(Countries.GERMANY.value,Countries.DENMARK.value, true );
        graph.set(Countries.GERMANY.value,Countries.CZECHIA.value, true );
        graph.set(Countries.UKRAINE.value,Countries.POLAND.value, true );
        graph.set(Countries.UKRAINE.value,Countries.HUNGARY.value, true );
        graph.set(Countries.UKRAINE.value,Countries.SLOVAKIA.value, true );
        graph.set(Countries.UKRAINE.value,Countries.BELARUS.value, true );
        graph.set(Countries.BELARUS.value,Countries.POLAND.value, true );
        graph.set(Countries.BELARUS.value,Countries.UKRAINE.value, true );
        graph.set(Countries.CZECHIA.value,Countries.POLAND.value, true );
        graph.set(Countries.CZECHIA.value,Countries.SLOVAKIA.value, true );
        graph.set(Countries.CZECHIA.value,Countries.POLAND.value, true );
        graph.set(Countries.CZECHIA.value,Countries.AUSTRIA.value, true );
        graph.set(Countries.AUSTRIA.value,Countries.HUNGARY.value, true );
        graph.set(Countries.AUSTRIA.value,Countries.SLOVAKIA.value, true );
        graph.set(Countries.AUSTRIA.value,Countries.CZECHIA.value, true );
        graph.set(Countries.AUSTRIA.value,Countries.GERMANY.value, true );
        graph.set(Countries.HUNGARY.value,Countries.SLOVAKIA.value, true );
        graph.set(Countries.HUNGARY.value,Countries.UKRAINE.value, true );
        graph.set(Countries.HUNGARY.value,Countries.AUSTRIA.value, true );
        graph.set(Countries.SLOVAKIA.value,Countries.POLAND.value, true );
        graph.set(Countries.SLOVAKIA.value,Countries.HUNGARY.value, true );
        graph.set(Countries.SLOVAKIA.value,Countries.UKRAINE.value, true );
        graph.set(Countries.SLOVAKIA.value,Countries.CZECHIA.value, true );
        graph.set(Countries.SLOVAKIA.value,Countries.AUSTRIA.value, true );
        graph.set(Countries.DENMARK.value, Countries.GERMANY.value, true);
        System.out.println(Countries.legend());
        System.out.println(Graph.Colours.legend());
        System.out.println(graph.getTable());
        graph.createStoreAndInitSearch(true);
        boolean result = graph.search();
        if(result)
            graph.printResults();
        else
            System.out.print("No results provided");
    }
}
