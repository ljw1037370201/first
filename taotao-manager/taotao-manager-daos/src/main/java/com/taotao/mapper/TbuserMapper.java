package com.taotao.mapper;


import com.taotao.pojo.Tbuser;

public interface TbuserMapper {
    /**
     * 根据用户名查询数据库
     * @return
     */
   Tbuser getUserByUsername(String username);

    /**
     * 根据手机号码查询数据库
     * @return
     */
   Tbuser getUserByPhone(String phone);

    /**
     * 根据邮箱查询数据库
     * @return
     */
   Tbuser getUserByEmail(String email);

    /**
     * 将用户信息添加到数据库
     * @param tbuser
     */
   void insertUser(Tbuser tbuser);

    /**
     * 根据账号和密码查询数据库返回用户信息 注意密码是MD5加密
     * @param username 账号
     * @param password 密码
     * @return 如果为null说明用户不存在
     */
   Tbuser selectUser(String username,String password);
}