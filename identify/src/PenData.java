import cello.tablet.JTablet;
import cello.tablet.JTabletCursor;
import cello.tablet.JTabletException;
/**
 * @author Maserhe
 * @Date 2020-12-04  23:48
 *
 * 实现 画板。
 */
public class PenData {
    static JTablet jTablet;
    //static int x;
    //static int y;
    //static int z;

    static int pressure = 0;            // pressure value
    static int azimuth = 0;             // orientation azimuth   0度 到 360度之间。方位角
    static int altitude = 90;            // orientation altitude angle   90度 到 0度之间。 方位高度角
    //static int tangentPressure = 0;     // tangent pressure 切向压力

    static {
        if (jTablet == null) {
            try {
                jTablet = new JTablet();
            } catch (JTabletException e) {
                e.printStackTrace();
            }
        }
    }
    public static int pressure()
    {
        try
        {
            jTablet.poll();
            pressure = jTablet.getPressure();
        }catch(Exception err)
        {
            err.printStackTrace();
        }
        return pressure;
    }
    public static int altitude()
    {
        try
        {
            jTablet.poll();
        }catch(Exception err)
        {
            err.printStackTrace();
        }
        if(jTablet.getCursor() instanceof JTabletCursor)
            altitude = jTablet.getCursor().getData(JTabletCursor.DATA_ORIENTATION_ALTITUDE);
        return altitude;
    }
    public static int azimuth()
    {
        try
        {
            jTablet.poll();
        }catch(Exception err)
        {
            err.printStackTrace();
        }
        if(jTablet.getCursor() instanceof JTabletCursor)
            azimuth = jTablet.getCursor().getData(JTabletCursor.DATA_ORIENTATION_AZIMUTH);
        return azimuth;
    }
    /*
    public static int tangentPressure(){
        try
        {
            jTablet.poll();
        }catch(Exception err)
        {
            err.printStackTrace();
        }
        if(jTablet.getCursor() instanceof JTabletCursor)
            tangentPressure = jTablet.getCursor().getData(JTabletCursor.DATA_TANGENT_PRESSURE);
        return tangentPressure;
    }
    */
    static void refresh(){
        if (jTablet.getCursor() instanceof JTabletCursor) {
            try {
                jTablet.poll();
            } catch (JTabletException e) {
                e.printStackTrace();
            }
            // 更新信息。
            pressure = jTablet.getCursor().getData(JTabletCursor.DATA_PRESSURE);
            azimuth = jTablet.getCursor().getData(JTabletCursor.DATA_ORIENTATION_AZIMUTH);
            altitude = jTablet.getCursor().getData(JTabletCursor.DATA_ORIENTATION_ALTITUDE);
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println(PenData.pressure() + " " + " " + PenData.altitude() + " " + PenData.azimuth());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
