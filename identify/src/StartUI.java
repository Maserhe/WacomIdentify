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

    private JPanel panel0 = new JPanel();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();

    private JButton startButton = new JButton("开始");
    private JButton stopButton = new JButton("结束");

    // 设置单选按钮
    private ButtonGroup group1 = new ButtonGroup();
    private JRadioButton radiobutton1 = new JRadioButton("真实笔迹",true);	//声明单旋钮
    private JRadioButton radiobutton2 = new JRadioButton("简单伪造",false);
    private JRadioButton radiobutton3 = new JRadioButton("熟练伪造",false);

    private ButtonGroup group2 = new ButtonGroup();
    private JRadioButton radiobutton4 = new JRadioButton("规定",true);	//声明单旋钮，默认设备为Display
    private JRadioButton radiobutton5 = new JRadioButton("非规定",false);
    private JRadioButton radiobutton6 = new JRadioButton("签名",false);

    private ButtonGroup group3 = new ButtonGroup();
    private JRadioButton radiobutton7 = new JRadioButton("单一任务",true);
    private JRadioButton radiobutton8 = new JRadioButton("复杂任务",false);
    private JRadioButton radiobutton9 = new JRadioButton("自由书写",false);

    private JLabel jlabel1 = new JLabel();
    private JTextField jtext1 = new JTextField(20);


    StartUI(){
        super();
        Container container = this.getContentPane();
        container.setLayout(null);

        setBounds(480, 260, 370, 450);
        panel0.setBounds(10,10,343,45);
        panel2.setBounds(10, 70, 343, 45);
        panel3.setBounds(10, 130, 343, 45);
        panel4.setBounds(10, 190, 343, 45);
        panel1.setBounds(10, 300, 343, 45);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jlabel1.setText("ID:");
        jlabel1.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        jlabel1.setBounds(20, 40, 135, 25);
        jtext1.setBounds(20, 45, 108, 24);
        jtext1.setFont(new Font("        ", Font.PLAIN, 11));

        radiobutton1.setBounds(60, 85, 75, 27);
        radiobutton2.setBounds(190, 85, 75, 27);
        radiobutton3.setBounds(190, 85, 75, 27);

        group1.add(radiobutton1);
        group1.add(radiobutton2);
        group1.add(radiobutton3);

        radiobutton4.setBounds(60, 35, 75, 27);
        radiobutton5.setBounds(190, 35, 75, 27);
        radiobutton6.setBounds(190, 35, 75, 27);

        group2.add(radiobutton4);
        group2.add(radiobutton5);
        group2.add(radiobutton6);


        radiobutton7.setBounds(60, 35, 75, 37);
        radiobutton8.setBounds(190, 35, 75, 37);
        radiobutton9.setBounds(190, 35, 75, 37);
        group3.add(radiobutton7);
        group3.add(radiobutton8);
        group3.add(radiobutton9);

        startButton.setBounds(35, 350, 294, 27);

        // 添加控件。
        panel0.add(jlabel1);
        panel0.add(jtext1);

        panel1.add(startButton);
        panel2.add(radiobutton1);
        panel2.add(radiobutton2);
        panel2.add(radiobutton3);

        panel3.add(radiobutton4);
        panel3.add(radiobutton5);
        panel3.add(radiobutton6);

        panel4.add(radiobutton7);
        panel4.add(radiobutton8);
        panel4.add(radiobutton9);

        container.add(panel0);
        container.add(panel2);
        container.add(panel3);
        container.add(panel4);
        container.add(panel1);

        setVisible(true);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new Thread(new Dialog()).start();
            }
        });
    }
}
