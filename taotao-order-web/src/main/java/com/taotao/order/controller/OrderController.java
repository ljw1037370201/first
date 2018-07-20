package com.taotao.order.controller;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.Tbitem;
import com.taotao.pojo.Tbuser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Value("${TT_CART}")
    private String TT_CART;
    /**
     * 展示订单确认页面
     * @param request
     * @return
     */
    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request){
        Tbuser user = (Tbuser) request.getAttribute("user");
        System.out.println(user);
        List<Tbitem> cartList = getCartList(request);
        request.setAttribute("cartList",cartList);
        return "order-cart";
    }

    /**
     * 从cookie中取购物车列表
     * @param request 请求
     * @return 商品集合
     */
    private List<Tbitem> getCartList(HttpServletRequest request){
        //默认使用UTF-8编码格式
        String json = CookieUtils.getCookieValue(request, TT_CART, true);
        if (StringUtils.isBlank(json)){
            return new ArrayList<Tbitem>();
        }
        List<Tbitem> list = JsonUtils.jsonToList(json, Tbitem.class);
        return list;

    }
}
