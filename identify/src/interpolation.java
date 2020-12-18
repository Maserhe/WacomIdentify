/**
 * @author Maserhe
 * @Date 2020-12-18  22:28
 */
/**
 * @author ningustc
 *
 */
/*
 *realize three different ways of interpolation
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class interpolation extends JFrame
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Draw draw;
    MyPanel paint;
    public interpolation()
    {
        super("interpolation");
        draw = new Draw();//initial the DrawPanel to exibite the interpolation graph
        paint = new MyPanel();//initial the new panel
        paint.addMouseListener(new Press(this));//add mouseListener to the panel
        getContentPane().setLayout(new BorderLayout());//set layout
        JPanel ButtonBar = new JPanel();//the panel hold three buttons
        ButtonBar.setLayout(new GridLayout(1,3));//set the layout of the new panel

        JButton Lagrange = new JButton("Lagrange Interpolation");
        JButton Newton = new JButton("Newton Interpolation");
        JButton Thrice = new JButton("三次自然样条");
        JButton Cls = new JButton("clear");
        ButtonBar.add(Lagrange);
        ButtonBar.add(Newton);
        ButtonBar.add(Thrice);
        ButtonBar.add(Cls);
        Lagrange.addActionListener(new Button(this));
        Newton.addActionListener(new Button(this));
        Thrice.addActionListener(new Button(this));
        Cls.addActionListener(new Button(this));
        //add ActionListener to the four buttons
        setBackground(Color.white);
        getContentPane().add(paint);
        getContentPane().add(paint,BorderLayout.CENTER);
        getContentPane().add(ButtonBar,BorderLayout.SOUTH);
        paint.repaint();

        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        draw.get(paint);
        draw.run();
    }

    public static void main(String argv[])
    {
        interpolation cz = new interpolation();
    }
}
class Draw
{
    Graphics g;
    int[][] range = new int[50][2];//50 dots
    int number=0;
    int choice=0;
    public void get(MyPanel a)//捕获需要作图的面板
    {
        g=a.getGraphics();
    }
    public void Lagrange()//拉格朗日插值算法
    {
        g.setColor(Color.BLUE);
        for(double x=0;x<=800;x=x+0.01)
        {
            double s=0,p=0;
            for(int j=0;j<number;j++)
            {
                p = range[j][1];
                for(int i=0;i<number;i++)
                {
                    if(i!=j)
                        p=p*(x-range[i][0])/(range[j][0]-range[i][0]);
                }
                s=s+p;
            }
            g.drawLine((int)x, (int)s, (int)x, (int)s);
        }
    }
    public void Newton()//牛顿插值算法
    {
        g.setColor(Color.green);
        double p = 0;
        double a[] = new double[50];
        for(int j=0;j<=number-1;j ++)
        {
            a[j]=range[j][1];
        }
        for(int k=1;k<=number-1;k++)
        {
            for(int j=number-1;j>=k;j--)
            {
                a[j]=(a[j]-a[j-1])/(range[j][0]-range[j-k][0]);
            }
        }
        for(double x=0;x<=800;x=x+0.01)
        {
            p = a[number-1];
            for(int i=number-2;i>=0;i--)
            {
                p=p*(x-range[i][0])+a[i];
            }
            g.drawLine((int)x, (int)p, (int)x, (int)p);
        }
    }

    public void Thrice()//三次自然样条插值算法
    {
        double a[] = new double[50];
        double b[] = new double[50];
        double c[] = new double[50];
        double d[] = new double[50];
        double h[] = new double[50];
        double s = 0;
        double x = 0;
        int temp = 0;
        double s1[] = new double[50];
        double s2[] = new double[50];

        for(int i = 0; i < number - 1; i ++)
        {
            for(int j=number-2;j>=i;j--)
            {
                if(range[j+1][0]<range[j][0])
                {
                    temp=range[j+1][0];
                    range[j+1][0]=range[j][0];
                    range[j][0]=temp;
                    temp = range[j+1][1];
                    range[j+1][1]=range[j][1];
                    range[j][1]=temp;

                }
            }
        }

        for(int k=0;k<=number-2;k++)
        {
            h[k] = range[k+1][0]-range[k][0];
        }
        a[1]=2*(h[0]+h[1]);
        for(int k=2;k<=number-2;k++)
        {
            a[k]=2*(h[k-1]+h[k])-h[k-1]*h[k-1]/a[k-1];
        }
        for(int k=1;k<=number-1;k++)
        {
            c[k] = (range[k][1] - range[k - 1][1])/h[k-1];
        }
        for(int k=1;k<=number-2;k++)
        {
            d[k]=6*(c[k+1]-c[k]);
        }
        b[1] = d[1];
        for(int k = 2; k <= number - 2; k ++)
        {
            b[k] = d[k]-b[k-1]*h[k-1]/a[k];
        }
        s2[number-2] = b[number - 2]/a[number - 2];
        for(int k = number-3;k>=1;k--)
        {
            s2[k]=(b[k]-h[k]*s2[k+1])/a[k];
        }
        s2[0]=0;
        s2[number-1]=0;
        for(int k=0;k<=number-2;k++)
        {
            for(x=range[k][0]; x<=range[k+1][0];x=x+0.1)
            {
                s1[k]=c[k+1]-s2[k+1]*h[k]/6-s2[k]*h[k]/3;
                s = range[k][1]+s1[k]*(x-range[k][0])+s2[k]*(x-range[k][0])*(x-range[k][0])/2+
                        (s2[k+1]-s2[k])*(x-range[k][0])*(x-range[k][0])*(x-range[k][0])/6/h[k];
                g.drawLine((int)x, (int)s, (int)x, (int)s);
            }
        }
    }
    public void Cls()//清屏操作
    {
        number = 0;
        g.setColor(new Color(239,239,239));
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.BLACK);
        g.drawLine(50,275,750,275);
        g.drawLine(400,25,400,525);
        g.drawString("(0,0)",405,265);
        g.fillOval(398,273,4,4);
    }
    public void run()
    {
        try
        {
            while(true)
            {
                while(choice == 0)
                {
                    g.setColor(Color.RED);
                    for(int i = 0;i < number;i++)
                        g.fillOval(range[i][0]-2, range[i][1]-2, 4, 4);
                }
                if(choice == 1)
                    Lagrange();
                else if(choice == 2)
                    Newton();
                else if(choice == 3)
                    Thrice();
                else if(choice == 4)
                    Cls();
                choice = 0;
            }
        }
        catch(Exception e)
        {
            System.out.println("error");
        }
    }
}
//the MouseListener
class Press implements MouseListener
{
    Draw abc;
    public Press(interpolation a)
    {
        super();
        abc = a.draw;
    }
    public void mouseClicked(MouseEvent e)
    {
        // TODO Auto-generated method stub
        try
        {
            if(abc.number < 100)//捕获得到的点并且保存在draw的坐标组中
            {
                abc.range[abc.number][0] = e.getX();
                abc.range[abc.number][1] = e.getY();
                abc.number++;
            }
        }
        catch(Exception error)
        {
            System.out.println("出现异常");
        }
    }
    public void mouseMoved(MouseEvent e) {

    }
    public void mouseDragged (MouseEvent e) {

    }

    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

}
// actionListener
class Button implements ActionListener
{
    Draw abc;

    public Button(interpolation a)
    {
        abc = a.draw;
    }

    public void actionPerformed(ActionEvent e)
    {
        JButton btn = (JButton) e.getSource();

        if(btn.getText().equals("Lagrange Interpolation"))
        {
            abc.choice = 1;
        }
        else if(btn.getText().equals("Newton Interpolation"))
        {
            abc.choice = 2;
        }
        else if(btn.getText().equals("三次自然样条"))
        {
            abc.choice = 3;
        }
        else if(btn.getText().equals("clear"))
        {
            abc.choice = 4;
        }
    }
}
//MyPanel extends the JPanel with two different line to
//form the special coordinate
class MyPanel extends JPanel
{
    //
    public MyPanel()
    {
        super();
    }
    //draw the coordinate System
    public void paintComponent(Graphics g)
    {
        g.drawLine(50,275,750,275);
        g.drawLine(400,25,400,525);
        g.drawString("(0,0)",405,265);
        g.fillOval(398,273,4,4);
    }
}

