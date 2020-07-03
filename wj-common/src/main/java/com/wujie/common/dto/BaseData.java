/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: BaseData
 * Author:   Guoqiang
 * Date:     2018/11/23 下午1:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 *
 *
 * @author Guoqiang
 * @since 2018/11/23
 * @version 1.0.0
 */
public class BaseData implements Serializable {

    private static final long serialVersionUID = -1L;

    public BaseData() {
    }

    @JsonIgnore
    public String getJsonString() {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(this);

        return jsonStr;
    }

    @JsonIgnore
    public JSONObject getJson() throws JSONException {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(this);

        JSONObject json = new JSONObject(jsonStr);

        return json;
    }

    @JsonIgnore
    public Map<String, Object> getMap() {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(this);

        Map<String, Object> map = gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
        }.getType());

        return map;
    }
}
