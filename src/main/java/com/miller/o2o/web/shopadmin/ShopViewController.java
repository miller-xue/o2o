package com.miller.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by miller on 2019/2/24
 * 店铺视图控制器
 * @author Miller
 */
@Controller
@RequestMapping(value = "/shopAdmin",method = RequestMethod.GET)
public class ShopViewController {
    String prefix = "shop/";

    @RequestMapping(path = "/shopOperation")
    public String shopOperation() {
        return prefix + "shopOperation";
    }

    @RequestMapping(path = "/shopList")
    public String shopList() {
        return prefix + "shopList";
    }

    @RequestMapping(path = "/shopManagement")
    public String shopManagement() {
        return prefix + "shopManage";
    }

}
