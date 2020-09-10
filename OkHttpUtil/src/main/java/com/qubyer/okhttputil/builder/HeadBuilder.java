package com.qubyer.okhttputil.builder;

import com.qubyer.okhttputil.OkHttpUtils;
import com.qubyer.okhttputil.request.OtherRequest;
import com.qubyer.okhttputil.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
