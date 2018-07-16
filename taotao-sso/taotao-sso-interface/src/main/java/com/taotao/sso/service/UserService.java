package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.Tbuser;

public interface UserService {
    /**
     * 检查数据是否可用
     * @param param 需要检查的数据
     * @param type 检查的类型1.账号2.手机号码3.邮箱
     * @return {satus:200,msg:ok,data:"true|false"}true表示数据可用
     */
    TaotaoResult checkData(String param,int type);

    /**
     * 用户注册 注意要做数据校验
     * @param tbuser 需要注册的用户
     * @return {status：200,msg:"ok",data:""} 如果是status为400则表示注册不成功并且返回 msg:注册失败. 请校验数据后请再提交数据.
     */
    TaotaoResult createUser(Tbuser tbuser);
}
