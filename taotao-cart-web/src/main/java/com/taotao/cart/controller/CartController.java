package com.taotao.cart.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.Tbitem;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ItemService itemService;
    @Value("${TT_CART}")
    private String TT_CART;
    @Value("${CART_EXPIRE}")
    private Integer CART_EXPIRE;
    @RequestMapping("/add/{itemId}")
    public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response){
        List<Tbitem> list = getCartList(request);
        boolean flag = false;
        for (Tbitem tbitem : list){
            //他们是对象类型的整型  吧对象类型的整型变成 基本数据类型
            if (tbitem.getId() == itemId.longValue()){
                //注意 在数据库中num代表的是库存，这里我们用这个属性来保存数量也是行的
                tbitem.setNum(tbitem.getNum()+num);
                flag = true;
                break;
            }
        }
        //代表没有在cookie里面找到相同的商品
        if (!flag){
            Tbitem tbitem = itemService.getItemById(itemId);
            //吧页面想要购买的数量添加进去
            tbitem.setNum(num);
            //在添加商品的时候 图片是有多张的 他们之间是以,号分割的
            String images = tbitem.getImage();
            if (StringUtils.isNotBlank(images)){
                String image = images.split(",")[0];
                tbitem.setImage(image);
            }
            list.add(tbitem);
        }
        //集合里面一定有值了 并且把他存入我们的cookie里面
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(list),CART_EXPIRE,true);
        //跳转到添加成功页面
        return "cartSuccess";
    }
    //http://localhost:8089/cart/cart.html
    @RequestMapping("/cart")
    public String showCartList(HttpServletRequest request){
        List<Tbitem> list = getCartList(request);
        request.setAttribute("list",list);
        return "cart";
    }
///update/num/{itemId}/{num}
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateNum(@PathVariable Long itemId,@PathVariable Integer num,
                                  HttpServletRequest request,HttpServletResponse response){
        //从cookie里面取出来商品集合
        List<Tbitem> list = getCartList(request);
        //遍历商品集合
        for (Tbitem tbitem : list) {
            if(tbitem.getId() == itemId.longValue()){
                tbitem.setNum(num);
                break;
            }
        }
        //集合里面一定有值了 并且把他存入我们的cookie里面
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(list),CART_EXPIRE,true);
        return TaotaoResult.ok();
    }
    //http://localhost:8089/cart/delete/153167606750022.html
    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
        List<Tbitem> cartList = getCartList(request);
        for (int i = 0;i<cartList.size();i++) {
            Tbitem tbitem = cartList.get(i);
            if (tbitem.getId() == itemId.longValue()){
                cartList.remove(tbitem);
                break;
            }
        }
        //集合里面一定有值了 并且把他存入我们的cookie里面
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(cartList),CART_EXPIRE,true);
        return "cart";
    }

    //用于查询cookie里面是否相同的商品
     private List<Tbitem> getCartList(HttpServletRequest request){
        //默认使用UTF-8编码格式
         String json = CookieUtils.getCookieValue(request, TT_CART, true);
         if (StringUtils.isBlank(json)){
             //从cookie里面取数据，取不到直接返回空集合
             return new ArrayList<Tbitem>();
         }
         List<Tbitem> list = JsonUtils.jsonToList(json, Tbitem.class);
         return list;
     }

}
