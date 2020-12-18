import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * @author Maserhe
 * @Date 2020-11-29  23:48
 *
 * ʵ�� ���塣
 */

public class DrawPanel extends JPanel {
    boolean mousePressed;       // ����Ƿ����¡������̡߳�
    String fileName;            //  �ļ�����
    Graphics og;
    Image image;                // ͼ�񻺳塣
    // �洢������Ϣ��
    ArrayList<ArrayList<Point>> pointInfo;
    ArrayList<Point> tempPoint;
    /*
    int LastX = 0;
    int LastY = 0;
    */
    public DrawPanel() {

        pointInfo = new ArrayList<ArrayList<Point>>();
        tempPoint = new ArrayList<Point>();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("---------");
                System.out.println(e.getKeyChar() + " hahahahahaahah");
            }
        });

/*
        addMouseListener(new MouseAdapter() {
            public void mousePress(MouseEvent e) {
                tempPoint.add(new Point(e.getX(), e.getY()));
                LastX = e.getX();
                LastY = e.getY();
            }
        });

*/

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                pointInfo.add(tempPoint);
                tempPoint = null;
            }
        });


        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                tempPoint.add(new Point(e.getX(), e.getY()));
                int x=e.getX();
                int y=e.getY();
                Graphics g = getGraphics();
                /*
                //g.drawLine(LastX, LastY, x, y);
                //g.drawOval(LastX,LastY,5,5);
                LastX=e.getX();
                LastY=e.getY();
                */
            }

        });

        this.setFocusable(true);		                    //ע�⣺�������JPanel��ʹ��addKeyListener()��Ч��������ӱ��䡣

    }

    public void display() {

        if (og == null) {
            // ����һ����JPanel һ���� ͼ�λ��塣
            image = this.createImage(this.getWidth(),this.getHeight());
            if (image != null) og = image.getGraphics();
        }
        if (og != null) {
            // ���ø���Ļ滭����ˢ����Ļ��
            super.paint(og);
            //PenData.refresh();
            // ��ͼ
            //og.drawOval(PenData.x, PenData.y, 10,10);
            //System.out.println("-----> " + PenData.x + " < --------- " + PenData.y + " < -----");
            //og.fillOval(199,144,55,55);
            Graphics2D g2 = (Graphics2D)og;  //g��Graphics����
            g2.setStroke(new BasicStroke(5.0f));
            for (int i = 1; i < tempPoint.size(); i ++ ) {
                g2.drawLine(tempPoint.get(i - 1).x, tempPoint.get(i - 1).y, tempPoint.get(i).x, tempPoint.get(i).y);
            }
            for (ArrayList<Point> i: pointInfo) {
                for (int j = 1; j < i.size(); j ++ ) {
                    //og.fillOval(i.get(j).x, i.get(j).y, 10, 10);
                    g2.drawLine(i.get(j - 1).x, i.get(j - 1).y, i.get(j).x, i.get(j).y);
                }
            }
        }
        this.repaint(); //���»���JPanel
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);     // ����2D��ͼ
    }

}
