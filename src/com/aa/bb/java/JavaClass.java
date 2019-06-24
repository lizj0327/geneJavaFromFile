package com.aa.bb.java;

import java.util.ArrayList;
import java.util.List;

public class JavaClass {

	Notes notes = new Notes();
	
	String simpleName;
	
	String packageName;
	
	String fullName;
	
	String tableName;
	
	List<JavaField> fields = new ArrayList<>();
	
	List<JavaField> defaultFields = new ArrayList<>();
	
	{
		JavaField field1 = new JavaField();
		field1.tableChnName="创建者";
		field1.tableName="create_by";
		field1.tableType="varchar";
		field1.length="20";
		field1.notes.note="";
		JavaField field2 = new JavaField();
		field2.tableChnName="创建时间";
		field2.tableName="create_date_time";
		field2.tableType="timestamp";
		field2.length="6";
		field2.notes.note="2018-01-22 33:44:55";
		JavaField field3 = new JavaField();
		field3.tableChnName="最后修改者";
		field3.tableName="last_modified_by";
		field3.tableType="varchar";
		field3.length="20";
		field3.notes.note="";
		JavaField field4 = new JavaField();
		field4.tableChnName="最后修改时间";
		field4.tableName="last_modified_date_time";
		field4.tableType="timestamp";
		field4.length="6";
		field4.notes.note="2018-01-22 33:44:55";
		defaultFields.add(field1);
		defaultFields.add(field2);
		defaultFields.add(field3);
		defaultFields.add(field4);
	}

	@Override
	public String toString() {
		return "JavaClass [notes=" + notes + ", simpleName=" + simpleName + ", packageName=" + packageName
				+ ", fullName=" + fullName + ", tableName=" + tableName + ", fields=" + fields + "]";
	}
	
}
