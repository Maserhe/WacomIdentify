import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Maserhe
 * @Date 2020-11-29  23:44
 *
 * 主界面
 */

public class StartUI extends JFrame {
    public static void main(String[] args) {
        StartUI startUI = new StartUI();
    }
    private JPanel panel1 = new JPanel();

    private JButton startButton = new JButton("开始");
    private JButton stopButton = new JButton("结束");

    private JTextField jTextField = new JTextField(100);

    StartUI(){
        super();
        Container container = this.getContentPane();
        container.setLayout(null);
        setBounds(480, 260, 370, 476);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel1.setBounds(10, 10, 343, 95);
        container.add(panel1);
        startButton.setBounds(35, 350, 294, 27);
        panel1.add(startButton);

        setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new Thread(new Dialog()).start();
            }
        });
    }
}
