package com.aa.bb.md;

public class StringUtil {

	public static boolean empty(String line) {
		if(line == null || line.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	public static String firstUpperCase(String str) {
		char[] chars = str.toCharArray();
		if(chars[0] >= 97 && chars[0]<=122) {
			chars[0] = (char)(chars[0] - 32);
		}
		return new String(chars);
	}
	
	/**
	 * 将驼峰式命名的字符串转换为下划线小写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->hello_world
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线小写方式命名的字符串
	 */
	public static String camel2UnderScore(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			result.append(name.substring(0, 1).toLowerCase());
			// 循环处理其余字符
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				result.append(s.toLowerCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将下划线小写方式命名的字符串转换为驼峰式。如果转换前的下划线小写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：hello_world->HelloWorld
	 * 
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String underScore2Camel(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}
}
