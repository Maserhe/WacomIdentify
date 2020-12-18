import com.csvreader.CsvWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Maserhe
 * @Date 2020-12-05  21:12
 */

public class DrawTest extends JPanel {

    CopyOnWriteArrayList<CopyOnWriteArrayList<PointInfo>> pointInfo;
    CopyOnWriteArrayList<PointInfo> tempPoint;
    /*
    Vector<Vector<PointInfo>> pointInfo;    // 存储所有点的信息。
    Vector<PointInfo> tempPoint;            // 存储当前笔这一化的信息。
     */
    Graphics og;
    Image image;                            // 图像缓冲。

    String csvName;                            // 读写文件的名称
    public DrawTest() {


        // 初始化。
        pointInfo = new CopyOnWriteArrayList<CopyOnWriteArrayList<PointInfo>>();
        tempPoint = new CopyOnWriteArrayList<PointInfo>();
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
                super.mouseReleased(e);
                pointInfo.add(tempPoint);
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
                newPoint.setTime(new SimpleDateFormat("HH:mm:ss:SS").format(new Date()));
                newPoint.setX(e.getX());
                newPoint.setY(e.getY());
                // 参数z 可以由角度 和 笔的长度 乘以sin 角度得到。
                newPoint.setAltitude(PenData.altitude());
                newPoint.setPressure(PenData.pressure());
                newPoint.setAzimuth(PenData.azimuth());
                //newPoint.setTangentPressure(PenData.tangentPressure());
                tempPoint.add(newPoint);

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
            tempG.setStroke(new BasicStroke(4.0f));
            // 开始画图。
            // 先把 当前画笔里面的 先画出来。

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

    void writeCsv(){
        if (csvName == null) {
            csvName = "Maserhe.csv";
        }

        CsvWriter csvWriter = new CsvWriter(csvName, ',', Charset.forName("UTF-8"));
        // 表头和内容
        String[]  headers = {"x", "y", "pressure", "azimuth", "altitude", "time", "Serial number"};

        // 写表头和内容，因为csv文件中区分没有那么明确，所以都使用同一函数，写成功就行
        try {
            csvWriter.writeRecord(headers);

            for (int i = 0; i < pointInfo.size(); i ++ ) {
                for (int j = 0; j < pointInfo.get(i).size(); j ++ ) {
                    //String[] content = {String.valueOf(i.get(j).getX()), String.valueOf(i.get(j).getY()),String.valueOf(i.get(j).getPressure()),String.valueOf(i.get(j).getAzimuth()),String.valueOf(i.get(j).getAltitude()),String.valueOf(i.get(j).getTangentPressure()),i.get(j).getTime(), String.valueOf(i + 1)};
                    String[] content = {String.valueOf(pointInfo.get(i).get(j).getX()), String.valueOf(Toolkit.getDefaultToolkit().getScreenSize().height - pointInfo.get(i).get(j).getY()),String.valueOf(pointInfo.get(i).get(j).getPressure()),String.valueOf(pointInfo.get(i).get(j).getAzimuth()/10),String.valueOf(pointInfo.get(i).get(j).getAltitude()/10),pointInfo.get(i).get(j).getTime(), String.valueOf(i + 1)};
                    csvWriter.writeRecord(content);
                }
            }

            pointInfo = new CopyOnWriteArrayList<CopyOnWriteArrayList<PointInfo>>();
            tempPoint = new CopyOnWriteArrayList<PointInfo>();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭csvWriter
        csvWriter.close();
    }
    // 三次样条插值。


}
