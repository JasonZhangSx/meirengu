package com.meirengu.utils;

import java.util.List;

/**
 * @author huangxiaoliangyfx
 *
 */
public class ListUtil {

	public ListUtil() {
		super();
	}

	/**
	 * list拆分
	 * @param list
	 * @param size
	 * @return
	 */
	public static List<?>[] splitList(List<?> list, int size) {  
	    int total = list.size();
	    int count = total % size == 0 ? total / size : total / size + 1;
	    List<?>[] result = new List[count];
	    for(int i = 0; i < count; i++) {
	        int start = i * size;
	        int end = start + size > total ? total : start + size;
	        List<?> subList = list.subList(start, end);
	        result[i] = subList;
	    }  
	    return result;  
	}

}