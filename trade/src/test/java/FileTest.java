import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by root on 2017/4/1.
 */
public class FileTest {
    public static void main(String[] args) {
        String fileName = "c:/e/kuka.txt";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write("订单ID");
            out.newLine();
            out.write("订单金额");
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
