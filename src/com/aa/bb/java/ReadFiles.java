package com.aa.bb.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class ReadFiles {

	public static void main(String[] args) {
		// 生成文件后存放的地址
		String destPath = "D:\\workspace-benyou1\\geneJavaFromFile\\src\\com\\aa\\bb\\files";
		// .java文件存放的地址
		String sourcePath = "D:\\workspace-benyou1\\ottgs\\components\\support\\src\\main\\java\\com\\cqbxzc\\go\\msg\\vms\\domain\\VsmMessageSendRecord.java";
		
//		File file = new File(sourcePath);
//		readFiles(file,destPath);
		List<String> paths = new ArrayList<>();
		paths.add("D:\\workspace-benyou1\\ottgs\\components\\support\\src\\main\\java\\com\\cqbxzc\\go\\msg\\vms\\domain\\VsmMessageSendRecord.java");
		paths.add("D:\\workspace-benyou1\\ottgs\\components\\support\\src\\main\\java\\com\\cqbxzc\\go\\msg\\domain\\MessageEvent.java");
		paths.add("D:\\workspace-benyou1\\ottgs\\components\\support\\src\\main\\java\\com\\cqbxzc\\go\\msg\\domain\\MessageTemplate.java");
		paths.add("D:\\workspace-benyou1\\ottgs\\components\\support\\src\\main\\java\\com\\cqbxzc\\go\\msg\\email\\domain\\EmailMessageSendRecord.java");
		paths.add("D:\\workspace-benyou1\\ottgs\\components\\support\\src\\main\\java\\com\\cqbxzc\\go\\msg\\servicemsg\\domain\\WeixinMessageSendRecord.java");
		paths.add("D:\\workspace-benyou1\\ottgs\\components\\support\\src\\main\\java\\com\\cqbxzc\\go\\msg\\sms\\domain\\ShortMessageSendRecord.java");
		readFiles(paths,destPath);
	}
	
	/**
	 * 处理文件夹或单个文件
	 * @param file
	 * @param destPath
	 */
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
	
	/**
	 * 处理文件列表
	 * @param files
	 * @param destPath
	 */
	public static void readFiles(List<String> paths,String destPath) {
		for(String path:paths) {
			File file = new File(path);
			readFile(file,destPath);
		}
	}
	
	public static void readFile(File file,String destPath) {
		System.out.println("开始读取:"+file.getName());
		AnalyseJavaFile analyseJavaFile = new AnalyseJavaFile(new JavaClass());
    	try(BufferedReader br=new BufferedReader(new FileReader(file))) {
			String line = br.readLine();
			while(true) {
				analyseJavaFile.handleLine(line);
				if(line == null) {
					System.out.println("文件读完了"+file.getName());
					break;
				}
				line = br.readLine();
			}
			GeneFile.file(analyseJavaFile, destPath+"\\"+file.getName().replace("java", "md"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}
