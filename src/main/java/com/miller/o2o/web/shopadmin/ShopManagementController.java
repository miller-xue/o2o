package com.miller.o2o.web.shopadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miller.o2o.dto.AjaxResult;
import com.miller.o2o.dto.ShopExecution;
import com.miller.o2o.entity.Area;
import com.miller.o2o.entity.PersonInfo;
import com.miller.o2o.entity.Shop;
import com.miller.o2o.entity.ShopCategory;
import com.miller.o2o.enums.ShopStateEnum;
import com.miller.o2o.service.AreaService;
import com.miller.o2o.service.ShopCategoryService;
import com.miller.o2o.service.ShopService;
import com.miller.o2o.util.CodeUtil;
import com.miller.o2o.util.HttpServletRequestUtil;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by miller on 2019/2/24
 * 引入了全局异常处理器
 * @author Miller
 */
@Controller
@RequestMapping("/shop/admin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private HttpServletRequest request;


    @ResponseBody
    @RequestMapping(value = "/shop/{shopId}", method = RequestMethod.GET)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AjaxResult getShopById(@PathVariable Long shopId) {
        AjaxResult result;
        if (shopId != null && shopId > -1) {
            Shop shop = shopService.getById(shopId);
            List<Area> areaList = areaService.getList();
            result = AjaxResult.success()
                    .put("shop", shop)
                    .put("areaList", areaList);
        } else {
            result = AjaxResult.error("empty shopId");
        }
        return result;
    }


    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getShopInitInfo() {
        return AjaxResult.success()
                .put("shopCategoryList", shopCategoryService.getList(new ShopCategory()))
                .put("areaList", areaService.getList());
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    //TODO 代码重构
    public Map<String, Object> registerShop(@RequestParam("shopImg") MultipartFile file) {
        Map<String, Object> modelMap = new HashMap<>(2);
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //1.接收并转换相应的参照，包括店铺信息和图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //TODO 上传重构
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }

        //2.注册店铺
        if (shop != null && shopImg != null) {
            // TODO 设置管理员（在线登陆用户）
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                se = shopService.add(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            if (se.getState() == ShopStateEnum.CHECK.getState()) {
                modelMap.put("success", true);
                // 把能管理的店铺放在session中
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                if (shopList == null) {
                    shopList = new ArrayList<>();
                }
                shopList.add(se.getShop());
                request.getSession().setAttribute("shopList", shopList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            //3.返回结果
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
    }

    public AjaxResult register(Shop shop, MultipartFile file) {
        return null;
    }

    /**
     * 修改店铺
     * @param shop
     * @param file
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult modify(Shop shop, MultipartFile file) throws IOException {

        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            return AjaxResult.error("输入了错误的验证码");
        }
        // 仓库修改
        if (shop != null && shop.getId() != null) {
            InputStream in = null;
            String imgName = null;
            if (file != null && file.getSize() > 0) {
                in = file.getInputStream();
                imgName = file.getOriginalFilename();
            }
            ShopExecution se = shopService.modifyShop(shop, in, imgName);
            if (se.getState() == ShopStateEnum.CHECK.getState()) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error(se.getStateInfo());
            }
        }
        return AjaxResult.error("empty shopId");
    }

//    private static void inputStreamToFile(InputStream ins, File file) {
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            while ((bytesRead = ins.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("调用inputStreamToFile产生异常" + e.getMessage());
//        }finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                if (ins != null) {
//                    ins.close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException("inputStreamToFile关闭Io产生异常" + e.getMessage());
//            }
//        }
//
//    }
}
