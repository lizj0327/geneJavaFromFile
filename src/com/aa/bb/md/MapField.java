package com.aa.bb.md;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字段
 * @author lizj
 *
 */
public class MapField {

	String header;//注解
	
	String access = "private";
	
	String type;//类型
	
	String name;//英文字段名对应的表名
	
	String nameJava;//英文字段名
	
	String nameChn;//中文名称
	
	Integer length;//长度
	
	boolean primaryKey = false;
	
	boolean nullAble = true;
	
	boolean unique = false;
	
	boolean enumType = false;
	
	String defaultValue;
	
	String remark;
	
	static List<String> ignoreNames = new ArrayList<>();
	
	{
		ignoreNames.add("create_by");
		ignoreNames.add("create_date_time");
		ignoreNames.add("last_modified_by");
		ignoreNames.add("last_modified_date_time");
	}
	
	static Map<String,Map<String,String>> headersTemplate = new HashMap<>();
	
	{
		Map<String,String> nameMap = new HashMap<>();
		Map<String,String> typeMap = new HashMap<>();
		Map<String,String> enumMap = new HashMap<>();
		nameMap.put("id", "@Id\r\n" + 
				"    @GeneratedValue(strategy = GenerationType.IDENTITY)\r\n" + 
				"    @Column(updatable = false, nullable = false)");
		
		typeMap.put("String", "@ApiModelProperty(value = \"%s\"%s)\r\n" + 
				"	@Column(length = %d%s%s)\r\n" + 
				"	@Length(max = %d, message = \"%s长度不能超过%d\")");
		
		typeMap.put("Long", "@ApiModelProperty(value = \"%s\")\r\n" + 
				"    @Column");
		typeMap.put("LocalDateTime", "@ApiModelProperty(value = \"%s\")\r\n" + 
				"	@CreationTimestamp\r\n" + 
				"	@Column(columnDefinition = \"timestamp(6) default null\", updatable = false)\r\n" + 
				"	@DateTimeFormat(pattern = ClockUtil.LOCAL_DATE_TIME_FORMATTER_PATTERN)\r\n" + 
				"	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ClockUtil.LOCAL_DATE_TIME_FORMATTER_PATTERN, timezone = ClockUtil.DEFAULT_TIME_ZONE)\r\n");
		
		enumMap.put("enum", "@ApiModelProperty(value = \"%s\"%s)\r\n" + 
				"	@Enumerated(EnumType.STRING)");
		headersTemplate.put("name", nameMap);
		headersTemplate.put("type", typeMap);
		headersTemplate.put("enum", enumMap);
	}
	
	public void setHeader(){
		String template = "";
		if(!StringUtil.empty(name)) {
			template = headersTemplate.get("name").get(name);
			if(!StringUtil.empty(template)) {
				header = template;
			}
		}
		if(StringUtil.empty(template) && !StringUtil.empty(type) && !enumType) {
			template = headersTemplate.get("type").get(type);
			if(!StringUtil.empty(template)) {
				if(type.equals("String")) {
					header = String.format(template, this.getNameChn(),this.getNullAble()?"":", required = true",
							this.getLength(),nullAble?"":", nullable = true",this.getUnique()?", unique = true":"",
							this.getLength(),this.getNameChn(),this.getLength());
				}else if(type.equals("Long")) {
					header = String.format(template, this.getNameChn());
				}else if(type.equals("LocalDateTime")) {
					header = String.format(template, this.getNameChn());
				}
			}
		}
		if(StringUtil.empty(template) && !StringUtil.empty(type) && enumType) {
			template = headersTemplate.get("enum").get("enum");
			if(!StringUtil.empty(template)) {
				header = String.format(template, this.getNameChn(),this.getNullAble()?"":", required = true");
			}
		}
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLength() {
		return length;
	}

	public String getNameJava() {
		return nameJava;
	}

	public void setNameJava(String nameJava) {
		this.nameJava = nameJava;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public boolean getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean getNullAble() {
		return nullAble;
	}

	public void setNullAble(boolean nullAble) {
		this.nullAble = nullAble;
	}

	public boolean getUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNameChn() {
		return nameChn;
	}

	public void setNameChn(String nameChn) {
		this.nameChn = nameChn;
	}

	public boolean getEnumType() {
		return enumType;
	}

	public void setEnumType(boolean enumType) {
		this.enumType = enumType;
	}
	
}
