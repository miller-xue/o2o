package com.miller.o2o.web.shopadmin;

import com.miller.o2o.common.AjaxResult;
import com.miller.o2o.dto.ProductCategoryExecution;
import com.miller.o2o.entity.ProductCategory;
import com.miller.o2o.entity.Shop;
import com.miller.o2o.enums.ProductCategoryStateEnum;
import com.miller.o2o.service.ProductCategoryService;
import com.miller.o2o.util.HttpContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by miller on 2019/3/16
 *
 * @author Miller
 */
@Controller
@RequestMapping(value = "/shop/admin/product/category")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public AjaxResult list(Long shopId) {
        // 对shopId进行操作...
        Shop currentShop = (Shop) HttpContextUtils.getHttpSession().getAttribute("currentShop");
        //TODO
        if (shopId == null || shopId <= 0) {
            return AjaxResult.error().state(ProductCategoryStateEnum.INNER_ERROR);
        }
        return AjaxResult.success().put("list", productCategoryService.getList(shopId));
    }

    @ResponseBody
    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    public AjaxResult batchAdd(@RequestBody List<ProductCategory> productCategoryList) {
        ProductCategoryExecution execution = productCategoryService.batchAdd(productCategoryList);
        if (execution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
            return AjaxResult.success();
        }
        return AjaxResult.error(execution.getStateInfo());
    }

}
