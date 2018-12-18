package com.inspur.train.exam.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordGernerateTest1 {

	public static void main(String[] args) throws IOException {
		File file = new File("c:\\test001.docx");
//		InputStream is = new FileInputStream();
		XWPFDocument xdoc = new XWPFDocument();
		XWPFParagraph xpara = xdoc.createParagraph();
		 
		// 一个XWPFRun代表具有相同属性的一个区域。
		XWPFRun run = xpara.createRun();
		run.setBold(true); // 加粗
		run.setText("加粗的内容");
		
		run = xpara.createRun();
		run.setColor("FF0000");
		run.setFontSize(15);
		run.setText("\n\n插入内容。");
		
		xpara = xdoc.createParagraph();
		xpara.setFirstLineIndent(20);
		run = xpara.createRun();
		run.setText("aaaaa");
		run.addCarriageReturn();
		
		addPictureToRun(run, new File("c:\\test1.jpg"));
		
		run.addCarriageReturn();
		run.setText("dadfafa大家好");
		addPictureToRun(run, new File("c:\\jiayou.jpg"));
		run.setText("我在图片后！");
		
		OutputStream os = new FileOutputStream(file);
		xdoc.write(os);
		
		xdoc.close();
		os.close();

	}
	
	public static void addPictureToRun(XWPFRun run, File picFile){
		try {
			BufferedImage bi = ImageIO.read(picFile);
			FileInputStream fis = new FileInputStream(picFile);
			
			int picType = XWPFDocument.PICTURE_TYPE_PNG;
			if(picFile.getName().endsWith(".jpg")){
				picType = XWPFDocument.PICTURE_TYPE_JPEG;
			}else if(picFile.getName().endsWith(".png")){
				picType = XWPFDocument.PICTURE_TYPE_PNG;
			}else if(picFile.getName().endsWith("gif")){
				picType = XWPFDocument.PICTURE_TYPE_GIF;
			}
//			System.out.println(bi.getWidth());
//			System.out.println(bi.getHeight());
			run.addPicture(fis, picType, picFile.getAbsolutePath(), Units.toEMU(bi.getWidth()), 
					Units.toEMU(bi.getHeight()));
			fis.close();
		} catch (InvalidFormatException|IOException e) {
			e.printStackTrace();
		} 
	}

}
