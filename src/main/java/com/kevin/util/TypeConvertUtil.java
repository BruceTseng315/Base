package com.kevin.util;


import org.springframework.format.datetime.DateFormatter;
import org.springframework.validation.DataBinder;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TypeConvertUtil {
	
	
	static DataBinder converter;
	/**
	 * 类型转化
	 * @param value
	 * @param requiredType
	 */
	public static <T> T convertIfNecessary(Object value, Class<T> requiredType) {
		
		
		DataBinder convert=getConverter();
	
		return convert.convertIfNecessary(value,requiredType);
	}
	
	
	
	private static DataBinder getConverter() {
		
		if(converter==null) {
			converter=new DataBinder(null);
			/**
			 * 添加的时候一定要注意顺序
			 */
			converter.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
			converter.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
			converter.registerCustomEditor(String.class, new Date2String());
		}
		return converter;
	}
	
	static class Date2String  extends PropertyEditorSupport{

		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
		
			super.setValue(text);
		}
		
		
	}
	

	public static void main(String[] args) {
		
			System.out.println(TypeConvertUtil.convertIfNecessary(new Date(), String.class));
		
	}
	
}
