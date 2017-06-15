package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITranslate extends Remote {  
    public String en2ch(String str) throws RemoteException;  
} 