import javax.swing.*;
import java.awt.*;


/**
 * @author Maserhe
 * @Date 2020-12-04  22:38
 */

public class Dialog extends JFrame implements Runnable {
    //DrawPanel drawPanel = new DrawPanel();
    DrawTest drawPanel = new DrawTest();
    public Dialog() throws HeadlessException {

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());      // ¸²¸ÇÈ«ÆÁÄ»¡£
        this.getContentPane().add(drawPanel);                           // Ìí¼Ó»­°å¡£
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
            PenData.refresh();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
