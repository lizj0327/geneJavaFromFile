package com.aa.bb.md;

import java.util.HashMap;
import java.util.Map;

public class MapEnum {

	static Map<String,Map<String,String>> enums = new HashMap<>();
	
	static {
		Map<String,String> memberEnums = new HashMap<>();
		memberEnums.put("sex", "MemberSexEnum");
		memberEnums.put("status", "MemberStatusEnum");
		memberEnums.put("level", "MemberLevelEnum");
		enums.put("member", memberEnums);
	}
	
	public static String getEnumType(String entity,String field) {
		Map<String,String> entityMap = enums.get(entity);
		if(entityMap!=null) {
			return entityMap.get(field);
		}
		return null;
	}
}
