package com.wujie.pcclient.netty.client.send;

import com.wujie.pcclient.netty.pojo.BaseTask;
import com.wujie.pcclient.netty.protocol.WjProtocol;

import java.io.UnsupportedEncodingException;

public interface Sen_i {
    public WjProtocol generateWj(BaseTask baseTask) throws UnsupportedEncodingException;
}
