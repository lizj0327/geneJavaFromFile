package com.aa.bb.md;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeneFile {

	public static void file(MapFile mapFile,String path) {
		File file = new File(path);
		if(mapFile.getGene()) {
			System.out.println(file.getName()+"已经生成，不要重复生成");
			return;
		}
		if(file.exists()) {
			file.delete();
		}
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		writeFile(mapFile,file);
		mapFile.setGene(true);
		mapFile.init();
	}
	
	public static void writeFile(MapFile mapFile,File file) {
    	try(BufferedWriter bw=new BufferedWriter(new FileWriter(file))) {
    		
			StringBuffer sb = new StringBuffer();
			sb.append("package "+ mapFile.getPackageName()+semicolon()+changeDoubleLine()); //包名
			sb.append(mapFile.getImports()+changeDoubleLine());  //导入包
			sb.append(mapFile.getNote()+changeDoubleLine());//注释
			sb.append(mapFile.getHeaders()+changeDoubleLine());//注解
			sb.append("public class "+ mapFile.getSimpleJavaName()+" extends JpaEntity<Long> {"+changeDoubleLine());
			for(MapField field:mapFile.getFields()) {
				sb.append(tab()+field.getHeader()+changeLine());
				sb.append(tab()+"private "+field.getType()+" "+field.getNameJava()+semicolon()+changeDoubleLine());
			}
			sb.append("}");
			bw.write(sb.toString());
			bw.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
	
	public static String changeLine() {
		return "\r\n";
	}
	
	public static String changeDoubleLine() {
		return changeLine()+changeLine();
	}
	
	public static String semicolon() {
		return ";";
	}
	
	public static String tab() {
		return "\t";
	} 
}
