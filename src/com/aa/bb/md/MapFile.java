package com.aa.bb.md;

import java.util.ArrayList;
import java.util.List;

public abstract class MapFile {

	String startMarker;//开始标志
	
	boolean start;//是否结束
	
	String endMarker;//结束标志
	
	boolean end;//是否开始
	
	String packageName;
	
	String javaName;//全路径类名
	
	String simpleJavaName;//类名
	
	String tableName;//表名
	
	String tableNote;//中文表名
	
	String imports;
	
	String note;//注释
	
	String headers;//注解
	
	List<MapField> fields = new ArrayList<>();//字段
	
	boolean gene;
	
	public void init(){
		
	}
	
	public void handleLine(String line) {
		System.out.println(line == null);
		if(!start) {
			setStart(line);
		}
		if(start) {
			setTableName(line);
			setNote();
			setImports();
			setHeaders();
			setFields(line);
			setEnd(line);
		}
	}
	
	
	public abstract boolean getStart();
	
	public abstract void setStart(String line);
	
	public abstract String getTableName();
	
	public abstract void setTableName(String line);
	
	public abstract String getNote();
	
	public abstract void setNote();
	
	public abstract void setHeaders();
	
	public abstract String getHeaders();
	
	public abstract void setFields(String line);
	
	public abstract List<MapField> getFields();
	
	public abstract boolean setEnd(String line);
	
	public abstract boolean getEnd();
	
	public abstract void setPackageName(String packageName);
	
	public abstract String getPackageName();
	
	public abstract String getImports();
	
	public abstract void setImports();
	
	public abstract String getJavaName();
	
	public abstract void setJavaName();
	
	public abstract String getSimpleJavaName();
	
	public abstract boolean getGene();
	
	public abstract void setGene(boolean gene);
}
