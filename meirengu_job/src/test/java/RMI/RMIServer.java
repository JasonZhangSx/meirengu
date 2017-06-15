package RMI;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class RMIServer  
{  
     public static void main(String[] args) throws Exception  
     {  
          ITranslate t = new ITranslateImpl();  
		  LocateRegistry.createRegistry(1099);
          Naming.bind("rmi://localhost:1099/server", t);  
          System.out.println("RMI server started and provide service now...");  
     }  
}  