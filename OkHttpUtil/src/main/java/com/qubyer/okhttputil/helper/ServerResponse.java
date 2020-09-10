package com.qubyer.okhttputil.helper;

import java.io.Serializable;
import java.util.Map;

/**
 * 服务器响应界面类
 * @author jiangtianming
 * @date 2016/6/27 16:58
 */
public abstract class ServerResponse implements Serializable {
    /**
     * 响应状态
     */
    private int code;
    /**
     * 响应描述
     */
    private String msg;
    /**
     * 服务器响应时间戳
     */
    private long time;
    /**
     * 请求标记TAG MAP
     */
    private Map<String, Object> requestTagMap;

    /**
     * 创建错误实体
     * @param status
     * @param message
     * @return
     */
    public static ServerResponse createErrorResponse(int status, String message){
        ServerResponse response = new ServerResponse() {
            @Override
            public Object getResult() {
                return null;
            }
        };
        response.code = status;
        response.msg = message;
        response.time = System.currentTimeMillis();
        return response;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "code=" + code +
                ", message='" + msg + '\'' +
                ", time=" + time +
                '}';
    }

    public void setRequestTagMap(Map<String, Object> requestTagMap) {
        this.requestTagMap = requestTagMap;
    }

    public Map<String, Object> getRequestTagMap() {
        return requestTagMap;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public long getTime() {
        return time;
    }

    public abstract Object getResult();
}
