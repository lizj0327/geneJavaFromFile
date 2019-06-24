package com.aa.bb.java;

public class JavaField {

	String javaType;
	
	String tableType;
	
	String javaName;
	
	String tableName;
	
	String tableChnName;
	
	Notes notes = new Notes(); //注释
	
	Boolean update = true;// 是否可以更新,true-是
	
	Boolean nullable = true; // 是否可以为空,true-是
	
	Boolean unique = false; // 是否唯一,true-是
	
	Boolean priKey = false; // 是否是主键 true -是
	
	String length;
	
	String fieldAnno = "@Column";
	
	boolean column;

	@Override
	public String toString() {
		return "JavaField [javaType=" + javaType + ", tableType=" + tableType + ", javaName=" + javaName
				+ ", tableName=" + tableName + ", tableChnName=" + tableChnName + ", notes=" + notes + ", update="
				+ update + ", nullable=" + nullable + ", unique=" + unique + ", priKey=" + priKey + ", length=" + length
				+ ", fieldAnno=" + fieldAnno + ", column=" + column + "]";
	}

}
