package com.meirengu.uc;

import com.meirengu.utils.ObjectToFile;
import org.junit.Test;

import java.util.List;

/**
 * Created by huoyan403 on 4/1/2017.
 */
public class TestInviteRewardController {


    @Test
    public void test(){
//        InviteRewardController inviteRewardController = new InviteRewardController();
//        inviteRewardController.notify("E://1.txt");

        List<String[]> arr = ObjectToFile.readArray("E://2.txt");
        for(String[] arr1:arr){
            for(String ar:arr1){
                System.err.print(ar+",");
            }
        }
    }
}
