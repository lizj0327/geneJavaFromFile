package com.aa.bb.md;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析 *.md文件
 * @author lizj
 *
 */
public class MdFile extends MapFile{
	
	String startMarker = "#";//开始标志
	
	String endMarker = "\\r\\n";//结束标志
	
	int fieldLines = 0;//  文件中 | 行的个数
	
	public void init(){
		fieldLines = 0;
		
		start = false;//是否开始
		
		end = false;//是否开始
		
		packageName = "";
		
		javaName = "";//全路径类名
		
		simpleJavaName = "";//类名
		
		tableName = "";//表名
		
		tableNote = "";//中文表名
		
		imports = "";
		
		note = "";//注释
		
		headers = "";//注解
		
		fields = new ArrayList<>();//字段
		
		gene = false;
	}
	
	public boolean getStart() {
		return start;
	}
	public void setStart(String line) {
		if(StringUtil.empty(line)) {
			return;
		}
		if(line.startsWith(startMarker) && !(tableNote = line.replaceAll(startMarker, "").trim()).equals("")) {
			start = true;
		}
	}
	
	public boolean getEnd() {
		return end;
	}
	
	public boolean setEnd(String line) {
		System.out.println("start:"+start+",end="+end+",fields.size="+fields.size()+",line="+line);
		if(start && fields!=null &&  fields.size()!=0 && StringUtil.empty(line)) {
			end = true;
		}
		return end;
	}
	
	public String getJavaName() {
		return javaName;
	}
	
	public void setJavaName() {
		String[] keys = tableName.split("_");
		if(keys.length>1) {
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<keys.length;i++) {
				keys[i] = StringUtil.firstUpperCase(keys[i]);
				sb.append(keys[i]);
			}
			simpleJavaName = sb.toString();
		}else {
			simpleJavaName = StringUtil.firstUpperCase(tableName);
		}
		javaName = getPackageName()+"."+simpleJavaName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String line) {
		if(StringUtil.empty(line)) {
			return;
		}
		if(start && line.startsWith("**") && line.endsWith("**")) {
			tableName = line.replaceAll("\\*", "");
			setJavaName();
		}
	}
	
	public void setImports() {
		if(StringUtil.empty(imports)) {
			imports = "import javax.persistence.Column;\r\n" + 
					"import javax.persistence.Entity;\r\n" + 
					"import javax.persistence.EnumType;\r\n" + 
					"import javax.persistence.Enumerated;\r\n" + 
					"import javax.persistence.GeneratedValue;\r\n" + 
					"import javax.persistence.GenerationType;\r\n" + 
					"import javax.persistence.Id;\r\n" + 
					"import javax.persistence.Table;\r\n" + 
					"\r\n" + 
					"import org.hibernate.validator.constraints.Length;\r\n" + 
					"\r\n" + 
					"import com.cqbxzc.go.member.dict.MemberLevelEnum;\r\n" + 
					"import com.cqbxzc.go.member.dict.MemberSexEnum;\r\n" + 
					"import com.cqbxzc.go.member.dict.MemberStatusEnum;\r\n" + 
					"import com.fasterxml.jackson.annotation.JsonIdentityInfo;\r\n" + 
					"import com.fasterxml.jackson.annotation.ObjectIdGenerators;\r\n" + 
					"\r\n" + 
					"import io.swagger.annotations.ApiModel;\r\n" + 
					"import io.swagger.annotations.ApiModelProperty;\r\n" + 
					"import live.jialing.data.domain.JpaEntity;\r\n" + 
					"import lombok.AllArgsConstructor;\r\n" + 
					"import lombok.Builder;\r\n" + 
					"import lombok.Data;\r\n" + 
					"import lombok.NoArgsConstructor;";
		}
	}
	
	public String getImports() {
		return imports;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote() {
		if(StringUtil.empty(note)) {
			note = "/**\r\n" + 
					" * %s\r\n" + 
					" *\r\n" + 
					" * @author %s\r\n" + 
					" */";
			note = String.format(note, tableNote, MapNote.author);
		}
	}
	
	public void setHeaders() {
		if(StringUtil.empty(headers) && !StringUtil.empty(javaName) && !StringUtil.empty(tableName)) {
			headers = "@ApiModel(value = \"%s\", description = \"%s\")\r\n" + 
					"@Data\r\n" + 
					"@AllArgsConstructor\r\n" + 
					"@NoArgsConstructor\r\n" + 
					"@Builder(toBuilder = true)\r\n" + 
					"@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = \"id\", scope = %s.class)\r\n" + 
					"@Entity\r\n" + 
					"@Table(name = \"%s\")";
			headers = String.format(headers, javaName,tableNote,simpleJavaName,tableName);
		}
	}
	
	public void setFields(String line) {
		if(StringUtil.empty(line)) {
			return;
		}
		if(line.startsWith("|") && line.endsWith("|")) {
			fieldLines++;
		}else {
			return;
		}
		if(fieldLines >= 3) {
			setField(line);
		}
	}
	private void setField(String line) {
		String[] fields = line.split("\\|",-1);
		if(MapField.ignoreNames.contains(fields[2])) {
			return;
		}
		MapField f = new MapField();
		f.setNameChn(fields[1]);
		f.setName(fields[2]);
		f.setNameJava(setJavaField(f.getName()));
		if(!StringUtil.empty(fields[3])) {
			String type = fields[3].toLowerCase();
			System.out.println("table="+tableName+",field="+f.getName());
			String type1 = MapEnum.getEnumType(tableName, f.getName());
			if(StringUtil.empty(type1)) {
				if(type.equals("varchar")) {
					type = "String";
				} else if(type.equals("bitint")){
					type = "Long";
				} else if(type.equals("bigint")){
					type = "Long";
				} else if(type.equals("timestamp")){
					type = "LocalDateTime";
				}
			}else {
				type = type1;
				f.setEnumType(true);
			}
			f.setType(type);
		}
		if(!StringUtil.empty(fields[4])) {
			f.setLength(Integer.valueOf(fields[4]));
		}
		if(!StringUtil.empty(fields[5]) && fields[5].equals("是")) {
			f.setPrimaryKey(true);
		}
		if(!StringUtil.empty(fields[6]) && fields[6].equals("否")) {
			f.setNullAble(false);
		}
		if(!StringUtil.empty(fields[7]) && fields[7].equals("是")) {
			f.setUnique(true);
		}
		if(!StringUtil.empty(fields[8])) {
			f.setDefaultValue(fields[8]);
		}
		if(!StringUtil.empty(fields[9])) {
			f.setRemark(fields[9]);
		}
		f.setHeader();
		
		this.fields.add(f);
	}
	
	private String setJavaField(String name) {
		String[] keys = name.split("_");
		if(keys.length>1) {
			StringBuffer sb = new StringBuffer();
			sb.append(keys[0]);
			for(int i=1;i<keys.length;i++) {
				keys[i] = StringUtil.firstUpperCase(keys[i]);
				sb.append(keys[i]);
			}
			return sb.toString();
		}else {
			return name;
		}
	}
	
	@Override
	public String getHeaders() {
		return headers;
	}
	@Override
	public List<MapField> getFields() {
		return fields;
	}
	@Override
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	@Override
	public String getPackageName() {
		this.packageName = "com.cqbxzc.file";
		return this.packageName;
	}
	@Override
	public String getSimpleJavaName() {
		return simpleJavaName;
	}
	@Override
	public boolean getGene() {
		return gene;
	}
	@Override
	public void setGene(boolean gene) {
		this.gene = gene;
	}
}
