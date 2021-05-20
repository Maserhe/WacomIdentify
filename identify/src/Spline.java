import static java.lang.StrictMath.pow;

/**
 * @author Maserhe
 * @Date 2020-12-18  22:00
 */

public class Spline {
    /*
    public static void main(String[] args) {

        double[] x = new double[]{0.52 ,3.1,8.0,17.95,28.65,39.62,50.65,78,104.6,156.6,
                208.6,260.7,312.5,364.4,416.3,468,494,507,520};
        double[] y = new double[]{5.288,9.4,13.84,20.20,24.90,28.44,31.10,35,
                36.9,36.6,34.6,31.0,26.34,20.9,14.8,7.8,3.7,1.5,0.2};

        //Spline.Spline(x, y,0,0);
    }
    */

    public static double[][] spline(double[] x, double[] y, double y01, double yn1) {

        // 1,���� h[N]
        // 2,���� ��[N] , ��[n]
        // 3,���� c[N]
        int n = x.length;
        double[][] ans = new double[n][2];
        double[] h = new double[n];
        for (int i = 0;  i < n - 1; i ++ ) {
            h[i] = x[i + 1] - x[i];
        }
        double[] a = new double[n];
        double[] b = new double[n];
        double[] C = new double[n];

        // 2, �ټ���  ��[n]  �� ��[n]
        for (int i = 1; i < n - 1; i++)  // �� �� 2 �� n- 1 ��
        {
            a[i] = h[i - 1] / (h[i - 1] + h[i]);
            b[i] = 1.0 - a[i];

        }
        //���� c[N]  , 6���Ķ��ײ���
        for (int i = 1; i < n - 1; i++){
            //     6   /     h0 + h1      *    [  ( y2 - y1 )/h1      -     ( y1 - y0 )/h0   ]
            C[i] = 6.0 / (h[i - 1] + h[i]) * ((y[i + 1] - y[i]) / h[i] - (y[i] - y[i - 1]) / h[i - 1]);
        }

        //���ݶ˵� ��һ�׵���  ���  C0  ��  Cn
        C[0] = 6.0 / h[0] *    (  (y[1] - y[0])  / h[0] - y01);
        C[n-1] = 6.0 / h[n - 2] * (yn1 - (y[n - 1] - y[n - 2]) / h[n - 2]);

        double[][] A = new double[n][n]; //�����

        //��һ�к����һ�� �������
        A[0][0] = 2 ;
        A[0][1] = 1;
        A[n - 1][n - 1 ] = 2 ;
        A[n - 1][n - 2] = 1;

        //��   ��[i]  , 2 ,  ��[i]  �������
        for (int i = 1; i < n -  1; i++)
        {
            A[i][i - 1] = a[i];
            A[i][i] = 2.0;
            A[i][i + 1] = b[i];
        }

        double[] M = Chase(A, C);

        //�ؽ����  �õ�M ����
        //��¼��

        //for( int i = 0 ; i <19 ;i ++ )cout<<"   "<<M[i]<<endl;
        //double answer;

        //����Xȷ��i  ,ȷ�� ��Ҫ��ֵ�� ���䷶Χ
        /*
        int i = 0;
        while (x[i] <= X) i++;
        i--;

        // S(x)��4�����  ������һ�𷵻ؾ�����
        double temp1 = pow(x[i + 1] - X, 3) / (6.0 * h[i]) * M[i];
        double temp2 = pow(X - x[i], 3) / (6.0 * h[i]) * M[i + 1];
        double temp3 = (x[i + 1] - X) / h[i] * (y[i] - h[i] * h[i] / 6.0 * M[i]);
        double temp4 = (X - x[i]) / h[i] * (y[i + 1] - h[i] * h[i] / 6.0 * M[i + 1]);
        answer = temp1 + temp2 + temp3 + temp4;

         */
        double[][] answer = new double[n][2]; // ��һ�� �ٶȣ� �ڶ��м��ٶȡ�


        //System.out.println(answer[0][1] + " ----------- " + answer[n - 1][1]);

        for (int i = 1; i < n - 1; i ++ ) {
            double tx = x[i];
            // ����һ�׵���
            double temp1 = - pow(x[i + 1] - tx, 2) / (2.0 * h[i])* M[i];
            double temp2 = pow(x[i] - tx, 2) / (2.0 * h[i])* M[i + 1];
            double temp3 = h[i] *(M[i] - M[i + 1]) / 6.0;
            double temp4 = (y[i + 1] - y[i - 1]) / h[i];
            //System.out.println(temp1 + " " + temp2 + " " + temp3 + " " + temp4);

            // ���һ�׵���
            answer[i][0] = temp1 + temp2 + temp3 + temp4;
            // ������׵���
            temp1 = (x[i + 1] - tx) * M[i] / h[i];
            temp2 = (tx - x[i]) * M[i + 1] / h[i];

            answer[i][1] = temp1 + temp2;
        }
        // ��ʼ�ͽ�β�� λ�� = ����֮һ�� ���ٶ� ���� ʱ��� ƽ����
        answer[0][1] = 2 * y[1] / (x[1] * x[1]);
        answer[n - 1][1] =  2 * (y[n - 1] -  y[n - 2]) / pow(x[n - 1] - x[n - 2], 2);

        return answer;
    }
    // L U �ֽ�
    public static double[] Chase(double[][] A, double[] d) {
        int n = A.length;
        //�Ծ������ L U �ֽ� ��˳������ʽ��Ϊ��
        double[] L = new double[n];
        double[] U = new double[n];

        U[0] = A[0][0];
        //�ӵڶ��ʼ �� �� n ��  ,���� L �� U
        for (int i = 1; i < n ; i++)
        {
            // Li = ai / ui-1
            L[i] = A[i][i - 1] / U[i - 1];
            // Ui = bi - Li* Ci-1
            U[i] = A[i][i] - L[i] * A[i -1][i];

        }
        double[] y = new double[n];
        y[0] = d[0];   // y1= d1

        for (int i = 1; i < n; i++)
        {
            y[i] = d[i] - L[i]* y[i - 1] ;  // yi = di - li *yi-1

        }
        //��¼��
        double[] ans = new double[n];

        ans[n - 1] = y[n - 1] / U[n -1]; // Xn = Yn/ Un

        for (int i = n - 2; i >= 0; i--)
        {
            // Xi = (Yn - Ci* Xi+1)/ Un
            ans[i] = (y[i] - A[i][i + 1]*ans[i + 1]) / U[i];
        }
        return ans;
    }

    // �������������õĺ�����
    public static double[][] spline(double[] x, double[] y, double y01, double yn1, double perAdd) {

        // 1,���� h[N]
        // 2,���� ��[N] , ��[n]
        // 3,���� c[N]
        int n = x.length;
        double[][] ans = new double[n][2];
        double[] h = new double[n];
        for (int i = 0;  i < n - 1; i ++ ) {
            h[i] = x[i + 1] - x[i];
        }
        double[] a = new double[n];
        double[] b = new double[n];
        double[] C = new double[n];

        // 2, �ټ���  ��[n]  �� ��[n]
        for (int i = 1; i < n - 1; i++)  // �� �� 2 �� n- 1 ��
        {
            a[i] = h[i - 1] / (h[i - 1] + h[i]);
            b[i] = 1.0 - a[i];

        }
        //���� c[N]  , 6���Ķ��ײ���
        for (int i = 1; i < n - 1; i++){
            //     6   /     h0 + h1      *    [  ( y2 - y1 )/h1      -     ( y1 - y0 )/h0   ]
            C[i] = 6.0 / (h[i - 1] + h[i]) * ((y[i + 1] - y[i]) / h[i] - (y[i] - y[i - 1]) / h[i - 1]);
        }

        //���ݶ˵� ��һ�׵���  ���  C0  ��  Cn
        C[0] = 6.0 / h[0] *    (  (y[1] - y[0])  / h[0] - y01);
        C[n-1] = 6.0 / h[n - 2] * (yn1 - (y[n - 1] - y[n - 2]) / h[n - 2]);

        double[][] A = new double[n][n]; //�����

        //��һ�к����һ�� �������
        A[0][0] = 2 ;
        A[0][1] = 1;
        A[n - 1][n - 1 ] = 2 ;
        A[n - 1][n - 2] = 1;

        //��   ��[i]  , 2 ,  ��[i]  �������
        for (int i = 1; i < n -  1; i++)
        {
            A[i][i - 1] = a[i];
            A[i][i] = 2.0;
            A[i][i + 1] = b[i];
        }

        double[] M = Chase(A, C);

        //�ؽ����  �õ�M ����
        //��¼��

        //for( int i = 0 ; i <19 ;i ++ )cout<<"   "<<M[i]<<endl;
        //double answer;

        //����Xȷ��i  ,ȷ�� ��Ҫ��ֵ�� ���䷶Χ
        /*
        int i = 0;
        while (x[i] <= X) i++;
        i--;

        // S(x)��4�����  ������һ�𷵻ؾ�����
        double temp1 = pow(x[i + 1] - X, 3) / (6.0 * h[i]) * M[i];
        double temp2 = pow(X - x[i], 3) / (6.0 * h[i]) * M[i + 1];
        double temp3 = (x[i + 1] - X) / h[i] * (y[i] - h[i] * h[i] / 6.0 * M[i]);
        double temp4 = (X - x[i]) / h[i] * (y[i + 1] - h[i] * h[i] / 6.0 * M[i + 1]);
        answer = temp1 + temp2 + temp3 + temp4;
         */
        int count = (int)((x[n - 1] - x[0]) / perAdd);
        double[][] answer = new double[count][2];

        //System.out.println(answer[0][1] + " ----------- " + answer[n - 1][1]);


        for (int j = 0; j < count; j ++ ) {

            double X = x[0] + perAdd * j;

            int i = 0;
            while (x[i] <= X) i++;
            i--;

            double temp1 = pow(x[i + 1] - X, 3) / (6.0 * h[i]) * M[i];
            double temp2 = pow(X - x[i], 3) / (6.0 * h[i]) * M[i + 1];
            double temp3 = (x[i + 1] - X) / h[i] * (y[i] - h[i] * h[i] / 6.0 * M[i]);
            double temp4 = (X - x[i]) / h[i] * (y[i + 1] - h[i] * h[i] / 6.0 * M[i + 1]);
            answer[j][0] = j*perAdd + x[0];
            answer[j][1] = temp1 + temp2 + temp3 + temp4;

        }

        return answer;
    }
}