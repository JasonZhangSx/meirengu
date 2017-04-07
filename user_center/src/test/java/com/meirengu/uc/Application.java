package com.meirengu.uc;

/**
 * Created by huoyan403 on 3/23/2017.
 */

import com.meirengu.uc.service.impl.AddressServiceImpl;

//@SpringBootApplication
//@EnableRetry
public class Application {

    public static void main(String[] args) throws Exception {
//        VerityServiceImpl verityServiceImpl = new VerityServiceImpl();
//        verityServiceImpl.verityUser(123123,"000","6222030200002311516","13207603761","13112719910820095X","连彦进","mima");

        AddressServiceImpl addressService = new AddressServiceImpl();
        addressService.showProvinceList();



    }
//    @Test
//    public testAddressRedis(){
//
//    }

}