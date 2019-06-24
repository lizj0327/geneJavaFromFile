package com.aa.bb.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeneFile {

	public static void file(AnalyseJavaFile analyseJavaFile,String path) {
		File file = new File(path);
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
	
		writeFile(analyseJavaFile,file);
	}
	
	public static void writeFile(AnalyseJavaFile analyseJavaFile,File file) {
    	try(BufferedWriter bw=new BufferedWriter(new FileWriter(file))) {
    		
			StringBuffer sb = new StringBuffer();
			sb.append("# "+ geneChnName(analyseJavaFile)+changeDoubleLine()); //包名
			sb.append("**"+geneTableName(analyseJavaFile)+"**"+changeDoubleLine());  //导入包
			sb.append(geneTableHeader()+changeLine());
			sb.append(geneFields(analyseJavaFile));
			bw.write(sb.toString());
			bw.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
	
	public static String geneChnName(AnalyseJavaFile analyseJavaFile) {
		return analyseJavaFile.javaClass.notes.note;
	}
	
	public static String geneTableName(AnalyseJavaFile analyseJavaFile) {
		return analyseJavaFile.javaClass.tableName;
	}
	
	public static String geneTableHeader() {
		return "|名称|字段|数据类型|长度|主键|可空|唯一|默认值|备注|\r\n" + 
				"|--------|--------|------------|--------|--------|--------|--------|--------|--------|";
	}
	public static String geneFields(AnalyseJavaFile analyseJavaFile) {
		StringBuffer sb = new StringBuffer();
		analyseJavaFile.javaClass.fields.addAll(analyseJavaFile.javaClass.defaultFields);
		for(JavaField field:analyseJavaFile.javaClass.fields) {
			sb.append(getFieldSplit()).append(field.tableChnName).append(getFieldSplit())
			.append(field.tableName).append(getFieldSplit()).append(field.tableType)
			.append(getFieldSplit()).append(changeNull(field.length)).append(getFieldSplit())
			.append(field.priKey?"是":"").append(getFieldSplit()).append(field.nullable?"":"否")
			.append(getFieldSplit()).append(field.unique?"是":"").append(getFieldSplit())
			.append(changeNull(field.notes.note)).append(getFieldSplit()).append("\r\n");
		}
		return sb.toString();
	}
	
	public static String getFieldSplit() {
		return "|";
	}
	public static String changeNull(String str) {
		if(str == null) {
			str = "";
		}
		return str;
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
