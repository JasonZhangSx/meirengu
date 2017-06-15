package RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ITranslateImpl extends UnicastRemoteObject  implements ITranslate {  
    private static final long serialVersionUID = -8492344530612173938L;  
    protected ITranslateImpl() throws RemoteException {  
        super();  
    }  
    @Override  
    public String en2ch(String str) throws RemoteException {
        if(str!=null&&str.equals("hello")){
            return "你好";
        }else if(str!=null&&str.equals("world")){
            return "世界";
        }else
            return "不知道怎么说";
    }  
}  