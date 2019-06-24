package com.aa.bb.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aa.bb.md.StringUtil;

public class AnalyseJavaFile {
	JavaClass javaClass;
	
	JavaField currentJavaField;
	
	public AnalyseJavaFile(JavaClass javaClass) {
		this.javaClass = javaClass;
	}
	
	public void handleLine(String line) {
		getPackageName(line);
		getNotes(line);
		getTableName(line);
		getSimpleName(line);
		getJavaField(line);
	}

	public void getPackageName(String line) {
		if(line!=null && line.startsWith("import")) {
			javaClass.packageName = line.replaceAll("import", "").trim();
		}
	}
	
	public void getNotes(String line) {
		if(line == null) {
			return;
		}
		if(!javaClass.notes.start && line.startsWith("/*")) {
			javaClass.notes.start = true;
		}
		if(!javaClass.notes.end && !line.contains("@") && !line.startsWith("/*") && !line.endsWith("*/") && javaClass.notes.start) {
			String line1 = line.replaceAll("\\*", "").trim();
			if(line1.length()!=0) {
				javaClass.notes.note = line1;
			}
		}
		if(javaClass.notes.start && line.endsWith("*/")) {
			javaClass.notes.end = true;
		}
		
	}
	
	public void getTableName(String line) {
		if(line == null) {
			return;
		}
		if(javaClass.tableName == null || javaClass.tableName.trim().equals("")) {
			if(line.contains("@Table")) {
				Pattern pattern = Pattern.compile("@Table\\(.*name\\s*=\\s*\"(.*?)\".*\\)");
				Matcher matcher = pattern.matcher(line);
				while (matcher.find()) {
					javaClass.tableName = matcher.group(1);
				}
			}
		}
	}
	
	private void getTableNameFromSimpleName(String simpleName) {
		if(simpleName == null || simpleName.trim().equals("")) {
			return;
		}
		if(javaClass.tableName == null || javaClass.tableName.trim().equals("")) {
			javaClass.tableName = StringUtil.camel2UnderScore(simpleName);
		}
	}
	
	public void getSimpleName(String line) {
		if(line == null) {
			return;
		}
		if(javaClass.simpleName == null || javaClass.simpleName.trim().equals("")) {
			if(line.contains("class")) {
				Pattern pattern = Pattern.compile("public\\s+class\\s+(.*)\\s+extends.*");
				Matcher matcher = pattern.matcher(line);
				while (matcher.find()) {
					javaClass.simpleName = matcher.group(1);
					if(javaClass.tableName == null || javaClass.tableName.equals("")) {
						getTableNameFromSimpleName(javaClass.tableName);
					}
				}
			}
		}
		
	}

	public void getJavaField(String line) {
		if(line == null) {
			return;
		}
		if(currentJavaField == null) {
			currentJavaField = new JavaField();
		}
		if(javaClass.simpleName != null && !javaClass.simpleName.trim().equals("")) {
			if(line.contains("*") && !line.contains("/*") && !line.contains("*/")) {
//				currentJavaField.notes.note = line.replaceAll("\\*", "").trim();
				currentJavaField.tableChnName = line.replaceAll("\\*", "").trim();
			}
			if(line.contains("@Column")) {
				currentJavaField.column = true;
				Pattern pattern = Pattern.compile("@Column\\((updatable = ([a-z]*))?,?\\s*(nullable\\s+=\\s+([a-z]*))?,?\\s*(length = ([0-9]*(,\\d+){0,1}))?,?\\s*(nullable = ([a-z]*))?,?\\s*(unique = ([a-z]*))?.*\\)");
				Matcher matcher = pattern.matcher(line);
				while (matcher.find()) {
					if(matcher.group(2) != null && !matcher.group(2).equals("")) {
						currentJavaField.update = Boolean.valueOf(matcher.group(2));
					}
					if(matcher.group(4) != null && !matcher.group(4).equals("")) {
						currentJavaField.nullable = Boolean.valueOf(matcher.group(4));
					}
					if(matcher.group(6) != null && !matcher.group(6).equals("")) {
						currentJavaField.length = matcher.group(6);
					}
					if(matcher.group(9) != null && !matcher.group(9).equals("")) {
						currentJavaField.nullable = Boolean.valueOf(matcher.group(9));
					}
					if(matcher.group(11) != null && !matcher.group(11).equals("")) {
						currentJavaField.unique = Boolean.valueOf(matcher.group(11));
					}
				}
				if(line.contains("text")) {
					currentJavaField.length = "4096";
				}
			}
			if(line.contains("@Length")) {
				Pattern pattern = Pattern.compile("@Length\\(.*(max\\s*=\\s*(\\d+),)\\s*(message\\s*=\\s*\"(.*)\")?.*\\)");
				Matcher matcher = pattern.matcher(line);
				while (matcher.find()) {
					if(matcher.group(2) != null && !matcher.group(2).equals("")) {
						currentJavaField.length = matcher.group(2);
					}
					if(matcher.group(4) != null && !matcher.group(4).equals("")) {
						currentJavaField.notes.note = matcher.group(4);
						if(currentJavaField.notes.note!=null && !currentJavaField.notes.note.equals("")) {
							int index = currentJavaField.notes.note.indexOf("长度");
							if(index == -1) {
								index = currentJavaField.notes.note.indexOf("不能");
							}
							if(index == -1) {
								index = currentJavaField.notes.note.length();
							}
							currentJavaField.tableChnName = currentJavaField.notes.note.substring(0, index);
						}
						
					}
				}
				System.out.println("line=="+line);
				System.out.println("length=="+currentJavaField.length);
				System.out.println("note=="+currentJavaField.notes.note);
			}
			if(currentJavaField.column && (line.contains("public") || line.contains("protected") || line.contains("private"))) {
				currentJavaField.javaName = line.split("\\s+")[3].replace(";", "");
				currentJavaField.javaType = line.split("\\s+")[2];
				getTableName();
				getTableType();
				javaClass.fields.add(currentJavaField);
				System.out.println(javaClass);
				currentJavaField = null;
			}
		}
	}
	
	private void getTableName() {
		if(currentJavaField!=null && currentJavaField.javaName!=null) {
			currentJavaField.tableName = StringUtil.camel2UnderScore(currentJavaField.javaName);
			if(currentJavaField.tableChnName == null || currentJavaField.tableChnName.equals("")) {
				if(currentJavaField.tableName.equals("id")) {
					currentJavaField.tableChnName = "主键";
					currentJavaField.priKey = true;
				}else {
					currentJavaField.tableChnName = currentJavaField.tableName;
				}
			}
		}
	}
	
	private void getTableType() {
		if(currentJavaField!=null && currentJavaField.javaType!=null) {
			if(currentJavaField.javaType.equals("String")) {
				currentJavaField.tableType = "varchar";
			}else if(currentJavaField.javaType.equals("Long")) {
				currentJavaField.tableType = "bigint";
			}else if(currentJavaField.javaType.contains("Enum")) {
				currentJavaField.tableType = "varchar";
				currentJavaField.length = "20";
			}else if(currentJavaField.javaType.equals("LocalDateTime")) {
				currentJavaField.tableType = "timestamp";
			}else if(currentJavaField.javaType.equals("Integer")) {
				currentJavaField.tableType = "int";
			}
		}
	}
}
