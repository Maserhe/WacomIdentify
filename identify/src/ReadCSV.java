import com.csvreader.CsvReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Vector;

/**
 * ����:
 *  ��ȡʵ���csv��Ϣ��
 * @author Maserhe
 * @create 2021-02-16 14:50
 */
public class ReadCSV {


    // ����ʵ�������
    // ��Ϊ���֣� 1����׼�Ĵ�ӡ����  2��ģ�µ���
    public static String path;
    // 1. ���� ���֣� 2��csv�ĵ㡣
    public static int status = 3;

    public static int number = 0;

    // ����ͼ�εĻ���
    public static ArrayList<String> getInfo0(int line) {

        if (status != 0) return null;
        CsvReader csvReader = null;
        try {
            csvReader = new CsvReader(path, ',', Charset.forName("GBK"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<String> ans = new ArrayList<String>();
        try {
            csvReader.readHeaders();
            int length = csvReader.getHeaderCount();
            while (csvReader.readRecord())
            {
                ans.add(csvReader.get(line));
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ans;
    }

    // ��ȡcsv ���ɶ�άArraylist
    public static ArrayList<ArrayList<WritePoint>> getInfo1() {
        if (status != 1) return null;
        ArrayList<ArrayList<WritePoint>> ans = new ArrayList<ArrayList<WritePoint>>();
        CsvReader csvReader = null;

        try {
            csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));
            int length = csvReader.getHeaderCount();
            ArrayList<WritePoint> temp = new ArrayList<WritePoint>();
            int lintTemp = 0;
            // ������ͷ
            csvReader.readHeaders();
            while (csvReader.readRecord())
            {
                if (Integer.valueOf(csvReader.get(13)) != lintTemp + 1) {
                    ans.add(temp);
                    lintTemp += 1;
                    temp = new ArrayList<WritePoint>();
                }
                temp.add(new WritePoint(Integer.valueOf(csvReader.get(1)), Integer.valueOf(csvReader.get(2))));
            }

            if (temp.size() != 0) ans.add(temp);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static void main(String[] args) {
        ReadCSV.path = System.getProperty("user.dir") + "/" + "123465.csv";
        ReadCSV.status = 1;

        System.out.println(path);
        ArrayList<ArrayList<WritePoint>> info = ReadCSV.getInfo1();

        for (ArrayList<WritePoint> list : info) {
            for (WritePoint writePoint : list) {
                System.out.println(writePoint.getX() + " " + writePoint.getY());
            }
        }
    }
}

class WritePoint {
    int x;
    int y;

    public WritePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}