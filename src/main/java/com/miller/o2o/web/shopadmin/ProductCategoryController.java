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
 *  商品分类管理
 * @author Miller
 */
@Controller
@RequestMapping(value = "/shop/admin/product/category")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 根据当前店铺查找当前店铺下所有商品分类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public AjaxResult list() {
        Shop currentShop = (Shop) HttpContextUtils.getHttpSession().getAttribute("currentShop");
        if (currentShop == null || currentShop.getId() <= 0) {
            return AjaxResult.error().state(ProductCategoryStateEnum.INNER_ERROR);
        }
        return AjaxResult.success().put("list", productCategoryService.getList(currentShop.getId()));
    }

    /**
     * 批量添加商品分类
     * @param productCategoryList
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    public AjaxResult batchAdd(@RequestBody List<ProductCategory> productCategoryList) {
        Shop currentShop = (Shop) HttpContextUtils.getHttpSession().getAttribute("currentShop");
        if (currentShop == null || currentShop.getId() <= 0) {
            return AjaxResult.error().state(ProductCategoryStateEnum.INNER_ERROR);
        }
        // 填充数据
        productCategoryList.stream().forEach((ProductCategory p) -> p.setShopId(currentShop.getId()));
        ProductCategoryExecution execution = productCategoryService.batchAdd(productCategoryList);
        if (execution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
            return AjaxResult.success();
        }
        return AjaxResult.error(execution.getStateInfo());
    }

    /**
     * 根据id删除商品分类
     * @param id
     * @return
     */
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
