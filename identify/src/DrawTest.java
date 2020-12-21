import com.csvreader.CsvWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.StrictMath.sqrt;

/**
 * @author Maserhe
 * @Date 2020-12-05  21:12
 */

public class DrawTest extends JPanel {

    CopyOnWriteArrayList<CopyOnWriteArrayList<PointInfo>> pointInfo;
    CopyOnWriteArrayList<PointInfo> tempPoint;
    /*
    Vector<Vector<PointInfo>> pointInfo;    // �洢���е����Ϣ��
    Vector<PointInfo> tempPoint;            // �洢��ǰ����һ������Ϣ��
     */
    Graphics og;
    Image image;                            // ͼ�񻺳塣

    String csvName;                            // ��д�ļ�������

    PointInfo last;
    public DrawTest() {


        // ��ʼ����
        pointInfo = new CopyOnWriteArrayList<CopyOnWriteArrayList<PointInfo>>();
        tempPoint = new CopyOnWriteArrayList<PointInfo>();
        /*
        pointInfo = new Vector<Vector<PointInfo>>();
        tempPoint = new Vector<PointInfo>();

         */
        // ���ô�С���Լ�һЩ������
        // ���ü��̼�����

        // ������������
        /*
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ��� ����ѹ��׼����¼��ǰ��һ���� ��ʼʱ�䡣
                tempPoint = new CopyOnWriteArrayList<PointInfo>();
            }
        });

         */
        /*
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ��ǰ��һ�����Ѿ����ꡣ
                pointInfo.add(tempPoint);
            }
        });
         */



        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                pointInfo.add(tempPoint);
                tempPoint = new CopyOnWriteArrayList<PointInfo>();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // ��¼�����Ϣ��
                // ˢ�±ʡ�
                //PenData.refresh();
                PointInfo newPoint = new PointInfo();
                //���õ�ǰ�����Ϣ�� �Լ��ڶ�Ĳ�����
                newPoint.setTime(new SimpleDateFormat("HH:mm:ss:SS").format(new Date()));
                newPoint.setX(e.getX());
                newPoint.setY(e.getY());
                // ����z �����ɽǶ� �� �ʵĳ��� ����sin �Ƕȵõ���
                newPoint.setAltitude(PenData.altitude());
                newPoint.setPressure(PenData.pressure());
                newPoint.setAzimuth(PenData.azimuth());
                //newPoint.setTangentPressure(PenData.tangentPressure());
                if (last == null) {
                    last = newPoint;
                    tempPoint.add(newPoint);
                }
                else if (!last.getTime().equals(newPoint.getTime())){
                    tempPoint.add(newPoint);
                }

            }

        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                writeCsv();
                og = null;
            }
        });

        this.setFocusable(true);
    }
    // ׼����ͼ��
    public void paintComponent(){
        if (og == null) {
            // ����һ���� JPanel һ���� ͼ�λ��塣
            image = this.createImage(this.getWidth(),this.getHeight());
            if (image != null) og = image.getGraphics();
        }

        if (og != null) {
            super.paint(og);    //���ø����  paint ��ˢ����Ļ��
            // ���ʼӴ�,����ǿ��ת�͡�
            Graphics2D tempG = (Graphics2D)og;
            tempG.setStroke(new BasicStroke(4.0f));
            // ��ʼ��ͼ��
            // �Ȱ� ��ǰ��������� �Ȼ�������

            for (int i = 1; i < tempPoint.size(); i ++ ) {
                if (i < tempPoint.size())tempG.drawLine(tempPoint.get(i - 1).getX(), tempPoint.get(i - 1).getY(),tempPoint.get(i).getX(),tempPoint.get(i).getY());
            }

            // Ȼ���ÿһ���ʻ������������
            for (CopyOnWriteArrayList<PointInfo> i: pointInfo) {
                for (int j = 1; j < i.size(); j ++ ) {
                    tempG.drawLine(i.get(j - 1).getX(), i.get(j - 1).getY(), i.get(j).getX(), i.get(j).getY());
                    //System.out.println(i.get(j - 1).getX() + "<--->" + i.get(j - 1).getY() + "<--->" + i.get(j - 1).getAltitude() + "<--->" + i.get(j - 1).getAzimuth() + "<--->" + i.get(j - 1).getTime());
                }
            }
        }
        // ���»��ơ�
        this.repaint();
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);     // ����2D��ͼ
    }

    void writeCsv(){
        if (csvName == null) {
            csvName = "Maserhe.csv";
        }

        CsvWriter csvWriter = new CsvWriter(csvName, ',', Charset.forName("UTF-8"));
        // ��ͷ������
        String[]  headers = {"x", "y", "pressure", "azimuth", "altitude", "time", "Serial number" , "Speed_x", "Speed_Ax", "Speed_y", "Speed_Ay", "Speed_abs"};


        // д��ͷ�����ݣ���Ϊcsv�ļ�������û����ô��ȷ�����Զ�ʹ��ͬһ������д�ɹ�����
        try {
            csvWriter.writeRecord(headers);
            //getSpeed();
            double[][] ss = doInfo();
            int index = 0;
            for (int i = 0; i < pointInfo.size(); i ++ ) {
                for (int j = 0; j < pointInfo.get(i).size(); j ++ ) {
                    //String[] content = {String.valueOf(i.get(j).getX()), String.valueOf(i.get(j).getY()),String.valueOf(i.get(j).getPressure()),String.valueOf(i.get(j).getAzimuth()),String.valueOf(i.get(j).getAltitude()),String.valueOf(i.get(j).getTangentPressure()),i.get(j).getTime(), String.valueOf(i + 1)};
                    String[] content = {String.valueOf(pointInfo.get(i).get(j).getX()), String.valueOf(Toolkit.getDefaultToolkit().getScreenSize().height - pointInfo.get(i).get(j).getY()),String.valueOf(pointInfo.get(i).get(j).getPressure()),String.valueOf(pointInfo.get(i).get(j).getAzimuth()/10),String.valueOf(pointInfo.get(i).get(j).getAltitude()/10),pointInfo.get(i).get(j).getTime(), String.valueOf(i + 1), String.valueOf(ss[index][0]), String.valueOf(ss[index][1]),String.valueOf(ss[index][2]), String.valueOf(ss[index][3]),String.valueOf(sqrt(ss[index][0]*ss[index][0] + ss[index][2]*ss[index][2]))};
                    csvWriter.writeRecord(content);
                    index ++ ;
                }
            }
            pointInfo = new CopyOnWriteArrayList<CopyOnWriteArrayList<PointInfo>>();
            tempPoint = new CopyOnWriteArrayList<PointInfo>();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // �ر�csvWriter
        csvWriter.close();
    }

    double[][] getSpeed() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
        int count = 0;
        for (int i = 0; i < pointInfo.size(); i ++ ) count += pointInfo.get(i).size();

        double[][] ans = new double[count][4];

        count = 0;
        for (int i = 0; i < pointInfo.size();i ++ ){


            if (pointInfo.get(i).size() <= 2) {
                System.out.println(pointInfo.get(i).size());
                count += pointInfo.get(i).size();
                continue;
            }


            double[] x = new double[pointInfo.get(i).size()];
            double[] y = new double[pointInfo.get(i).size()];
            double[] t = new double[pointInfo.get(i).size()];

            double startX = pointInfo.get(0).get(0).getX();
            double startY = pointInfo.get(0).get(0).getY();
            double startT = sdf.parse(pointInfo.get(0).get(0).getTime()).getTime();


            for (int j = 0; j < pointInfo.get(i).size(); j ++ ){

                x[j] = (double)pointInfo.get(i).get(j).getX() - startX;

                y[j] = (double)pointInfo.get(i).get(j).getY() -startY;

                t[j] = (double)sdf.parse(pointInfo.get(i).get(j).getTime()).getTime() - startT;

                System.out.println(x[j] + "     " + y[i] + "    " + t[i]);

                //System.out.println(pointInfo.get(i).get(j).getX() + " " + pointInfo.get(i).get(j).getY() + " " + pointInfo.get(i).get(j).getTime());


            }

            System.out.println(" ------------ ");
            double [][] arrX = Spline.spline(t, x, 0, 0);
            double [][] arrY = Spline.spline(t, y,0,0);

            for (int j = 0; j < pointInfo.size(); j ++ ){
                ans[count][0] = arrX[j][0];
                ans[count][1] = arrX[j][1];
                ans[count][2] = arrY[j][0];
                ans[count][3] = arrY[j][1];
                count ++ ;
            }

        }
        return ans;

    }



    // ��������������ȡ�ٶȺͼ��ٶȡ�
    double[][] doInfo() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");

        int count = 0;
        for (int i = 0; i < pointInfo.size(); i ++ ) count += pointInfo.get(i).size();
        if (count == 0) return null;

        double[][] arr = new double[count][4];

        int index = 0;
        double startX = pointInfo.get(0).get(0).getX();
        double startY = pointInfo.get(0).get(0).getY();

        double startT = sdf.parse(pointInfo.get(0).get(0).getTime()).getTime();

        for (int i = 0; i < pointInfo.size(); i ++ ) {
            for (int j = 0; j < pointInfo.get(i).size(); j ++ ) {
                arr[index][0] = (double)pointInfo.get(i).get(j).getX() - startX;
                arr[index][1] = (double)sdf.parse(pointInfo.get(i).get(j).getTime()).getTime() - startT;
                arr[index][2] = (double)pointInfo.get(i).get(j).getY() -startY;
                index ++ ;

                //System.out.println(pointInfo.get(i).get(j).getX() + " " + pointInfo.get(i).get(j).getY() + " " + pointInfo.get(i).get(j).getTime());
            }
        }
        List<Double> listX = new ArrayList<Double>();
        List<Double> listY = new ArrayList<Double>();
        List<Double> listT = new ArrayList<Double>();
        double sumX = arr[0][0];
        double sumY = arr[0][2];

        double last = startT;
        int t_count = 1;
        for (int i = 1; i < count; i ++ ){
            if (arr[i][1] != last){

                listX.add( sumX / t_count);
                listY.add( sumY / t_count);
                listT.add(last);
                last = arr[i][1];
                sumX = arr[i][0];
                sumY = arr[i][2];
                t_count = 1;
            }
            else {
                t_count ++ ;
                sumX += arr[i][0];
                sumY += arr[i][2];
            }
        }

        if (sumX > 0) {
            listX.add( sumX / t_count);
            listY.add( sumY / t_count);
            listT.add(last);

        }

        double[] splineX = new double[listX.size()];
        double[] splineY = new double[listX.size()];
        double[] splineT = new double[listT.size()];

        for (int i = 0; i < listX.size(); i ++ ){
            splineX[i] = listX.get(i);
            splineT[i] = listT.get(i);
            splineY[i] = listY.get(i);
            //System.out.println(splineX[i] + " " + splineT[i] + " " + splineY[i]);
        }

        double[][] t_splinex = Spline.spline(splineT,splineX, 0 , 0);
        double[][] t_spliney = Spline.spline(splineT,splineY, 0, 0);
/*
        for (int i = 0; i < t_splinex.length; i ++ ) {
            System.out.println(t_splinex[i][0] + "   " + t_splinex[i][1] + " " + t_spliney[i][0] + " " + t_spliney[i][1]);
        }
*/
        last = arr[0][1]; // ��һ����ʼʱ�䡣
        index = 0;
        arr[0][0] = t_splinex[0][0];
        arr[0][1] = t_splinex[0][1];
        arr[0][2] = t_spliney[0][0];
        arr[0][3] = t_spliney[0][1];

        for (int i = 1; i < count; i ++ ){
            if (arr[i][1] != last){
                last = arr[i][1];
                index ++ ;
            }
            arr[i][0] = t_splinex[index][0];
            arr[i][1] = t_splinex[index][1];

            arr[i][2] = t_spliney[index][0];
            arr[i][3] = t_spliney[index][1];

            //System.out.println(arr[i][0] + " " + arr[i][1] + " " + arr[i][2] + " " + arr[i][3]);
        }
        return arr;
    }
}
