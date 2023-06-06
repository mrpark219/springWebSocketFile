package me.park.springwebsocketfile.util;

import org.springframework.lang.Nullable;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class CommonUtil {

	public static boolean isEmpty(@Nullable Object obj) {
		if(obj == null) {
			return true;
		}

		if(obj instanceof Optional<?> optional) {
			return optional.isEmpty();
		}
		if(obj instanceof CharSequence charSequence) {
			return charSequence.length() == 0;
		}
		if(obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		if(obj instanceof Collection<?> collection) {
			return collection.isEmpty();
		}
		if(obj instanceof Map<?, ?> map) {
			return map.isEmpty();
		}

		return false;
	}

}
