package com.jkk.demo.util;

import com.jkk.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class BeanUtil {
	public static void copyNullProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target, getNoNullProperties(target));
	}

	private static String[] getNoNullProperties(Object target) {
		BeanWrapper srcBean = new BeanWrapperImpl(target);
		PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
		Set<String> noEmptyName = new HashSet<>();
		for (PropertyDescriptor p : pds) {
			Object value = srcBean.getPropertyValue(p.getName());
			if (value != null) noEmptyName.add(p.getName());
		}
		String[] result = new String[noEmptyName.size()];
		return noEmptyName.toArray(result);
	}
}
