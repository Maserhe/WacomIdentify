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
    JFrame mFrame=new JFrame("»­°å");
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

                double[] xx = new double[]{0.52 ,3.1,8.0,17.95,28.65,39.62,50.65,78,104.6,156.6,
                        208.6,260.7,312.5,364.4,416.3,468,494,507,520};
                double[] yy = new double[]{5.288,9.4,13.84,20.20,24.90,28.44,31.10,35,
                        36.9,36.6,34.6,31.0,26.34,20.9,14.8,7.8,3.7,1.5,0.2};
                double lastx = 0.52;
                double lasty = 5.288;
                Graphics g =mFrame.getGraphics();
                for(double i= 1 ; i < 520 ; i += 1 ){
                    double t = Spline.Spline(xx,yy,0,0, i) * 10;
                    g.drawLine((int)lastx,(int)lasty, (int)i,(int)t);
                    System.out.println(t + "  " + i);
                    lastx = i;
                    lasty = t;
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