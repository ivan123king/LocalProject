package com.lw.house.user.common;

import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.common.collect.ImmutableMap;
import com.lw.house.user.exception.IllegalParamsException;
import com.lw.house.user.exception.WithTypeException;

public class Exception2CodeRepo {

	private static final ImmutableMap<Object, RestCode> map = ImmutableMap
			.<Object, RestCode> builder()
			.put(IllegalParamsException.Type.WRONG_PAGE_NUM,
					RestCode.WRONG_PAGE)
			.put(IllegalStateException.class, RestCode.UNKNOWN_ERROR).build();

	public static RestCode getCode(Throwable throwable) {
		if (throwable == null) {
			return RestCode.UNKNOWN_ERROR;
		}
		Object target = throwable;
		if (throwable instanceof WithTypeException) {
			Object type = getType(throwable);
			if (type != null) {
				target = type;
			}
		}
		RestCode restCode = map.get(target);
		if (restCode != null) {
			return restCode;
		}
		//获取上层异常
		Throwable rootCause = ExceptionUtils.getRootCause(throwable);
		if (rootCause != null) {
			return getCode(rootCause);
		}
		return RestCode.UNKNOWN_ERROR;
	}

	private static Object getType(Throwable throwable) {
		try {
			return FieldUtils.readDeclaredField(throwable, "type", true);
		} catch (Exception e) {
			return null;
		}
	}

}
