package common;

import com.meirengu.common.PasswordEncryption;

import static com.meirengu.common.PasswordEncryption.createHash;
import static com.meirengu.common.PasswordEncryption.validatePassword;

/**
 * Created by huoyan403 on 3/21/2017.
 */
public class PasswordEncryptionTest {


    /**
     * Tests the basic functionality of the PasswordHash class
     *
     * @param   args        ignored
     */
    public static void main(String[] args)
    {
        try
        {
            // Print out 10 hashes
            for(int i = 0; i < 10; i++)
                System.out.println(createHash("p\r\nassw0Rd!"));

            // Test password validation
            boolean failure = false;
            System.out.println("Running tests...");
            for(int i = 0; i < 100; i++)
            {
                String password = ""+i;
                String hash = createHash(password);
                String secondHash = createHash(password);
                if(hash.equals(secondHash)) {
                    System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
                    failure = true;
                }
                String wrongPassword = ""+(i+1);
                if(PasswordEncryption.validatePassword(wrongPassword,password)) {
                    System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
                    failure = true;
                }
                if(!PasswordEncryption.validatePassword(password, hash)) {
                    System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
                    failure = true;
                }
            }
            if(failure)
                System.out.println("TESTS FAILED!");
            else
                System.out.println("TESTS PASSED!");


            System.err.print(validatePassword("","1000:29bcf0ef3b1f33698b2254415caf7c81957770883a8b65b7:d9cb6f281a95c4a44415b5e5e37fb607"));
            System.err.print(createHash("123456"));
        }
        catch(Exception ex)
        {
            System.out.println("ERROR: " + ex);
        }
    }

}
