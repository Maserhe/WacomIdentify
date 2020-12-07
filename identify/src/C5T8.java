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

        mFrame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x=e.getX();
                int y=e.getY();
                Graphics g =mFrame.getGraphics();
                //g.drawLine(LastX, LastY, x, y);
                //g.drawOval(LastX,LastY,5,5);
                g.fillOval(LastX,LastY,10,10);
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