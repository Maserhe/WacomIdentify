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
 * ������
 */

public class StartUI extends JFrame {
    public static void main(String[] args) {
        StartUI startUI = new StartUI();
    }

    // �û��� ʵ�������֡�
    public static String userName = "Maserhe";

    // �ʼ���Ϣ  0 ��ʵ�ʼ� 1����α�� 2������α��
    public static int handwritingStatus = 0;

    // �ʼ�������Ϣ 0, �涨���ݣ� 1�� �ǹ涨���ݣ� 2��ǩ��
    public static  int contentInfoStatus = 0;

    // ������Ϣ 0����һ���� 1���������� 2��������д��
    public static int taskInfoStatus = 0;

    // ʵ��״̬��Ϣ 0�� �״�ʵ�飬 1�����״�ʵ�顣
    public static int frequencyInfo = 0;

    public static ArrayList<String> info0;

    public static ArrayList<ArrayList<WritePoint>> info1;

    private JPanel panel0 = new JPanel();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JPanel panel5 = new JPanel();

    private JButton startButton = new JButton("��ʼ");
    private JButton stopButton = new JButton("����");

    // ���õ�ѡ��ť
    private ButtonGroup group1 = new ButtonGroup();
    private JRadioButton radiobutton1 = new JRadioButton("��ʵ�ʼ�",true);	//��������ť
    private JRadioButton radiobutton2 = new JRadioButton("��α��",false);
    private JRadioButton radiobutton3 = new JRadioButton("����α��",false);

    private ButtonGroup group2 = new ButtonGroup();
    private JRadioButton radiobutton4 = new JRadioButton("�涨",true);	//��������ť��Ĭ���豸ΪDisplay
    private JRadioButton radiobutton5 = new JRadioButton("�ǹ涨",false);
    private JRadioButton radiobutton6 = new JRadioButton("ǩ��",false);

    private ButtonGroup group3 = new ButtonGroup();
    private JRadioButton radiobutton7 = new JRadioButton("��һ����",true);
    private JRadioButton radiobutton8 = new JRadioButton("��������",false);
    private JRadioButton radiobutton9 = new JRadioButton("������д",false);

    private ButtonGroup group4 = new ButtonGroup();
    private JRadioButton radiobutton10 = new JRadioButton("�״�ʵ��",true);
    private JRadioButton radiobutton11 = new JRadioButton("���״�ʵ��",false);


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

        // ��ӿؼ���
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
                // ������Ϣ��ȡ��ȡ����Ϣ��

                // Ĭ�϶�ȡ�ļ���
                //ReadCSV.path = ReadCSV.path = System.getProperty("user.dir") + "/" + "123465.csv";
                //ReadCSV.status = 1;
                //ReadCSV.number = 0;
                //info1 = ReadCSV.getInfo1();
                ReadCSV.number = 0;
                new Thread(new Dialog()).start();
            }
        });

        // ��һ������¼�
        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                if ("��ʵ�ʼ�".equals(actionCommand)) {
                    handwritingStatus = 0;
                } else if ("��α��".equals(actionCommand)) {
                    handwritingStatus = 1;
                } else if ("����α��".equals(actionCommand)) {
                    handwritingStatus = 2;
                }
                System.out.println("��д״̬" + handwritingStatus);
            }
        };

        // �������� ��һ�����
        radiobutton1.addActionListener(listener1);
        radiobutton2.addActionListener(listener1);
        radiobutton3.addActionListener(listener1);

        // �ڶ�������¼�
        ActionListener listener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                if ("�涨".equals(actionCommand)) {
                    contentInfoStatus = 0;
                } else if ("�ǹ涨".equals(actionCommand)) {
                    contentInfoStatus = 1;
                } else if ("ǩ��".equals(actionCommand)) {
                    contentInfoStatus = 2;
                }
                System.out.println("�涨״̬" + contentInfoStatus);
            }
        };

        // �������� �ڶ������
        radiobutton4.addActionListener(listener2);
        radiobutton5.addActionListener(listener2);
        radiobutton6.addActionListener(listener2);

        // ����������¼�
        ActionListener listener3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                if ("��һ����".equals(actionCommand)) {
                    taskInfoStatus = 0;
                } else if ("��������".equals(actionCommand)) {
                    taskInfoStatus = 1;
                } else if ("������д".equals(actionCommand)) {
                    taskInfoStatus = 2;
                }
                System.out.println("����״̬" + taskInfoStatus);
            }
        };

        // �������� ���������
        radiobutton7.addActionListener(listener3);
        radiobutton8.addActionListener(listener3);
        radiobutton9.addActionListener(listener3);

        // ����������¼�
        ActionListener listener4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                if ("�״�ʵ��".equals(actionCommand)) {
                    frequencyInfo = 0;
                } else if ("���״�ʵ��".equals(actionCommand)) {
                    frequencyInfo = 1;
                }
                System.out.println("ʵ��״̬" + frequencyInfo);
            }
        };

        radiobutton10.addActionListener(listener4);
        radiobutton11.addActionListener(listener4);

        // Ĭ��ʵ������
        jtext1.setText(userName);
        // ʵ���������ļ�����
        jtext1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                userName = jtext1.getText().trim();
                System.out.println("������Ϣ" + userName);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                userName = jtext1.getText().trim();
                System.out.println("ɾ����Ϣ" + userName);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }


        });
    }
}
