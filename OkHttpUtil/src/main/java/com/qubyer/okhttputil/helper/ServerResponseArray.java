package com.qubyer.okhttputil.helper;

import java.util.List;

/**
 * 集合数据专用类
 * @param <T>
 * @author jiangtianming
 */
public class ServerResponseArray<T> extends ServerResponse {

	private List<T> data;

	@Override
	public String toString() {
		return "ServerResponseArray{" +
				"code=" + getCode() +
				", message='" + getMessage() + '\'' +
				", time=" + getTime() +
				", result=" +  (data != null ? data.toString():"") +
				'}';
	}

	@Override
	public Object getResult() {
		return data;
	}
}
