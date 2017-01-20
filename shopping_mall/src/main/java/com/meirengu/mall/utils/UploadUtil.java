package com.meirengu.mall.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadUtil {

	public static Map<String, String> uploadPic(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String absolutePath = ConfigUtil.getLoadPicPath();
			String showPath = ConfigUtil.getPicShowPath();
			if (request instanceof MultipartHttpServletRequest) {
				MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
				MultipartFile file = req.getFile("pic");
				if (file != null) {
					String originalFilename = file.getOriginalFilename();
					String pictureName = new Date().getTime()
							+ originalFilename.substring(originalFilename.lastIndexOf("."));
					File savedFile = new File(absolutePath, pictureName);
					boolean isSave = saveFile(file, savedFile);
					ImageUtil.thumbnailImage(absolutePath + "/" + pictureName, 200, 250);
					if (isSave) {
						map.put("path", savedFile.getAbsolutePath());
						map.put("zoopath", showPath + "zoo" + pictureName);
					}
				}
			}
		} catch (Exception e) {
			return map;
		}
		return map;
	}

	private static boolean saveFile(MultipartFile file, File f) {
		try {
			long size = file.getSize();
			byte[] data = new byte[(int) size];
			InputStream input = file.getInputStream();
			input.read(data);

			if (!f.exists()) {
				f.createNewFile();
			}

			FileOutputStream outStream = new FileOutputStream(f);

			outStream.write(data);
			outStream.close();
			input.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
