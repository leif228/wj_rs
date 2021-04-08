package com.wujie.apps.netty.server.send;

import com.wujie.apps.netty.pojo.BaseTask;
import com.wujie.apps.netty.protocol.WjProtocol;

import java.io.UnsupportedEncodingException;

public interface Sen_i {
    public WjProtocol generateWj(BaseTask baseTask) throws UnsupportedEncodingException;
}
