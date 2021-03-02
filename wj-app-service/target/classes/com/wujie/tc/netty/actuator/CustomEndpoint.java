package com.wujie.tc.netty.actuator;


import com.wujie.tc.netty.server.ChannelManager;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Endpoint(id = "custom-endpoint")
public class CustomEndpoint {
    @Autowired
    private ChannelManager channelManager;

    @ReadOperation
    public Map<String, String> endpoint() {
        Map<String, String> result  = new HashMap<>();
        Map<String, Channel> map = channelManager.deviceChannels;
        for(String key:map.keySet()){
            result.put(key,map.get(key).attr(ChannelManager.deviceInfoVoAttr).get().toString());
        }

        return result;
    }
}
