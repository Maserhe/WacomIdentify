import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * @author Maserhe
 * @Date 2020-11-29  23:48
 *
 * 实现 画板。
 */

public class DrawPanel extends JPanel {
    boolean mousePressed;       // 鼠标是否落下。控制线程。
    String fileName;            //  文件名。
    Graphics og;
    Image image;                // 图像缓冲。
    // 存储坐标信息。
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

        this.setFocusable(true);		                    //注意：如果想在JPanel上使得addKeyListener()生效，必须添加本句。

    }

    public void display() {

        if (og == null) {
            // 创建一个和JPanel 一样的 图形缓冲。
            image = this.createImage(this.getWidth(),this.getHeight());
            if (image != null) og = image.getGraphics();
        }
        if (og != null) {
            // 调用父类的绘画，会刷新屏幕。
            super.paint(og);
            //PenData.refresh();
            // 画图
            //og.drawOval(PenData.x, PenData.y, 10,10);
            //System.out.println("-----> " + PenData.x + " < --------- " + PenData.y + " < -----");
            //og.fillOval(199,144,55,55);
            Graphics2D g2 = (Graphics2D)og;  //g是Graphics对象
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
        this.repaint(); //重新绘制JPanel
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);     // 进行2D绘图
    }

}
