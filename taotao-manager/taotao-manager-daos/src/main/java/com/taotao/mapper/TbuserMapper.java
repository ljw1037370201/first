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
}