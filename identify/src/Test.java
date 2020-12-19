/**
 * @author Maserhe
 * @Date 2020-12-18  23:16
 */

public class Test {
    public static void main(String[] args) {

        double[][] a = info(8);
        for (int i = 0; i < a.length; i ++ ) {
            System.out.println(a[i][0] + "   " + a[i][0]);
        }

        double[][]  b= info(8);
        for (int i = 0; i < b.length; i ++ ) {
            System.out.println(b[i][0] + "   " + b[i][0]);
        }

    }

    public static double[][] info(int a){

        double[][] arr = new double[a][2];
        return arr;
    }
}
