package Kamil215691.ZPO.LAB7.Zad1;

import org.jacop.core.*;
import org.jacop.constraints.*;
import org.jacop.search.*;

public class Graph {
    public enum Colours{
        BLUE(3),RED(1), GREEN(4), YELLOW(5), BLACK(2);
        Colours(int i){
            value = i;
        }
        int value;
        public static Colours find(int i){
            for(Colours colour : values()){
                if(colour.value == i){
                    return colour;
                }
            }
            return null;
        }
        static String legend(){
            StringBuilder stringBuilder = new StringBuilder("LEGEND OF COLOURS:\n");
            for(Colours value : Colours.values()){
                stringBuilder.append(value.value);
                stringBuilder.append(". " + value.name() + '\n');
            }
            return stringBuilder.toString();
        }
    }
    public Graph(int i) {
        adjacencyTable = new AdjacencyTable(9);
    }

    public void createStoreAndInitSearch(boolean isCountries) {
        if(adjacencyTable != null) {
            graph = new IntVar[adjacencyTable.n];
            for (int i = 0; i < adjacencyTable.n; ++i) {
                graph[i] = new IntVar(store, isCountries ? Main.Countries.find(i).name(): "v" + i, 1, adjacencyTable.n);
            }
            for(int i = 0; i < adjacencyTable.n; ++i){
                for(int j = 0; j < adjacencyTable.n; ++j){
                    if(adjacencyTable.get(i,j)){
                        store.impose(new XneqY(graph[i], graph[j]));
                    }
                }
            }
            search = new DepthFirstSearch<>();
        }
    }

    class AdjacencyTable{
        int n =0;
        boolean[][] adjacencyTable;

        public AdjacencyTable(int n){
            this.n = n;
            adjacencyTable = new boolean[n][n];
        }

        public String toString(){
            if(adjacencyTable == null) return null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('\\');
            for(int j = 0; j < n; ++j){
                stringBuilder.append(" "  + (j+1) + " ");
            }
            stringBuilder.append('\n');
            for(int i = 0; i < n ; ++i){
                stringBuilder.append(i+1);
                for (int j = 0; j < n; ++j){
                    stringBuilder.append(adjacencyTable[i][j] ? " x " : " . ");
                }
                stringBuilder.append('\n');
            }
            return stringBuilder.toString();
        }
        private void set(int x, int y, boolean value){
            if(n > x && n > y){
                adjacencyTable[x][y] = value;
            }
        }

        public boolean get(int x, int y) {
            if(!(x < n && y < n)) return false;
            return adjacencyTable[x][y];
        }
    }
    public void set(int x, int y, boolean value){
        adjacencyTable.set(x,y,value);
    }
    public boolean get(int x, int y){
        return adjacencyTable.get(x,y);
    }
    private Store store = new Store();
    private IntVar[] graph;
    private AdjacencyTable adjacencyTable;
    private Search<IntVar> search;
    public String getTable(){
        return adjacencyTable.toString();
    }
    public boolean search(){
        SelectChoicePoint<IntVar> select =
                new InputOrderSelect<>(store, graph,
                        new IndomainMin<>());
        return search.labeling(store, select);
    }
    public void printResults(){
        for(IntVar var : graph){
            System.out.println(var.id + " has colour " + Colours.find(var.value()));
        }
    }
}
