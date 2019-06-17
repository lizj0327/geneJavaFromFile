package com.aa.bb.md;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * 文件读取模板就是同级目录下的【商户微信平台配置.md】
 * @author lizj
 *
 */
public class ReadFile {

	public static void main(String[] args) {
		// 生成文件后存放的地址
		String destPath = "D:\\workspace-benyou1\\geneJavaFromFile\\src\\com\\aa\\bb\\files";
		// .md文件存放的地址
		String sourcePath = "D:\\workspace-benyou1\\wiki\\数据库物理设计\\出行\\行程\\电子票.md";
		File file = new File(sourcePath);
		readFiles(file,destPath);
	}
	
	public static void readFiles(File file,String destPath) {
		if(file.isDirectory()) {
			String[] names = file.list();
			for(String name:names) {
				readFiles(new File(file.getAbsolutePath()+"\\"+name), destPath);
			}
		}else {
			readFile(file,destPath);
		}
	}
	
	public static void readFile(File file,String destPath) {
		System.out.println("开始读取:"+file.getName());
		MapFile mapFile = new MdFile();
    	try(BufferedReader br=new BufferedReader(new FileReader(file))) {
			String line = br.readLine();
			while(true) {
				mapFile.handleLine(line);
				if(mapFile.getEnd()) {
					System.out.println("开始时生成文件:"+mapFile.getSimpleJavaName()+".java");
					GeneFile.file(mapFile, destPath+"\\"+mapFile.getSimpleJavaName()+".java");
				}
				if(line == null) {
					System.out.println("文件读完了"+file.getName());
					break;
				}
				line = br.readLine();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
}
