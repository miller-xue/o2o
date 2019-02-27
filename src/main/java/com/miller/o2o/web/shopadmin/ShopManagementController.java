package com.miller.o2o.web.shopadmin;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miller.o2o.dto.ShopExecution;
import com.miller.o2o.entity.PersonInfo;
import com.miller.o2o.entity.Shop;
import com.miller.o2o.entity.ShopCategory;
import com.miller.o2o.enums.ShopStateEnum;
import com.miller.o2o.service.AreaService;
import com.miller.o2o.service.ShopCategoryService;
import com.miller.o2o.service.ShopService;
import com.miller.o2o.util.CodeUtil;
import com.miller.o2o.util.HttpServletRequestUtil;
import com.miller.o2o.util.ImageUtil;
import com.miller.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miller on 2019/2/24
 *
 * @author Miller
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;


    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>(3);
        try {
            modelMap.put("shopCategoryList", shopCategoryService.getList(new ShopCategory()));
            modelMap.put("areaList", areaService.getList());
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    //TODO 代码重构
    public Map<String, Object> registerShop(HttpServletRequest request,@RequestParam("shopImg") MultipartFile file) {
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
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }

        //2.注册店铺
        if (shop != null && shopImg != null) {
            // TODO 设置管理员（在线登陆用户）
            PersonInfo build = PersonInfo.builder().id(1).build();
            shop.setOwner(build);
            ShopExecution se = null;
            try {
                se = shopService.add(shop, shopImg.getInputStream(),shopImg.getOriginalFilename());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }//TODO catch 后下一步执行的代码
            if (se.getState() == ShopStateEnum.CHECK.getState()) {
                modelMap.put("success", true);
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            //3.返回结果
            return modelMap;
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
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
