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
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.StrictMath.sqrt;

/**
 * @author Maserhe
 * @Date 2020-12-05  21:12
 */

public class DrawTest extends JPanel {

    // 字的个数
    public static int number = 1;

    CopyOnWriteArrayList<CopyOnWriteArrayList<PointInfo>> pointInfo;
    CopyOnWriteArrayList<PointInfo> tempPoint;

    /*
    Vector<Vector<PointInfo>> pointInfo;    // 存储所有点的信息。
    Vector<PointInfo> tempPoint;            // 存储当前笔这一化的信息。
     */
    Graphics og;
    Image image;                            // 图像缓冲。

    String csvName;                         // 读写文件的名称

    PointInfo last;
    public DrawTest() {


        // 初始化。
        pointInfo = new CopyOnWriteArrayList<CopyOnWriteArrayList<PointInfo>>();
        tempPoint = new CopyOnWriteArrayList<PointInfo>();
        last = new PointInfo();
        /*
        pointInfo = new Vector<Vector<PointInfo>>();
        tempPoint = new Vector<PointInfo>();

         */
        // 设置大小，以及一些参数。
        // 设置键盘监听。

        // 设置鼠标监听。
        /*
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 鼠标 被按压，准备记录当前这一画的 开始时间。
                tempPoint = new CopyOnWriteArrayList<PointInfo>();
            }
        });

         */
        /*
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 当前这一画，已经画完。
                pointInfo.add(tempPoint);
            }
        });
         */



        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(tempPoint.size() > 3) pointInfo.add(tempPoint);
                tempPoint = new CopyOnWriteArrayList<PointInfo>();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // 记录点的信息。
                // 刷新笔。
                //PenData.refresh();
                PointInfo newPoint = new PointInfo();
                //设置当前点的信息， 以及众多的参数。
                newPoint.setTime(new SimpleDateFormat("yy:MM:dd:HH:mm:ss:SS").format(new Date()));
                newPoint.setX(e.getX());
                newPoint.setY(e.getY());
                // 参数z 可以由角度 和 笔的长度 乘以sin 角度得到。
                newPoint.setAltitude(PenData.altitude());
                newPoint.setPressure(PenData.pressure());
                newPoint.setAzimuth(PenData.azimuth());

                if (!last.getTime().equals(newPoint.getTime())) {
                     tempPoint.add(newPoint);
                     last = newPoint;
                }

            }

        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    writeCsv();
                    number ++ ;

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                og = null;
                ReadCSV.number ++ ;
            }
        });

        this.setFocusable(true);
    }
    // 准备绘图。
    public void paintComponent(){
        if (og == null) {
            // 创建一个和 JPanel 一样的 图形缓冲。
            image = this.createImage(this.getWidth(),this.getHeight());
            if (image != null) og = image.getGraphics();
        }


        if (og != null) {
            super.paint(og);    //调用父类的  paint 会刷新屏幕。
            // 画笔加粗,向下强制转型。
            Graphics2D tempG = (Graphics2D)og;

            // 开始画图。
            // 先把 当前画笔里面的 先画出来。
            Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width / 5,Toolkit.getDefaultToolkit().getScreenSize().height / 5);
            tempG.setColor(Color.pink);
            tempG.fill(rectangle);
            tempG.setColor(Color.black);
            tempG.setStroke(new BasicStroke(2.0f));

            if (ReadCSV.status == 0) {
                if (ReadCSV.number < StartUI.info0.size() ) {
                    tempG.setFont(new Font("微软雅黑", Font.BOLD, 80));
                    tempG.drawString(StartUI.info0.get(ReadCSV.number), Toolkit.getDefaultToolkit().getScreenSize().height / 10,Toolkit.getDefaultToolkit().getScreenSize().height / 10);
                }
            }

            if (ReadCSV.status == 1) {

                ArrayList<ArrayList<WritePoint>> info = StartUI.info1;
                for (ArrayList<WritePoint> list : info) {
                    int startX = list.get(0).getX();
                    int startY = Toolkit.getDefaultToolkit().getScreenSize().height - list.get(0).getY();

                    for (WritePoint writePoint : list) {
                        tempG.drawLine(startX / 5 , startY / 5 , writePoint.getX() / 5,Toolkit.getDefaultToolkit().getScreenSize().height / 5 - writePoint.getY() / 5);
                        startX = writePoint.getX();
                        startY = Toolkit.getDefaultToolkit().getScreenSize().height - writePoint.getY();
                    }
                }
            }

            tempG.setStroke(new BasicStroke(4.0f));
            for (int i = 1; i < tempPoint.size(); i ++ ) {
                if (i < tempPoint.size())tempG.drawLine(tempPoint.get(i - 1).getX(), tempPoint.get(i - 1).getY(),tempPoint.get(i).getX(),tempPoint.get(i).getY());
            }
            // 然后把每一个笔画都给绘出来。
            for (CopyOnWriteArrayList<PointInfo> i: pointInfo) {
                for (int j = 1; j < i.size(); j ++ ) {
                    tempG.drawLine(i.get(j - 1).getX(), i.get(j - 1).getY(), i.get(j).getX(), i.get(j).getY());
                    //System.out.println(i.get(j - 1).getX() + "<--->" + i.get(j - 1).getY() + "<--->" + i.get(j - 1).getAltitude() + "<--->" + i.get(j - 1).getAzimuth() + "<--->" + i.get(j - 1).getTime());
                }
            }



        }
        // 重新绘制。
        this.repaint();
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);     // 进行2D绘图
    }

    void writeCsv() throws IOException {

        // 文件名
        if (StartUI.userName.equals("")) StartUI.userName = "null";
        // 创建文件路径
        String path = System.getProperty("user.dir") +"/data/"+ StartUI.userName;

        File f = new File(path);
        String[] dirs = {"规定", "非规定", "签名"};
        String[] status = {"真实笔迹", "简单伪造", "熟练伪造"};

        if (!f.exists()) {
            f.mkdirs(); // 创建文件目录
            for (int i = 0; i < dirs.length; i ++ ) {
                f = new File(path + "/" + dirs[i]);
                f.mkdir();
            }
        }

        File csvFile = new File(path + "/" + dirs[StartUI.contentInfoStatus] , StartUI.userName + "-"+ status[StartUI.handwritingStatus] + "-" + number +".csv");
        if (!csvFile.exists()) {
            csvFile.createNewFile();
        }

        CsvWriter csvWriter = new CsvWriter(path + "/" + dirs[StartUI.contentInfoStatus] + "/" + StartUI.userName + "-" + status[StartUI.handwritingStatus] + "-" + number + ".csv", ',', Charset.forName("UTF-8"));
        // 表头和内容
        String[]  headers = {"milliseconds", "x", "y", "pressure", "azimuth", "altitude", "time", "Speed_x", "Speed_Ax", "Speed_y", "Speed_Ay", "Speed_abs", "Speed_A_abs", "Strokes_Number", "PerStrokes_Time"};

        // 写表头和内容，因为csv文件中区分没有那么明确，所以都使用同一函数，写成功就行
        try {
            csvWriter.writeRecord(headers); // 可以传入第二个参数， 将写入csv中的文件进行追加。
            SimpleDateFormat sdf = new SimpleDateFormat("yy:MM:dd:HH:mm:ss:SS");
            //getSpeed();
            //double[][] ss = doInfo();
            double[][] ss = getInformation();
            int index = 0;

            for (int i = 0; i < pointInfo.size(); i ++ ) {
                long startT = sdf.parse(pointInfo.get(0).get(0).getTime()).getTime();
                long startPerPenDraw = sdf.parse(pointInfo.get(i).get(0).getTime()).getTime();
                for (int j = 0; j < pointInfo.get(i).size(); j ++ ) {
                    //String[] content = {String.valueOf(i.get(j).getX()), String.valueOf(i.get(j).getY()),String.valueOf(i.get(j).getPressure()),String.valueOf(i.get(j).getAzimuth()),String.valueOf(i.get(j).getAltitude()),String.valueOf(i.get(j).getTangentPressure()),i.get(j).getTime(), String.valueOf(i + 1)};
                    String[] content = {
                                        String.valueOf(sdf.parse(pointInfo.get(i).get(j).getTime()).getTime() - startT),
                                        String.valueOf(pointInfo.get(i).get(j).getX()),
                                        String.valueOf(Toolkit.getDefaultToolkit().getScreenSize().height - pointInfo.get(i).get(j).getY()),
                                        String.valueOf(pointInfo.get(i).get(j).getPressure()),
                                        String.valueOf(pointInfo.get(i).get(j).getAzimuth()/10),
                                        String.valueOf(pointInfo.get(i).get(j).getAltitude()/10),
                                        pointInfo.get(i).get(j).getTime(),
                                        String.valueOf(ss[index][0]),
                                        String.valueOf(ss[index][1]),
                                        String.valueOf(ss[index][2]),
                                        String.valueOf(ss[index][3]),
                                        String.valueOf(sqrt(ss[index][0]*ss[index][0] + ss[index][2]*ss[index][2])),
                                        String.valueOf(sqrt(ss[index][1]*ss[index][1] + ss[index][3]*ss[index][3])),
                                        String.valueOf(i + 1),
                                        String.valueOf(sdf.parse(pointInfo.get(i).get(j).getTime()).getTime() - startPerPenDraw)
                                        };

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
        // 关闭csvWriter
        csvWriter.close();
    }

    double[][] getSpeed() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yy:MM:dd:HH:mm:ss:SS");
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


    // 调用三次样条求取速度和加速度。
    double[][] doInfo() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yy:MM:dd:HH:mm:ss:SS");

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
        last = arr[0][1]; // 第一个起始时间。
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

    // 通过三次样条获取速度。返回一个四列的二维数组。 1，x速度 2，x加速度 3, y轴速度 4，y轴加速度。
    double[][] getInformation() throws ParseException {

        int index = 0;
        for (int i = 0; i < pointInfo.size(); i ++ ) index += pointInfo.get(i).size();
        // 创建返回的结果。
        double[][] answer = new double[index][4];
        index = 0;

        // 时间格式。
        SimpleDateFormat sdf = new SimpleDateFormat("yy:MM:dd:HH:mm:ss:SS");
        for (int i = 0; i < pointInfo.size(); i ++ ) {
            // 对于每一条线。// 一条线的点数。
            int count = pointInfo.get(i).size();
            double startX = pointInfo.get(i).get(0).getX();
            double startY = pointInfo.get(i).get(0).getY();
            double startT = sdf.parse(pointInfo.get(i).get(0).getTime()).getTime();
            // 创建可以 用于三次样条计算的 数组。

            double[] tInfo = new double[count];
            double[] xInfo = new double[count];
            double[] yInfo = new double[count];

            for (int j = 0; j < pointInfo.get(i).size(); j ++ ) {

                xInfo[j] = pointInfo.get(i).get(j).getX() - startX;
                yInfo[j] = pointInfo.get(i).get(j).getY() - startY;
                tInfo[j] = sdf.parse(pointInfo.get(i).get(j).getTime()).getTime() - startT;
            }

            // 进行三次样条计算。返回一个二位数组，第一列速度，第二列加速度。
            double[][] ansX = Spline.spline(tInfo, xInfo, 0, 0);
            double[][] ansY = Spline.spline(tInfo, yInfo, 0, 0);

            for (int j = 0; j < count; j ++ ){
                System.out.println(ansX[j][0] + " " + ansX[j][1] + " ------- " + ansY[j][0] + " " + ansY[j][1]);
            }

            // 将结果保存。
            for (int j = 0; j < count; j ++ ) {
                answer[index][0] = ansX[j][0];
                answer[index][1] = ansX[j][1];
                answer[index][2] = ansY[j][0];
                answer[index][3] = ansY[j][1];
                index ++ ;
            }
        }
        return answer;
    }

}
