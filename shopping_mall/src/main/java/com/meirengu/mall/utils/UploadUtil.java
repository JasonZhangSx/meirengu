package com.meirengu.mall.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadUtil {

	/**
	 * 上传图片
	 * @param request
	 * @param absolutePath
	 * @param showPath
	 * @param spec  多种规格的图片250x720,340x780
     * @return
     */
	public static Map<String, String> uploadPic(HttpServletRequest request, String fileName, String absolutePath, String showPath, String spec) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			/*String absolutePath = ConfigUtil.getLoadPicPath();
			String showPath = ConfigUtil.getPicShowPath();*/
			if (request instanceof MultipartHttpServletRequest) {
				MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
				MultipartFile file = req.getFile(fileName);
				if (file != null) {
					String originalFilename = file.getOriginalFilename();
					long currentTime = new Date().getTime();
					String suffix  = originalFilename.substring(originalFilename.lastIndexOf("."));
					String pictureName = currentTime + suffix;
					File absoluteFile = new File(absolutePath);
					if(!absoluteFile.exists()){
						absoluteFile.mkdirs();
					}
					File savedFile = new File(absoluteFile.getPath(), pictureName);
					boolean isSave = saveFile(file, savedFile);
					String[] specs = null;
					if(!StringUtils.isEmpty(spec)){
						specs = spec.split(",");
					}

					if(isSave){
						map.put("path", pictureName);
						if(specs != null){
							for (int i = 0; i < specs.length; i++){
								String sp = specs[i];
								String[] sps = sp.split("x");

								int width = Integer.parseInt(sps[0]);
								int height = Integer.parseInt(sps[1]);
								ImageUtil.thumbnailImage(absolutePath + "/" + pictureName, height, width, true);
							}
						}

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
		return map;
	}

	public static boolean saveFile(MultipartFile file, File f) {
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
