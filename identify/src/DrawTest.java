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
    Vector<Vector<PointInfo>> pointInfo;    // �洢���е����Ϣ��
    Vector<PointInfo> tempPoint;            // �洢��ǰ����һ������Ϣ��
     */
    Graphics og;
    Image image;                            // ͼ�񻺳塣

    String csvName;                            // ��д�ļ�������
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
        String[]  headers = {"x", "y", "pressure", "azimuth", "altitude", "time", "Serial number"};

        // д��ͷ�����ݣ���Ϊcsv�ļ�������û����ô��ȷ�����Զ�ʹ��ͬһ������д�ɹ�����
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
        // �ر�csvWriter
        csvWriter.close();
    }
    // ����������ֵ��


}
