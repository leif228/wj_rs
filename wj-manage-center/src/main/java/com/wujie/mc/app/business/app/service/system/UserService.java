package com.wujie.mc.app.business.app.service.system;

import com.wujie.common.base.ApiResult;
import com.wujie.mc.app.business.vo.UserDetailsVo;

public interface UserService {

    ApiResult userLogin(String username, String password);

    ApiResult userRegist(String username, String password, String idcard, String phone, String userSelected, Integer pSort, Integer cSort, Integer aSort, Integer sSort);
}
