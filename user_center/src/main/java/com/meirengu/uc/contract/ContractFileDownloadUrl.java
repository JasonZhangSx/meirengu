package com.meirengu.uc.contract;

import com.meirengu.uc.utils.LogUtils;
import org.mapu.themis.api.request.contract.ContractFileDownloadUrlRequest;
import org.mapu.themis.api.response.contract.ContractFileDownloadUrlResponse;

/**
 * 合同文件下载
 */
public class ContractFileDownloadUrl extends ThemisClientInit {

	public void ContractFileDownloadUrl() {
		ContractFileDownloadUrlRequest request = new ContractFileDownloadUrlRequest();
		request.setPreservationId(541260L);
		ContractFileDownloadUrlResponse response = getClient().getContactFileDownloadUrl(request);
		LogUtils.logResponse(response);
	}
}
