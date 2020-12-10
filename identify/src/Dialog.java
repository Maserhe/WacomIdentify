import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Maserhe
 * @Date 2020-12-04  22:38
 */

public class Dialog extends JFrame implements Runnable {
    //DrawPanel drawPanel = new DrawPanel();
    DrawTest drawPanel = new DrawTest();
    public Dialog() throws HeadlessException {

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());      // ����ȫ��Ļ��
        this.getContentPane().add(drawPanel);                           // ��ӻ��塣
        drawPanel.setBounds(0, 0, getWidth(), getHeight());
        drawPanel.setLayout(null);
        drawPanel.setOpaque(false);
        this.setResizable(false);
        setVisible(true);

    }

    @Override
    public void run() {
        while (true) {
            drawPanel.paintComponent();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
