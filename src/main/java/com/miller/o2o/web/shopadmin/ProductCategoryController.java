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
import org.springframework.web.bind.annotation.*;

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
    public AjaxResult list() {
        // 对shopId进行操作...
        Shop currentShop = (Shop) HttpContextUtils.getHttpSession().getAttribute("currentShop");
        if (currentShop == null || currentShop.getId() <= 0) {
            return AjaxResult.error().state(ProductCategoryStateEnum.INNER_ERROR);
        }
        return AjaxResult.success().put("list", productCategoryService.getList(currentShop.getId()));
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

    @ResponseBody
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public AjaxResult delete(@PathVariable("id") Long id){
        Shop currentShop = (Shop) HttpContextUtils.getHttpSession().getAttribute("currentShop");
        if (id == null || id <= 0) {
            return AjaxResult.error("请至少选中一个商品类别");
        }

        ProductCategoryExecution se = productCategoryService.delete(id, currentShop.getId());
        if (se.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
            return AjaxResult.success();
        }
        return AjaxResult.error(se.getStateInfo());
    }

}
