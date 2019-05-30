import java.awt.*;

public class Point {
    public double X;
    public double Y;
    public String color;
    Boolean centroid;
    Point(int X, int Y){
        this.X=X;
        this.Y=Y;
    }

    public Point(int x, int y, String color, Boolean centroid) {
        X = x;
        Y = y;
        this.color = color;
        this.centroid = centroid;
    }



}
