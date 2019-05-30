


import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Thread.sleep;

public class Main extends JPanel {
    static List <Point> listaPunktow = new LinkedList<>();
    static List <Point> centroidy = new LinkedList<>();
    static String tab[] = {"1","2", "3","4", "5","6","7","8","9","a","b","c", "d", "e", "f" };
    static Boolean warunek = true;

    static void scanFromFile(){
        Scanner scannerPunktow = null;
        try {
            scannerPunktow = new Scanner(new File("src\\punkty.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        while(scannerPunktow.hasNextLine()) {
            String liniaPunkt = scannerPunktow.nextLine();
            String[] x = liniaPunkt.split(",");
            Boolean cen = true;
            if (x[3].equals("-")) {
                cen = false;
            }
            Point point = new Point(Integer.parseInt(x[0]), Integer.parseInt(x[1]), x[2], cen);
            if (cen) {
                centroidy.add(point);
            } else {
                listaPunktow.add(point);
            }
        }
    }
    static String generateColour(){
        StringBuilder colourBuild = new StringBuilder("#");
        Random generator = new Random();
        for(int i=0; i<6; i++){
            colourBuild.append(tab[generator.nextInt(15)]);
        }
        return colourBuild.toString();
    }
    static void generateRandom(int numberOfPoints, int numberOfCentroid){
        Random generator = new Random();
        for(int i=0; i<numberOfPoints; i++){
            Point point = new Point(generator.nextInt(950), generator.nextInt(950), "#000000", false);
            //int)(pow(generator.nextDouble(),0.5)*950)
            //System.out.println(point.X);
            listaPunktow.add(point);
        }

        for(int i=0; i<numberOfCentroid; i++){
            centroidy.add(new Point(generator.nextInt(950), generator.nextInt(950), generateColour(), true));
        }
    }

    public static void main(String[] args) {
        //scanFromFile();

        generateRandom(10000,25);

        JFrame f = new JFrame("Centroidziki");


        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new Main());
        f.setSize(1000, 1000);
        f.setVisible(true);
        while(warunek) {
            f.repaint();
        }
    }


    public void paint(Graphics g){
        //System.out.println(centroidy.size());
        try {
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String hexColour = "#000000";
        g.setColor(Color.decode(hexColour));
//        g.drawLine(10,10,40,10);


     //   Ellipse2D.Double pnt = new Ellipse2D.Double(10,10,1,1);
        Graphics2D p = (Graphics2D) g;
//        p.draw((new Line2D.Double(10.0,10.0, 40.0, 10.0)));
//        p.draw(new Line2D.Double(20,20,40,10));
//        p.setColor(Color.decode("#FC3D21"));
//        Point punkt = new Point(9,9);
//        p.drawRect((int)punkt.X,(int)punkt.Y,2,2);






        for(Point point : listaPunktow){
            p.setColor(Color.decode(point.color));
            p.drawRect((int)point.X,(int)point.Y,2,2);

        }


        ArrayList<ArrayList<Point>> aList = new ArrayList<ArrayList<Point> >();
        for(Point point : centroidy){
            p.setColor(Color.decode(point.color));
            p.drawRect((int)point.X,(int)point.Y,2,2);
            aList.add(new ArrayList<>());
        }



        for(Point point : listaPunktow){
            double maxd = 999999999;
            int j=0;
            for(int i=0; i<centroidy.size(); i++){
                double d = sqrt((point.X-centroidy.get(i).X)*(point.X-centroidy.get(i).X)+(point.Y-centroidy.get(i).Y)*(point.Y-centroidy.get(i).Y));
                //System.out.println(maxd);
                if(maxd >d){
                    maxd=d;
                    j=i;
                }

            }
            aList.get(j).add(point);

            point.color = centroidy.get(j).color;
            p.setColor(Color.decode(centroidy.get(j).color));
            p.drawLine((int)point.X,(int)point.Y,(int)centroidy.get(j).X,(int)centroidy.get(j).Y);

        }

        boolean zmiana = true;
        for(int i=0; i<aList.size(); i++){
            int x=0;
            int y=0;
            for(int j=0; j<aList.get(i).size(); j++){
                x+=aList.get(i).get(j).X;
                y+=aList.get(i).get(j).Y;
            }
            if(x!=0) {



                x /= aList.get(i).size();
                y /= aList.get(i).size();
                if(centroidy.get(i).X != x || centroidy.get(i).Y != y){
                    zmiana = false;
                }

                centroidy.get(i).X = x;
                centroidy.get(i).Y = y;
            }

        }
        if(zmiana){
            warunek=false;
        }



    }
    public void paintComponent(Graphics g){

        Graphics2D p = (Graphics2D) g;
        p.drawLine(8,9,60,80);


    }
}
