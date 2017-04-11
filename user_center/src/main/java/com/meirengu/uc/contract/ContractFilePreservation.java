package com.meirengu.uc.contract;

import com.meirengu.uc.utils.LogUtils;
import org.mapu.themis.api.common.PersonalIdentifer;
import org.mapu.themis.api.common.PreservationType;
import org.mapu.themis.api.request.contract.ContractFilePreservationCreateRequest;
import org.mapu.themis.api.response.preservation.PreservationCreateResponse;

/**
 * 合同文件保全
 */
public class ContractFilePreservation extends ThemisClientInit {

	public void ContractFilePreservation() {
		ContractFilePreservationCreateRequest.Builder builder = new ContractFilePreservationCreateRequest.Builder();
		builder.withFile("E:/test.pdf");
		builder.withPreservationTitle("XXX合同");//保全标题
		builder.withPreservationType(PreservationType.DIGITAL_CONTRACT);//保全类型，默认即可
		builder.withIdentifer(new PersonalIdentifer("500105198508252826","张三")); //测试是请修改为自己的姓名和身份证号
		builder.withSourceRegistryId("6");//平台来源ID
		builder.withContractAmount(0.00);//合同金额
		builder.withContractNumber("渝PCS-3247234");//合同编号
		builder.withMobilePhone("15123649601");//测试时请修改为自己的手机号码
		builder.withUserEmail("1816550434@qq.com");//测试是请修改为自己的邮箱
		builder.withComments("备注信息"); //备注
		builder.withIsNeedSign(true);//是否启用保全签章
		PreservationCreateResponse response = getClient().createPreservation(builder.build());

		LogUtils.logCreation(response);


		builder.build();
	}
}
