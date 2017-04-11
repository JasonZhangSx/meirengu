package com.meirengu.uc.utils;

import org.apache.commons.io.IOUtils;
import org.mapu.themis.api.domain.DigestDTO;
import org.mapu.themis.api.response.cer.CertificateFileGetResponse;
import org.mapu.themis.api.response.digest.GetLatestHashDigestResponse;
import org.mapu.themis.api.response.preservation.PreservationCreateResponse;
import org.mapu.themis.api.response.preservation.PreservationDownloadFileResponse;
import org.mapu.themis.api.response.preservation.PreservationGetResponse;
import rop.response.RopResponse;
import rop.security.MainError;
import rop.security.SubError;
import rop.thirdparty.com.alibaba.fastjson.JSON;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 日志工具
 * @author luopeng
 *         Created on 2014/5/5.
 */
public class LogUtils {

	public static void logCreation(PreservationCreateResponse response){
		System.out.println("-----------------------------------------------");
		if(response.isSuccess()){
			System.out.println("创建保全成功!");
			System.out.println("保全ID:"+response.getPreservationId());
			System.out.println("保全特征码:"+response.getDocHash());
			System.out.println("保全时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(response.getPreservationTime())));

		}else{
			System.out.println("创建保全失败");
			logError(response);

		}
		System.out.println("-----------------------------------------------");
	}

	private static void logError(RopResponse response) {
		MainError error = response.getError();
		if(error != null){
			System.out.println("Main Error Code:"+error.getCode());
			System.out.println("Main Error Message:"+error.getMessage());
			System.out.println("Main Error Solution:"+error.getSolution());
			List<SubError> subErrors = error.getSubErrors();
			if(subErrors != null){
				for(SubError subError : subErrors){
					System.out.println("Sub Error Code:"+subError.getCode());
					System.out.println("Sub Error Message:"+subError.getMessage());
				}
			}
		}else{
			System.out.println("Error is null");
		}
	}

	public static void logPreservation(PreservationGetResponse response) {

		System.out.println("-----------------------------------------------");
		if(response.isSuccess()){
			System.out.println("获取保全成功!");
			System.out.println("保全ID:"+response.getPreservationId());
			System.out.println("保全特征码:"+response.getDocHash());
			System.out.println("保全时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(response.getPreservationTime())));

		}else{
			System.out.println("获取保全失败");
			logError(response);

		}
		System.out.println("-----------------------------------------------");
	}

	public static void logDigest(GetLatestHashDigestResponse response) {

		System.out.println("-----------------------------------------------");
		if(response.isSuccess()){
			System.out.println("获取最新哈希摘要成功!");
			for(DigestDTO digestDTO:response.getDigestDTOList()){
				System.out.println(digestDTO.toString());
			}
		}else{
			System.out.println("获取最新哈希摘要失败");
			logError(response);

		}
		System.out.println("-----------------------------------------------");
	}

	public static void logResponse(RopResponse response) {

		System.out.println("-----------------------------------------------");
		if(response.isSuccess()){
			System.out.println("操作成功!");
			System.out.println(JSON.toJSONString(response));
		}else{
			System.out.println("操作失败");
			logError(response);

		}
		System.out.println("-----------------------------------------------");
	}

	public static void logCertificateFileResponse(CertificateFileGetResponse response, String fileSavePath) throws IOException {
		byte[] content = response.getContent();
		_innerFileLog(response,content,fileSavePath);
	}

	private static void _innerFileLog(RopResponse response, byte[] content, String fileSavePath) throws IOException {
		System.out.println("-----------------------------------------------");
		if(response.isSuccess()){
			System.out.println("操作成功!");
			String filePath = writeToFile(content,fileSavePath);
			System.out.println("文件存储路径："+filePath);
		}else{
			System.out.println("操作失败");
			logError(response);
		}
		System.out.println("-----------------------------------------------");

	}

	public static void logPreservationFileResponse(PreservationDownloadFileResponse response, String fileSavePath) throws IOException {
		byte[] content = response.getContent();
		_innerFileLog(response,content,fileSavePath);
	}

	private static String writeToFile(byte[] content, String fileSavePath) throws IOException {
		String fileName = UUID.randomUUID().toString().replace("-", "");
		String filePath = fileSavePath+"/"+fileName;
		IOUtils.write(content,new FileOutputStream(filePath));
		return filePath;
	}

}
