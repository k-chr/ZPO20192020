package Kamil215691.ZPO.Kolokwium;

public class Main {
    public static void main(String[] args) throws Exception{
        Maze maze = new Maze("map_21x21.png");
        System.out.println(maze.getStart());
        maze.goThroughThisFatalMaze(maze.getStart().getKey(), maze.getStart().getValue());
            maze.drawSet();
            maze.save();
        Maze maze1 = new Maze("map_31x31.png");
        System.out.println(maze1.getStart());
        maze1.goThroughThisFatalMaze(maze1.getStart().getKey(), maze1.getStart().getValue());
        maze1.drawSet();
        maze1.save();
        Maze maze2 = new Maze("map_51x51.png");
        System.out.println(maze2.getStart());
        maze2.goThroughThisFatalMaze(maze2.getStart().getKey(), maze2.getStart().getValue());
        maze2.drawSet();
        maze2.save();
    }
}
