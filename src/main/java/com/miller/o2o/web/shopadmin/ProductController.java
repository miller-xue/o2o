package com.miller.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miller.o2o.common.AjaxResult;
import com.miller.o2o.dto.ImageHolder;
import com.miller.o2o.dto.ProductExecution;
import com.miller.o2o.entity.Product;
import com.miller.o2o.entity.ProductCategory;
import com.miller.o2o.entity.Shop;
import com.miller.o2o.enums.ProductStateEnum;
import com.miller.o2o.enums.ShopStateEnum;
import com.miller.o2o.service.ProductCategoryService;
import com.miller.o2o.service.ProductService;
import com.miller.o2o.util.CodeUtil;
import com.miller.o2o.util.HttpContextUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by miller on 2019/3/19
 *
 * @author Miller
 */
@Controller
@RequestMapping(value = "/shop/admin/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @ResponseBody
    @RequestMapping(value = "/getListByShop", method = RequestMethod.GET)
    public AjaxResult getListByShop(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                    Long productCategoryId,
                                    String productName,
                                    HttpServletRequest request) {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop == null || currentShop.getId() == null) {
            return AjaxResult.error("empty shop");
        }
        Product condition = Product.builder().shop(currentShop).name(productName)
                .productCategory(ProductCategory.builder().id(productCategoryId).build()).build();
        ProductExecution pe = productService.getList(condition, pageNum, pageSize);
        return AjaxResult.success().put("count", pe.getCount()).put("productList", pe.getProductList());
    }


    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AjaxResult get(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            return AjaxResult.error("empty productId");
        }
        Product product = productService.getById(id);
        if (product == null) {
            return AjaxResult.error("empty productId");

        }
        Shop currentShop = (Shop) HttpContextUtils.getHttpSession().getAttribute("currentShop");
        return AjaxResult.success().put("product", product)
                .put("productCategoryList",productCategoryService.getList(currentShop.getId()));
    }


    private static final int IMAGE_MAX_COUNT = 6;

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public AjaxResult add(@RequestParam("productStr") String productJson, MultipartFile thumbnail,
                          List<MultipartFile> productImg, HttpServletRequest request) throws IOException {
        if (!CodeUtil.checkVerifyCode(request)) {
            return AjaxResult.error("输入了错误的验证码");
        }
        if (thumbnail != null && thumbnail.isEmpty()) {
            return AjaxResult.error("上传图片不能为空");
        }
        if (CollectionUtils.isEmpty(productImg) || productImg.size() > IMAGE_MAX_COUNT) {
            return AjaxResult.error("商品图片不能超过6张");
        }

        ObjectMapper mapper = new ObjectMapper();
        Product p = mapper.readValue(productJson, Product.class);
        List<ImageHolder> collect = productImg.stream().map(ImageHolder::of).collect(Collectors.toList());
        // 从session中获取当前操作的店铺id并赋值给product，减少对前端数据的依赖
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        p.setShop(currentShop);
        ProductExecution se = productService.add(p, ImageHolder.of(thumbnail), collect);
        if (se.getState() == ProductStateEnum.SUCCESS.getState()) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(se.getStateInfo());
        }
    }


    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public AjaxResult modify(@RequestParam("productStr") String productJson, MultipartFile thumbnail,
                             List<MultipartFile> productImg, HttpServletRequest request,
                             Boolean statusChange) throws IOException {
        if (statusChange != null && !statusChange) {
            if (!CodeUtil.checkVerifyCode(request)) {
                return AjaxResult.error("输入了错误的验证码");
            }
        }
        // 获取product对象
        ObjectMapper mapper = new ObjectMapper();
        Product p = mapper.readValue(productJson, Product.class);
        if (p == null || p.getId() == null) {
            return AjaxResult.error("empty product");
        }

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        p.setShop(currentShop);
        List<ImageHolder> collect = null;
        if (CollectionUtils.isNotEmpty(productImg)) {
            collect = productImg.stream().map(ImageHolder::of).collect(Collectors.toList());
        }
        ProductExecution se = productService.modify(p, ImageHolder.of(thumbnail), collect);
        if (se.getState() == ProductStateEnum.SUCCESS.getState()) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(se.getStateInfo());
        }
    }

}
