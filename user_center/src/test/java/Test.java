import com.meirengu.uc.utils.SerializableUtil;

/**
 * Created by huoyan403 on 3/17/2017.
 */
public class Test {

    public static void main(String args[]){
        System.out.println("lianzhang");
        System.out.println("lianzhang".getBytes());
        System.out.println("lianzhang".getBytes());
        System.out.println(getByte("lianzhang"));
        System.out.println(getByte("lianzhang"));
    }


    public void testGetByte(){

    }

    public static byte[] getByte(String key) {
        if (key instanceof String) {
            return String.valueOf(key).getBytes();
        } else {
            return SerializableUtil.serialize(key);
        }
    }

}
