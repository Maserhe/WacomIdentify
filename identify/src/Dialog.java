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

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());      // 覆盖全屏幕。
        this.getContentPane().add(drawPanel);                           // 添加画板。
        drawPanel.setBounds(0, 0, getWidth(), getHeight());
        drawPanel.setLayout(null);
        drawPanel.setOpaque(false);
        this.setResizable(false);
        setVisible(true);

    }

    @Override
    public void run() {
        while (true) {

            // ReadCSV.path = ReadCSV.path = System.getProperty("user.dir") + "/" + "Order1.csv";
            // ReadCSV.status = 0;
            // StartUI.info0 = ReadCSV.getInfo0(1);

            // 判断 读取文件的路径 和 读取 文件的类型。



            ReadCSV.path = ReadCSV.path = System.getProperty("user.dir") + "/" + "123465.csv";
            ReadCSV.status = 1;
            StartUI.info1 = ReadCSV.getInfo1();

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
