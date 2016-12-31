import com.meirengu.medical.service.IDoctorService;
import com.meirengu.medical.vo.DoctorVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/28 13:54.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring-config-datasource.xml"})
public class test {
    @Resource
    private IDoctorService iDoctorService;
    private ApplicationContext ac = null;
    @Test
    public String test() {
        DoctorVo doctorVo = new DoctorVo();
        System.out.println(iDoctorService.getDoctorData(doctorVo));
        return null;
    }
    @Test
	public void before() {
		ac = new ClassPathXmlApplicationContext("spring-servlet.xml");
        DoctorVo doctorVo = new DoctorVo();
        System.out.println(iDoctorService.getDoctorData(doctorVo));
	}
}
