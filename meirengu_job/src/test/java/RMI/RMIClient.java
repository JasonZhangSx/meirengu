package RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClient
{
    public static void main(String[] args) throws Exception
    {
        //System.setSecurityManager(new RMISecurityManager()); 注释了就可以不要policy文件了，否则必须处理，具体可以自己试试
        try
        {
            ITranslate t = (ITranslate)Naming.lookup("rmi://localhost:1099/server");
            String str = "world";

            String rs = t.en2ch("world");
            System.out.println(str+"中文意思就是" + rs);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        catch (NotBoundException e)
        {
            e.printStackTrace();
        }
    }
}