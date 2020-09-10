package com.qubyer.okhttputil.helper;

/**
 * 单个Bean专用类
 * @param <T>
 * @author jiangtianming
 */
public class ServerResponseBean<T> extends ServerResponse {

	private T data;

	@Override
	public String toString() {
		return "ServerResponseBean{" +
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
