import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Maserhe
 * @Date 2020-11-29  23:44
 * 主界面
 */

public class StartUI extends JFrame {
    public static void main(String[] args) {
        StartUI startUI = new StartUI();
    }

    // 用户名 实验者名字。
    public static String userName = "Maserhe";

    // 笔迹信息  0 真实笔迹 1，简单伪造 2，熟练伪造
    public static int handwritingStatus = 0;

    // 笔迹内容信息 0, 规定内容， 1， 非规定内容， 2，签名
    public static  int contentInfoStatus = 0;

    // 任务信息 0，单一任务， 1，复杂任务 2，自由书写。
    public static int taskInfoStatus = 0;

    // 实验状态信息 0， 首次实验， 1，非首次实验。
    public static int frequencyInfo = 0;

    public static ArrayList<String> info0;

    public static ArrayList<ArrayList<WritePoint>> info1;

    private JPanel panel0 = new JPanel();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JPanel panel5 = new JPanel();

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

    private ButtonGroup group4 = new ButtonGroup();
    private JRadioButton radiobutton10 = new JRadioButton("首次实验",true);
    private JRadioButton radiobutton11 = new JRadioButton("非首次实验",false);


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
        panel5.setBounds(10,250, 343,45);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jlabel1.setText("ID:");
        jlabel1.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        jlabel1.setBounds(20, 40, 135, 25);
        jtext1.setBounds(20, 45, 108, 24);
        jtext1.setFont(new Font("     ", Font.PLAIN, 11));

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


        radiobutton10.setBounds(60, 35, 75, 37);
        radiobutton11.setBounds(190, 35, 75, 37);
        group4.add(radiobutton10);
        group4.add(radiobutton11);



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

        panel5.add(radiobutton10);
        panel5.add(radiobutton11);

        container.add(panel0);
        container.add(panel2);
        container.add(panel3);
        container.add(panel4);
        container.add(panel1);
        container.add(panel5);

        setVisible(true);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawTest.number = 1;
                // 根据信息读取获取的信息。

                // 默认读取文件。
                //ReadCSV.path = ReadCSV.path = System.getProperty("user.dir") + "/" + "123465.csv";
                //ReadCSV.status = 1;
                //ReadCSV.number = 0;
                //info1 = ReadCSV.getInfo1();
                ReadCSV.number = 0;
                new Thread(new Dialog()).start();
            }
        });

        // 第一组监听事件
        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                if ("真实笔迹".equals(actionCommand)) {
                    handwritingStatus = 0;
                } else if ("简单伪造".equals(actionCommand)) {
                    handwritingStatus = 1;
                } else if ("熟练伪造".equals(actionCommand)) {
                    handwritingStatus = 2;
                }
                System.out.println("书写状态" + handwritingStatus);
            }
        };

        // 开启监听 第一组监听
        radiobutton1.addActionListener(listener1);
        radiobutton2.addActionListener(listener1);
        radiobutton3.addActionListener(listener1);

        // 第二组监听事件
        ActionListener listener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                if ("规定".equals(actionCommand)) {
                    contentInfoStatus = 0;
                } else if ("非规定".equals(actionCommand)) {
                    contentInfoStatus = 1;
                } else if ("签名".equals(actionCommand)) {
                    contentInfoStatus = 2;
                }
                System.out.println("规定状态" + contentInfoStatus);
            }
        };

        // 开启监听 第二组监听
        radiobutton4.addActionListener(listener2);
        radiobutton5.addActionListener(listener2);
        radiobutton6.addActionListener(listener2);

        // 第三组监听事件
        ActionListener listener3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                if ("单一任务".equals(actionCommand)) {
                    taskInfoStatus = 0;
                } else if ("复杂任务".equals(actionCommand)) {
                    taskInfoStatus = 1;
                } else if ("自由书写".equals(actionCommand)) {
                    taskInfoStatus = 2;
                }
                System.out.println("任务状态" + taskInfoStatus);
            }
        };

        // 开启监听 第三组监听
        radiobutton7.addActionListener(listener3);
        radiobutton8.addActionListener(listener3);
        radiobutton9.addActionListener(listener3);

        // 第四组监听事件
        ActionListener listener4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                if ("首次实验".equals(actionCommand)) {
                    frequencyInfo = 0;
                } else if ("非首次实验".equals(actionCommand)) {
                    frequencyInfo = 1;
                }
                System.out.println("实验状态" + frequencyInfo);
            }
        };

        radiobutton10.addActionListener(listener4);
        radiobutton11.addActionListener(listener4);

        // 默认实验者名
        jtext1.setText(userName);
        // 实验者姓名的监听。
        jtext1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                userName = jtext1.getText().trim();
                System.out.println("插入信息" + userName);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                userName = jtext1.getText().trim();
                System.out.println("删除信息" + userName);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }


        });
    }
}
