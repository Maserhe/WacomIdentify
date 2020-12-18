/**
 * @author Maserhe
 * @Date 2020-12-18  23:16
 */

public class Test {
    public static void main(String[] args) {
        int a = 10;
        int[][] arr = new int[a][];
        for (int i = 0; i < a; i ++ ) arr[i] = new int[a];
        for (int i = 0; i < a; i ++ ){
            for (int j = 0; j < a; j ++ ){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

    }
}
