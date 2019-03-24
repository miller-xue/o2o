package com.miller.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miller.o2o.common.AjaxResult;
import com.miller.o2o.dto.ImageHolder;
import com.miller.o2o.dto.ProductExecution;
import com.miller.o2o.entity.Product;
import com.miller.o2o.entity.ProductCategory;
import com.miller.o2o.entity.Shop;
import com.miller.o2o.enums.ProductStateEnum;
import com.miller.o2o.service.ProductCategoryService;
import com.miller.o2o.service.ProductService;
import com.miller.o2o.util.CodeUtil;
import com.miller.o2o.util.HttpContextUtils;
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
 *  商品控制层
 * @author Miller
 */
@Controller
@RequestMapping(value = "/shop/admin/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 根据参数获取指定商品列表
     * @param pageNum 当前页
     * @param pageSize 当前页容量
     * @param productCategoryId 商品分类id
     * @param productName 商品名称（模糊查询）
     * @param request
     * @return
     */
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

    /**
     * 根据id获取商品详情
     * @param id
     * @return
     */
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

    /**
     * 添加商品
     *
     * @param productJson 商品json字符串
     * @param thumbnail   商品缩略图
     * @param productImg  商品图列表
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResult add(@RequestParam("productStr") String productJson, MultipartFile thumbnail,
                          List<MultipartFile> productImg, HttpServletRequest request) throws IOException {
        // 1.参数校验，可以选则注解OR JSR303
        if (!CodeUtil.checkVerifyCode(request)) {
            return AjaxResult.error("输入了错误的验证码");
        }
        if (thumbnail != null && thumbnail.isEmpty()) {
            return AjaxResult.error("上传图片不能为空");
        }
        if (CollectionUtils.isEmpty(productImg) || productImg.size() > IMAGE_MAX_COUNT) {
            return AjaxResult.error("商品图片不能超过6张");
        }
        // 2.参数处理
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

    /**
     * 修改商品对象
     *
     * @param productJson  商品json字符串
     * @param thumbnail    缩略图
     * @param productImg   商品图片列表
     * @param request
     * @param statusChange 是否验证验证码（TODO 考虑是否抽出一个方法）
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public AjaxResult modify(@RequestParam("productStr") String productJson, MultipartFile thumbnail,
                             List<MultipartFile> productImg, HttpServletRequest request,
                             Boolean statusChange) throws IOException {
        // 1.参数校验
        if (statusChange != null && !statusChange) {
            if (!CodeUtil.checkVerifyCode(request)) {
                return AjaxResult.error("输入了错误的验证码");
            }
        }
        // 参数处理
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
