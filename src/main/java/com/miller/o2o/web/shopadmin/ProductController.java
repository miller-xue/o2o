package com.miller.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miller.o2o.common.AjaxResult;
import com.miller.o2o.dto.ImageHolder;
import com.miller.o2o.dto.ProductExecution;
import com.miller.o2o.entity.Product;
import com.miller.o2o.enums.ShopStateEnum;
import com.miller.o2o.service.ProductService;
import com.miller.o2o.util.CodeUtil;
import com.miller.o2o.util.HttpContextUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    private static final int IMAGE_MAX_COUNT = 6;

    @ResponseBody
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public AjaxResult add(@RequestParam("product") String productJson,
                          MultipartFile product,
                          List<MultipartFile> productImg) throws IOException {
        if (!CodeUtil.checkVerifyCode(HttpContextUtils.getHttpServletRequest())) {
            return AjaxResult.error("输入了错误的验证码");
        }
        if (CollectionUtils.isNotEmpty(productImg) && productImg.size() > IMAGE_MAX_COUNT) {
            return AjaxResult.error("商品图片不能超过6张");
        }

        ObjectMapper mapper = new ObjectMapper();
        Product p = mapper.readValue(productJson, Product.class);

        List<ImageHolder> collect = productImg.stream().map(ImageHolder::of).collect(Collectors.toList());

        ProductExecution se = productService.add(p, ImageHolder.of(product), collect);
        if (se.getState() == ShopStateEnum.CHECK.getState()) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(se.getStateInfo());
        }
    }
}
