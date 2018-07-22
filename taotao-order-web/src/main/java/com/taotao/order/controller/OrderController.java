package com.taotao.order.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.Tbitem;
import com.taotao.pojo.Tbuser;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private OrderService orderService;
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

    @RequestMapping("/order/create")
    public String createOrder(OrderInfo orderInfo, HttpServletRequest request){
        //走到这里是先经过了拦截器
        Tbuser tbuser = (Tbuser) request.getAttribute("user");
        //订单表中用户id
        orderInfo.setUserId(tbuser.getId());
        //订单表中用户昵称
        orderInfo.setBuyerNick(tbuser.getUsername());
        //插入订单表+订单与商品的关联表+订单与地址关联表
        TaotaoResult result = orderService.createOrder(orderInfo);
        //取订单号
        String orderId = result.getData().toString();
        // a)需要Service返回订单
        request.setAttribute("orderId",orderId);
        request.setAttribute("payment",orderInfo.getPayment());
        // b)当前日期加三天。
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        //正常的情况是物流返回给我们的
        request.setAttribute("date",dateTime.toString("yyyy-MM-dd"));
        return "success";
    }
}
