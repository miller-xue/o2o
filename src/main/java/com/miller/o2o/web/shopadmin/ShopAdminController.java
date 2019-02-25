package com.miller.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by miller on 2019/2/24
 *
 * @author Miller
 */
@Controller
@RequestMapping(value = "/shop",method = RequestMethod.GET)
public class ShopAdminController {
    String prefix = "shop/";

    @RequestMapping(path = "/shopoperation")
    public String shopOperation() {
        return prefix + "shopoperation";
    }

}
