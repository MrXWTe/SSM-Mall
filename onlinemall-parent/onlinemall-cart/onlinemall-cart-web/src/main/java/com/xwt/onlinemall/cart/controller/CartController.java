package com.xwt.onlinemall.cart.controller;

import com.xwt.onlinemall.pojo.TbItem;
import com.xwt.onlinemall.pojo.TbUser;
import com.xwt.onlinemall.service.ItemService;
import com.xwt.onlinemall.utils.CookieUtils;
import com.xwt.onlinemall.utils.E3Result;
import com.xwt.onlinemall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 购物车处理Controller
 * @Author: MrXu
 * @date: 2019/7/18
 */
@Controller
public class CartController {

    @Autowired
    private ItemService itemService;

    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    /**
     * 添加进购物车
     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId,
                          @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request,
                          HttpServletResponse response){
        // 判断用户是否登录，如果是登录状态，把购物车写入redis
        TbUser user = (TbUser)request.getAttribute("user");
        if(user != null){
            // 保存到服务端

            return "cartSuccess";
        }




        // 如果未登录，使用cookie
        // 从cookie中获取购物车列表
        List<TbItem> cartListFromCookie = getCartListFromCookie(request);

        // 判断商品列表在商品列表中是否存在
        boolean flag = false;
        for (TbItem item : cartListFromCookie) {
            // 如果存在商品数量相加
            if(item.getId() == itemId.longValue()){
                flag = true;
                item.setNum(item.getNum() + num);
            }
            // 跳出循环
            break;
        }
        if(!flag){
            // 如果不存在，根据商品ID查询商品信息，得到TbItem对象
            TbItem item = itemService.getItemById(itemId);
            // 设置商品数量
            item.setNum(num);
            // 取一张图片
            String image = item.getImage();
            if(StringUtils.isNoneBlank(image)){
                item.setImage(image.split(",")[0]);
            }
            // 把商品添加到商品列表
            cartListFromCookie.add(item);
        }
        // 写入cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartListFromCookie),
                COOKIE_CART_EXPIRE, true);

        // 返回添加成功页面
        return "cartSuccess";
    }


    /**
     * 从cookie中取购物列表的处理
     * @param request
     * @return
     */
    private List<TbItem> getCartListFromCookie(HttpServletRequest request){
        String cartJson = CookieUtils.getCookieValue(request, "cart", true);
        if(StringUtils.isBlank(cartJson)){
            return new ArrayList<>();
        }
        // 把Json转换成对象列表
        return JsonUtils.jsonToList(cartJson, TbItem.class);
    }


    /**
     * 展示购物车列表页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, Model model) {
        //取购物车商品列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //传递给页面
        model.addAttribute("cartList", cartList);
        return "cart";
    }


    /**
     * 异步更新商品数量，在购物车结算页面添加商品数量时，需要更改cookie的值
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateNum(@PathVariable Long itemId, @PathVariable Integer num,
                              HttpServletRequest request, HttpServletResponse response) {


        // 1、接收两个参数
        // 2、从cookie中取商品列表
        List<TbItem> cartList = getCartListFromCookie(request);
        // 3、遍历商品列表找到对应商品
        for (TbItem tbItem : cartList) {
            if (tbItem.getId() == itemId.longValue()) {
                // 4、更新商品数量
                tbItem.setNum(num);
            }
        }
        // 5、把商品列表写入cookie。
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        // 6、响应e3Result。Json数据。
        return E3Result.ok();
    }


    /**
     * 删除购物车的列表
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request,
                                 HttpServletResponse response) {
        // 1、从url中取商品id
        // 2、从cookie中取购物车商品列表
        List<TbItem> cartList = getCartListFromCookie(request);
        // 3、遍历列表找到对应的商品
        for (TbItem tbItem : cartList) {
            if (tbItem.getId() == itemId.longValue()) {
                // 4、删除商品。
                cartList.remove(tbItem);
                break;
            }
        }
        // 5、把商品列表写入cookie。
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        // 6、返回逻辑视图：在逻辑视图中做redirect跳转。
        return "redirect:/cart/cart.html";
    }


}
