import java.util.ArrayList;

/**
 * @author Maserhe
 * @Date 2020-12-18  22:00
 */

public class Spline {
    public static void main(String[] args) {

    }

    public static ArrayList<Pair> Spline(double[] x, double[] y, double y01, double yn1) {

        // 1,先求 h[N]
        // 2,再求 λ[N] , μ[n]
        // 3,再求 c[N]
        int n = x.length;






        return new ArrayList<Pair>();
    }

    public static ArrayList<Double> Chase() {


        return new ArrayList<Double>();
    }
}
class Pair{
    private Double x;
    private Double y;

    public Pair(Double x, Double y) {
        this.x = x;
        this.y = y;
    }
    public void setX(Double x) {
        this.x = x;
    }
    public void setY(Double y) {
        this.y = y;
    }
    public Double getX() {
        return x;
    }
    public Double getY() {
        return y;
    }
}