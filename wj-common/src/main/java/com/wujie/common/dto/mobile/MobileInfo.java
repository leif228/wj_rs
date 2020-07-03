/**
 * Copyright (C), 2016-2019, 广州航运电子商务有限公司
 * FileName: MobileInfo
 * Author:   Guoqiang
 * Date:     2019/3/6 下午4:17
 * Description: 安装软件基本信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.dto.mobile;

import java.io.Serializable;

/**
 * 安装软件基本信息
 *
 * @author Guoqiang
 * @since 2019/3/6
 * @version 1.0.0
 */
public class MobileInfo implements Serializable {
    private String type;//IOSorAndroid
    private String version;//软件版本
    private String source;//来源（各市场key）
    private String optVersion;//操作系统版本(8.0)
    private String optName;//厂商 (Xiaomi)
    private String phoneName;//手机类型（MI 5X ）

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOptVersion() {
        return optVersion;
    }

    public void setOptVersion(String optVersion) {
        this.optVersion = optVersion;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }
}
