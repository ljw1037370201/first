package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbuserMapper;
import com.taotao.pojo.Tbuser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbuserMapper tbuserMapper;
    @Override
    public TaotaoResult checkData(String param, int type) {
        if(type == 1){
            Tbuser tbuser = tbuserMapper.getUserByUsername(param);
            if(tbuser != null){
                return TaotaoResult.ok(false);
            }
        }else if (type == 2){
            Tbuser tbuser = tbuserMapper.getUserByPhone(param);
            if (tbuser != null){
                return TaotaoResult.ok(false);
            }
        }else if (type == 3){
            Tbuser tbuser = tbuserMapper.getUserByEmail(param);
            if (tbuser != null){
                return TaotaoResult.ok(false);
            }
        }else{
            return TaotaoResult.build(500,"传入的数据类型不合法");
        }
        return TaotaoResult.ok(true);
    }


    @Override
    public TaotaoResult createUser(Tbuser tbuser) {
        //校验账号是否为空
        if(StringUtils.isBlank(tbuser.getUsername())){
            return TaotaoResult.build(400,"用户名不能为空");
        }else {
            //校验账号是否被使用
            TaotaoResult result = checkData(tbuser.getUsername(), 1);
            if(!(boolean)result.getData()){
                return TaotaoResult.build(400,"该用户名已经被使用");
            }
        }
        //校验密码不能为空
        if(StringUtils.isBlank(tbuser.getPassword())){
            return TaotaoResult.build(400,"密码不能为空");
        }
        //校验手机号码是否为空
        if(StringUtils.isBlank(tbuser.getPhone())){
                return TaotaoResult.build(400,"该手机号码不能为空");
        }else {
            //校验手机号码是否被使用
            TaotaoResult result = checkData(tbuser.getPhone(), 2);
            if(!(boolean)result.getData()){
                return TaotaoResult.build(400,"该手机号码已经被使用");
            }
        }
        //手动生成日期
        Date date = new Date();
        tbuser.setCreated(date);
        tbuser.setUpdated(date);
        //密码md5加密
        String md5Password = DigestUtils.md5DigestAsHex(tbuser.getPassword().getBytes());
        tbuser.setPassword(md5Password);
        tbuserMapper.insertUser(tbuser);
        return TaotaoResult.ok();
    }
}
