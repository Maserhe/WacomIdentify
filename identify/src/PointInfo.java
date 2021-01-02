/**
 * @author Maserhe
 * @Date 2020-12-05  21:08
 */

public class PointInfo {
    private int x;
    private int y;
    private String time;
    private int pressure;
    private int azimuth;
    private int altitude;
    //private int tangentPressure;

    public PointInfo(int x, int y, String time, int pressure, int azimuth, int altitude) {
        this.x = x;
        this.y = y;
        this.time = time;
        this.pressure = pressure;
        this.azimuth = azimuth;
        this.altitude = altitude;
        //this.tangentPressure = tangentPressure;
    }

    public PointInfo() {
        x = 0;
        y = 0;
        time = "";
    }

    // set && get
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setAzimuth(int azimuth) {
        this.azimuth = azimuth;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getTime() {
        return time;
    }

    public int getPressure() {
        return pressure;
    }

    public int getAzimuth() {
        return azimuth;
    }

    public int getAltitude() {
        return altitude;
    }

}
