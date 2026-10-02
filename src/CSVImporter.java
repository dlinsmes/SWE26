import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class CSVImporter {
    public static ArrayList<String []> getData(String filename) {
        File f = new File(filename);
        ArrayList<String [] > data = new ArrayList<String []> ();

        try {
            Scanner s = new Scanner(f);

            while (s.hasNext()) {
                String line = s.nextLine();
                String[] row = line.split(",");
                data.add(row);
            }
        }
        catch (Exception e) {
            System.out.println("invalid file. no data imported");
        }

        return data;
    }
}