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

    @RequestMapping(path = "/operation")
    public String operation() {
        return prefix + "operation";
    }

    @RequestMapping(path = "/list")
    public String list() {
        return prefix + "list";
    }

    @RequestMapping(path = "/manage")
    public String manage() {
        return prefix + "manage";
    }


    @RequestMapping(path = "/productCategory/list")
    public String productCategoryList() {
        return prefix + "/productCategory/list";
    }

    @RequestMapping(path = "/product/operation")
    public String productOperation() {
        return prefix + "/product/operation";
    }


    @RequestMapping(path = "/product/list")
    public String productList() {
        return prefix + "/product/list";
    }


}
