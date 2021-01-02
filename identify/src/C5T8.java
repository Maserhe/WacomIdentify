import java.awt.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
class HuiTu{
    JFrame mFrame=new JFrame("画板");
    JPanel mPanel=new JPanel();
    int LastX=0;
    int LastY=0;
    Vector<Vector<Integer>> pointInfo;
    Vector<Integer> tempPoint;
    public HuiTu() {
        // TODO Auto-generated constructor stub
        mFrame.setSize(800, 800);
        mFrame.setVisible(true);
        mFrame.setForeground(Color.BLUE);
        mFrame.add(mPanel);
        mPanel.setBackground(Color.WHITE);
        pointInfo = new Vector<Vector<Integer>>();
        tempPoint = new Vector<Integer>();

        mFrame.addMouseListener(new MouseAdapter() {
            public void mousePress(MouseEvent e) {
                tempPoint = new Vector<Integer>();

                LastX = e.getX();
                LastY = e.getY();
            }
        });
        mFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                pointInfo.add(tempPoint);
                tempPoint = null;
            }
        });
        mFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                // 对19个散列点进行拟合曲线
                double[] xx = new double[]{0.52 ,3.1,8.0,17.95,28.65,39.62,50.65,78,104.6,156.6,
                        208.6,260.7,312.5,364.4,416.3,468,494,507,520};
                double[] yy = new double[]{5.288,9.4,13.84,20.20,24.90,28.44,31.10,35,
                        36.9,36.6,34.6,31.0,26.34,20.9,14.8,7.8,3.7,1.5,0.2};

                Graphics g = mFrame.getGraphics();

                double[][] t = Spline.spline(xx,yy,0,0, 0.5);

                System.out.println("拟合的总点数"+ t.length);
                double lastx = t[0][0] ;
                double lasty = t[0][1] * 10;

                for (int i = 0; i < t.length; i ++ ) {

                    g.drawLine((int)lastx,(int)lasty, (int)t[i][0],(int)(t[i][1] * 10));
                    lastx = t[i][0] ;
                    lasty = t[i][1] * 10;

                }

            }
        });
        mFrame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x=e.getX();
                int y=e.getY();
                Graphics g =mFrame.getGraphics();
                //g.drawLine(LastX, LastY, x, y);
                //g.drawOval(LastX,LastY,5,5);
                //g.fillOval(LastX,LastY,10,10);

                LastX=e.getX();
                LastY=e.getY();

            }

        });

        mFrame.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                String string=String.valueOf(e.getKeyChar());
                mFrame.getGraphics().drawString(string, LastX, LastY);
                LastX += 30;
            }
        });
    }
}

public class C5T8 {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new HuiTu();
    }
}