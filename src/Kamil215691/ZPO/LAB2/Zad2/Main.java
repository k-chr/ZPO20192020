package Kamil215691.ZPO.LAB2.Zad2;


/**
 * This is the main class of a program
 */
public class Main {

    /**
     * This field indicates the max depth of recursion in drawing ruler, it is helpful to justify ruler.
     */
    public static int maxDepth = 0;
    /**
     * This field indicates the current value of unit of ruler.
     */
    public static int currValue = 0;
    public static void main(String[] args) {
        try {
            System.out.println("************ All in one method ************");
            long time = System.nanoTime();
            drawRulerRecursive(6,4);
            time = System.nanoTime() - time;
            System.out.println("time of all in one method: " + time + "\n");
            System.out.println("************ Dispersed method ************");
            time = System.nanoTime();
            drawRuler(6,4);
            time = System.nanoTime() - time;
            System.out.println("time of dispersed method: " + time + "\n");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method draws a line of a ruler and adds additional spaces to justify ruler
     * @param lineLength This value indicates the length of current line, if it is less than maxDepth, method draws spaces to fill line.
     */
    public static void drawLine(int lineLength) {
        for(int i = 0; i < maxDepth - lineLength; ++i){
            System.out.print(" ");
        }
        for (int i = 0; i < lineLength * 2; ++i) {
            System.out.print("-");
        }
        for(int i = 0; i < maxDepth - lineLength; ++i){
            System.out.print(" ");
        }
        if(lineLength == maxDepth) {
            System.out.printf(" %d", currValue++);
        }
    }

    /**
     * This method divides process of drawing lines: firstly it draws the top neighbours of line of max length and then draws the line of max length and bottom neighbours.
     * @param maxLenOfLine Maximum value of length of a line
     */
    public static void drawLinesRecursive(int maxLenOfLine) {
        if (maxLenOfLine > 0) {
            drawLinesRecursive(maxLenOfLine - 1);
            drawLine(maxLenOfLine);
            drawLinesRecursive(maxLenOfLine - 1);
        }
    }

    /**
     * The main method of drawing Ruler, it calls methods drawing specified lines of ruler in a loop.
     * @param maxValue Indicates the maximum value of ruler in unspecified unit.
     * @param depth Indicates the depth of scale of ruler.
     * @throws Exception If depth is less or equal 0 or maxValue is negative
     */
    public static void drawRuler(int maxValue, int depth) throws Exception{
        if(depth > 0 && maxValue >= 0) {
            currValue = 0;
            maxDepth = depth;
            drawLine(depth);
            for (int i = 1; i <= maxValue; ++i) {
                drawLinesRecursive(depth - 1);
                drawLine(depth);
            }
            maxDepth = 0;
        }
        else{
            throw new Exception("Cannot draw a ruler");
        }
    }

    /**
     * This method draw a ruler without helpers recursively.
     * @param maxValue Indicates the maximum value of ruler in unspecified unit.
     * @param depth Indicates the depth of scale of ruler.
     * @throws Exception If depth is less or equal 0 or maxValue is negative
     */
    public static void drawRulerRecursive(int maxValue, int depth) throws Exception {

        if (depth == 0 && maxDepth == 0) {
            throw new Exception("Cannot draw a ruler");
        }

        if (depth > maxDepth) {
            currValue = 0;
            maxDepth = depth;
            drawLine(depth);
        }

        if (depth > 0 && currValue <= maxValue) {
            drawRulerRecursive(maxValue, depth - 1);
            drawLine(depth);
            // K.C 03:20 11.10.2019 draw inner units and lines
            if(depth == maxDepth && currValue <= maxValue){
                drawRulerRecursive(maxValue, maxDepth);
                if(currValue <= maxValue)
                    drawLine(maxDepth);
            }
            drawRulerRecursive(maxValue, depth - 1);
        }
    }
}