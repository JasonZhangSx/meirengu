package com.meirengu.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	/**
	 * 缩略图片
	 * 
	 * @param srcImgFilePath
	 * @param newImgFilePath
	 * @param toWidth
	 * @param toHeight
	 * @throws IOException
	 */
	public static void zoomImage(String srcImgFilePath, String newImgFilePath, Integer toWidth, Integer toHeight) throws IOException {
		
		// 读入文件
		File _file = new File(srcImgFilePath);
		if (_file.exists()) {
			// 构造Image对象
			BufferedImage src = ImageIO.read(_file);

			// 构造缩放后的图片
			BufferedImage dest = new BufferedImage(toWidth == null ? src
					.getWidth() : toWidth, toHeight == null ? src.getHeight()
					: toHeight, BufferedImage.TYPE_INT_RGB);
			// 绘制缩放后的图片
			dest.getGraphics().drawImage(src, 0, 0,
					toWidth == null ? src.getWidth() : toWidth,
					toHeight == null ? src.getHeight() : toHeight, null);
			String formatName = newImgFilePath.substring(newImgFilePath.lastIndexOf(".") + 1);  
//			FileOutputStream out = new FileOutputStream(newImgFilePath);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(dest);
//			jep.setQuality(0.7f, true);
//			encoder.encode(dest,jep);
//			out.close();
			ImageIO.write(dest, /*"GIF"*/ formatName /* format desired */ , new File(newImgFilePath) /* target */ ); 
		}
	}
	
	
    public static void zoomNative(String srcImgFilePath, String newImgFilePath, Integer toWidth, Integer toHeight) throws IOException {
		
    	int _new_width = toWidth;
    	int _new_height = toHeight;
    	
		// 读入文件
		File _file = new File(srcImgFilePath);
		if (_file.exists()) {
			// 构造Image对象
			BufferedImage src = ImageIO.read(_file);
			
			//原始图象的宽和高 
	        int _old_width  = src.getWidth(); 
	        int _old_height = src.getHeight(); 
	 
	        //改变前图象的比例 
	        int _old_ratio = _old_width/_old_height;
	        
	        //实际图：宽小高小 都比预设的要小
	        if(_old_width <= _new_width && _old_height <= _new_height){
	        	//不需要裁减
	        	return;
	        }
	        
	        //实际图：宽大高小在范围内  以宽按原比例缩略
	        if(_old_width > _new_width && _old_height <= _new_height){
	        	//宽不变 
	        	_new_height = _new_width/_old_ratio;
	        }
	        
	        //实际图：宽小高大 以高按原比例缩放
	        if(_old_width <= _new_width && _old_height > _new_height){
	        	//高不变 
	        	_new_width = _new_height*_old_ratio;
	        }
	        
	        //实际图：宽大高大  以差距大的按原比例缩放
	        if(_old_width > _new_width && _old_height > _new_height){
	        	
	        	if((_old_width-_new_width) > (_old_height-_new_height)){
	        		//以宽按原比例缩
	        		_new_height = _new_width/_old_ratio;
	        	} else {
	        		//以高按原比例缩
	        		_new_width = _new_height*_old_ratio;
	        	}
	        }

			// 构造缩放后的图片
			BufferedImage dest = new BufferedImage(_new_width, _new_height, BufferedImage.TYPE_INT_RGB);
			// 绘制缩放后的图片
			dest.getGraphics().drawImage(src, 0, 0,_new_width,_new_height, null);
			
//			FileOutputStream out = new FileOutputStream(newImgFilePath);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(dest);
//			jep.setQuality(0.7f, true);
//			encoder.encode(dest,jep);
//			out.close();
			
			String formatName = newImgFilePath.substring(newImgFilePath.lastIndexOf(".") + 1);  
			ImageIO.write(dest, /*"GIF"*/ formatName /* format desired */ , new File(newImgFilePath) /* target */ ); 
		}
	}
    
    
   
	
   
	/**
	 * 图片合成-加水印
	 * 
	 * @param pressImg
	 * @param targetImg
	 * @param x
	 * @param y
	 * @return
	 * @throws IOException
	 */
	public static String waterMarkImage(String pressImg, String targetImg, int x, int y) throws IOException {
		
		String newPath = "";
			
        File _file = new File(targetImg);
        if (_file.exists()){
            
            Image src = ImageIO.read(_file);
            
            int wideth = src.getWidth(null);   
            int height = src.getHeight(null);   
            
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);   
            Graphics g = image.createGraphics();   
            g.drawImage(src, 0, 0, wideth, height, null);   
            // 水印文件   
            File _filebiao = new File(pressImg);   
            Image src_biao = ImageIO.read(_filebiao);   
            int wideth_biao = src_biao.getWidth(null);   
            int height_biao = src_biao.getHeight(null);   
            g.drawImage(src_biao, wideth - wideth_biao - x, height - height_biao -y, wideth_biao, height_biao, null);               
            g.dispose();   
            
            // 输出图像
//            FileOutputStream out = new FileOutputStream(targetImg);   
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
//            encoder.encode(image);   
//            out.close();
            String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);  
			ImageIO.write(image, /*"GIF"*/ formatName /* format desired */ , new File(targetImg) /* target */ ); 
		
        }
		
		return newPath;
	}

	/**
	 * 图片变灰
	 * 
	 * @param srcFile
	 * @param destFilePath
	 * @throws IOException 
	 */
	public static void turnGray(File srcFile, String destFilePath) throws IOException{
		
        if (srcFile.exists()){
        	BufferedImage src = ImageIO.read(srcFile);
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(destFilePath));  
        }
	}
	
	
	
 	/**
 	 * 图像格式转换
 	 * 
 	 * @param f
 	 * @param formatName
 	 * @param result
 	 * @throws IOException
 	 */
 	public static void convert(File f, String formatName, String result) throws IOException{  
        if(f.exists()){
        	BufferedImage src = ImageIO.read(f);
        	ImageIO.write(src, formatName, new File(result)); 
        }
     }
 	
	public static void main(String[] args) throws IOException {

		String storagePath = "C:\\Users\\wangpengwx\\Desktop\\";
		String imgName = "001.jpeg";
		String new_imgName = "0012.jpeg";
		ImageUtil.zoomImage("D:\\workspace\\tank_thai_website\\src\\main\\webapp\\pic\\pics\\a1.jpg", "D:\\workspace\\tank_thai_website\\src\\main\\webapp\\pic\\pics\\a2.jpg", 300, 300);
	}
}