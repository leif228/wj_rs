package com.wujie.tc.netty.pojo;


import com.wujie.tc.netty.protocol.WjProtocol;

import java.io.UnsupportedEncodingException;

public interface Sen_i {
    public WjProtocol generateWj(BaseTask baseTask) throws UnsupportedEncodingException;
}
