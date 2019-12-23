package Kamil215691.ZPO.Kolokwium;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javafx.util.Pair;
public class Maze {
    enum PartsOfMaze{
        Wall(0xFF00000),
        Free(0xFFFFFFFF),
        Start(0xFFFF0000),
        Stop(0xFF00FF00);
        int partValue;
        PartsOfMaze(int value) {
            partValue = value;
        }
    }
    enum where{
        DEFAULT,UP,DOWN,RIGHT,LEFT
    }
    where Mat[][] ;
    int computedWidth;
    int computedHeight;
    BufferedImage image;
    private String filename;
    public final static int tileWidth = 14;
    public final static int tileHeight = 14;
    private MazeWriter writer = new MazeWriter();
    private MazeReader reader = new MazeReader();
    private Pair<Integer,Integer> start;
    private LinkedHashSet<Pair<Integer,Integer>> set = new LinkedHashSet<>();
    public Maze(String filename) throws  Exception{
        image = ImageIO.read(new File(filename));
        computedHeight = image.getHeight()/tileHeight;
        computedWidth = image.getWidth()/tileWidth;
        start = findStartTile();
        Mat = new where[computedWidth][computedHeight];
        for(int i = 0; i < computedHeight; ++i){
            Mat[i] = new where[computedWidth];
            for(int j =0; j < computedWidth; ++j){
                Mat[i][j] = where.DEFAULT;
            }
        }
        this.filename = filename;
    }
    public Pair<Integer, Integer> getStart(){
        return start;
    }
    private Pair<Integer, Integer> findStartTile(){
        Pair<Integer,Integer> pair = new Pair<>(-1,-1);
        boolean found = false;
        for(int i = 0; i < computedHeight; ++i){
            for(int j = 0; j < computedWidth; ++j){
                if(reader.isStart(j * tileWidth, i * tileHeight)){
                    pair = new Pair<>(j, i);
                    found = true;
                    break;
                }
            }
            if(found) break;
        }
        return pair;
    }

    public void save() throws Exception{
        writer.dumpImage();
    }

    public boolean goThroughThisFatalMaze(int startX, int startY) throws Exception{
        if(!(startX < 0 || startY < 0 || startX > computedWidth || startY > computedHeight)) {
            Pair<Integer, Integer> pair = new Pair<>(startX, startY);
            if (reader.isStop(startX * tileWidth, startY * tileHeight)) {
                System.out.println("tileX = " + startX + ", tileY = " + startY);
                return true;
            }
            if (true) {
//                goThroughThisFatalMaze(startX, startY + 1);
//                goThroughThisFatalMaze(startX, startY - 1);
//                goThroughThisFatalMaze(startX - 1, startY);
//                goThroughThisFatalMaze(startX + 1, startY);
                if ((reader.isStart((startX) * tileWidth, (startY - 1) * tileHeight) || reader.isEmpty(startX * tileWidth, (startY - 1) * tileHeight) || reader.isStop(startX * tileWidth, (startY - 1) * tileHeight)) && Mat[startY - 1][startX] == where.DEFAULT) {
                    Mat[startY][startX] = where.UP;
                    if (goThroughThisFatalMaze(startX, startY - 1)) {
                        System.out.println("go up");
                        return true;
                    }
                    Mat[startY][startX] = where.DEFAULT;
                }
                if ((reader.isStart((startX) * tileWidth, (startY + 1) * tileHeight) || reader.isEmpty(startX * tileWidth, (startY + 1) * tileHeight) || reader.isStop(startX * tileWidth, (startY + 1) * tileHeight)) && Mat[startY + 1][startX] == where.DEFAULT) {
                    Mat[startY][startX] = where.DOWN;
                    if (goThroughThisFatalMaze(startX, startY + 1)) {
                        System.out.println("go down");
                        return true;
                    }
                    Mat[startY][startX] = where.DEFAULT;
                }
                if ((reader.isStart((startX - 1) * tileWidth, (startY) * tileHeight) || reader.isEmpty((startX - 1) * tileWidth, (startY) * tileHeight) || reader.isStop((startX - 1) * tileWidth, (startY) * tileHeight)) && Mat[startY][startX - 1] == where.DEFAULT) {
                    Mat[startY][startX] = where.LEFT;
                    if (goThroughThisFatalMaze(startX - 1, startY)) {
                        System.out.println("go left");
                        return true;
                    }
                    Mat[startY][startX] = where.DEFAULT;
                }
                if ((reader.isStart((startX + 1) * tileWidth, (startY) * tileHeight) || reader.isEmpty((startX + 1) * tileWidth, (startY) * tileHeight) ||  reader.isStop((startX + 1) * tileWidth, (startY) * tileHeight)) && Mat[startY][startX + 1] == where.DEFAULT) {
                    Mat[startY][startX] = where.RIGHT;
                    if (goThroughThisFatalMaze(startX + 1, startY)) {
                        System.out.println("go right");
                        return true;
                    }
                    Mat[startY][startX] = where.DEFAULT;
                }
            }
            return false;
        }

        return false;


    }
    public void drawSet(){
        for(int i = 0; i < computedHeight; ++i){
            for(int j = 0; j < computedWidth; ++j){
                if(Mat[i][j] != where.DEFAULT){
                    writer.drawStep(j * tileWidth, i * tileHeight, Mat[i][j]);
                }
            }
        }

    }
    public class MazeReader {
        public boolean isEmpty(int x, int y){
            if(x < 0 || y < 0 || x > image.getWidth() - tileWidth + 1 || y > image.getWidth() - tileHeight + 1) throw new UnsupportedOperationException("Coordinates are not correct for provided image");
            return image.getRGB(x + 1, y + 1) == PartsOfMaze.Free.partValue;
        }
        public boolean isWall(int x, int y){
            if(x < 0 || y < 0 ||  x > image.getWidth() - tileWidth + 1 || y > image.getWidth() - tileHeight + 1) throw new UnsupportedOperationException("Coordinates are not correct for provided image");
            return image.getRGB(x + 1, y + 1) == PartsOfMaze.Wall.partValue;
        }
        public boolean isStart(int x, int y){
            if(x < 0 || y < 0 ||  x > image.getWidth() - tileWidth + 1 || y > image.getWidth() - tileHeight + 1) throw new UnsupportedOperationException("Coordinates are not correct for provided image");
            return image.getRGB(x + 1, y + 1) == PartsOfMaze.Start.partValue;
        }
        public boolean isStop(int x, int y){
            if(x < 0 || y < 0 ||  x > image.getWidth() - tileWidth + 1 || y > image.getWidth() - tileHeight + 1) throw new UnsupportedOperationException("Coordinates are not correct for provided image");
            return image.getRGB(x + 1, y + 1) == PartsOfMaze.Stop.partValue;
        }
    }

    public class MazeWriter {
        public void drawStep(int x, int y, where wh){
            if(x < 0 || y < 0 ||  x > image.getWidth() - tileWidth + 1 || y > image.getWidth() - tileHeight + 1) throw new UnsupportedOperationException("Coordinates are not correct for provided image");

            switch(wh){
                case DOWN: {
                    for (int i = 0; i < tileHeight; ++i) {
                            image.setRGB(x + tileWidth / 2, y + i + tileHeight / 2, 0xFF0000FF);
                    }
                    break;
                }
                case UP:{
                    for (int i = 0; i < tileHeight; ++i) {
                            image.setRGB(x + tileWidth / 2, y - i + tileHeight / 2, 0xFF0000FF);
                    }
                    break;
                }
                case LEFT: {
                    for(int i = 0; i < tileWidth; ++i){
                        image.setRGB(x + tileWidth/2 - i, y + tileHeight/2, 0xFF0000FF);
                    }
                    break;
                }
                case RIGHT:{
                    for(int i = 0; i < tileWidth; ++i){
                        image.setRGB(x + i + tileWidth/2, y + tileHeight/2, 0xFF0000FF);
                    }
                    break;
                }
            }
        }
        public void dumpImage()throws Exception{
            ImageIO.write(image, "PNG", new File("solved" + filename));
        }
    }

}
