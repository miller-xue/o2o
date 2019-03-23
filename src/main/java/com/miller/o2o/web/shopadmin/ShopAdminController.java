package com.miller.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miller.o2o.common.AjaxResult;
import com.miller.o2o.dto.ImageHolder;
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
import com.miller.o2o.util.HttpContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miller on 2019/2/24
 * 引入了全局异常处理器
 * @author Miller
 */
@Controller
@RequestMapping("/admin/shop")
public class ShopAdminController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    /**
     * 查询一个店铺
     * @param shopId 店铺顶下
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{shopId}", method = RequestMethod.GET)
    public AjaxResult getShopById(@PathVariable Long shopId) {
        AjaxResult result;
        if (shopId != null && shopId > -1) {
            Shop shop = shopService.getById(shopId);
            if (shop == null) {
                result = AjaxResult.error("empty shopId");
            }else {
                List<Area> areaList = areaService.getList();
                result = AjaxResult.success()
                        .put("shop", shop)
                        .put("areaList", areaList);
            }
        } else {
            result = AjaxResult.error("empty shopId");
        }
        return result;
    }

    /**
     * 查询店铺列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public AjaxResult getShopList() {
        // TODO 列表需要 当前用户id
        PersonInfo currentUser = HttpContextUtils.getCurrentUser();
        ShopExecution se = shopService.getShopList(Shop.builder().owner(PersonInfo.builder().id(1L).build()).build(), 1, 100);
        return AjaxResult.success().put("shopList", se.getShopList()).put("user", currentUser);
    }

    /**
     * 没看懂是个啥方法。。
     * @param shopId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getShopManagementInfo",method = RequestMethod.GET)
    public AjaxResult getShopManagementInfo(Long shopId) {
        Shop currentShop = (Shop) HttpContextUtils.getHttpSession().getAttribute("currentShop");

        if (shopId == null || shopId <= 0) {
            if (currentShop == null) {
                return AjaxResult.redirect(true).put("url", "/shopAdmin/shopList");
            }
            return AjaxResult.redirect(false).put("shopId", currentShop.getId());
        }else {
            // 判断当前登陆人是否有传入商铺id权限，
            Shop byId = shopService.getById(shopId);
            if (byId != null) {
                HttpContextUtils.getHttpSession().setAttribute("currentShop", byId);
                return AjaxResult.redirect(false);
            }else {
                return AjaxResult.redirect(true).put("url", "/shopAdmin/shopList");
            }
        }

//        if (shopId == null || shopId <= 0) {
//            Shop currentShop = (Shop) HttpContextUtils.getHttpSession().getAttribute("currentShop");
//            if (currentShop == null) {
//                return AjaxResult.redirect(true).put("url", "/shopAdmin/shopList");
//            }else {
//                return AjaxResult.redirect(false).put("shopId", currentShop.getId());
//            }
//        }else {
//            Shop shop = Shop.builder().id(shopId).build();
//            HttpContextUtils.getHttpSession().setAttribute("currentShop", shop);
//            return AjaxResult.redirect(false);
//        }
    }


    /**
     * 注册店铺初始化参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getShopInit", method = RequestMethod.GET)
    public AjaxResult getShopInitInfo() {
        return AjaxResult.success()
                .put("shopCategoryList", shopCategoryService.getList(new ShopCategory()))
                .put("areaList", areaService.getList());
    }



    /*@RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerShop(@RequestParam("shopImgFile") MultipartFile shopImgFile) {
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
        CommonsMultipartFile shopImgFile = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImgFile");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }

        //2.注册店铺
        if (shop != null && shopImgFile != null) {
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                se = shopService.add(shop, shopImgFile.getInputStream(), shopImgFile.getOriginalFilename());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
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
    }*/

    /**
     * 注册店铺
     *
     * @param shopJson    店铺对象Json字符串
     * @param shopImgFile 图片
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public AjaxResult register(@RequestParam("shop") String shopJson, MultipartFile shopImgFile, HttpServletRequest request) throws IOException {
        // 参数校验
        if (!CodeUtil.checkVerifyCode(request)) {
            return AjaxResult.error("输入了错误的验证码");
        }
        if (shopImgFile == null || shopImgFile.isEmpty()) {
            return AjaxResult.error("上传图片不能为空");
        }
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = mapper.readValue(shopJson, Shop.class);
        if (shop == null) {
            return AjaxResult.error("请输入店铺信息");
        }
        // 参数补充
        // TODO 设置管理员（在线登陆用户）
        shop.setOwner((PersonInfo) request.getSession().getAttribute("user"));
        ShopExecution se = shopService.add(shop, ImageHolder.builder().image(shopImgFile.getInputStream()).imageName(shopImgFile.getOriginalFilename()).build());
        if (se.getState() != ShopStateEnum.CHECK.getState()) {
            return AjaxResult.error(se.getStateInfo());
        }
        // 把能管理的店铺放在session中
//        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
//        if (shopList == null) {
//            shopList = new ArrayList<>();
//        }
//        shopList.add(se.getShop());
//        request.getSession().setAttribute("shopList", shopList);
        return AjaxResult.success();
    }

    /**
     * 修改店铺
     *
     * @param shopJson
     * @param shopImgFile
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult modify(@RequestParam("shop") String shopJson, MultipartFile shopImgFile, HttpServletRequest request) throws IOException {
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            return AjaxResult.error("输入了错误的验证码");
        }
        // 仓库修改
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = mapper.readValue(shopJson, Shop.class);

        if (shop != null && shop.getId() != null) {
            InputStream in = null;
            String imgName = null;
            if (shopImgFile != null && shopImgFile.getSize() > 0) {
                in = shopImgFile.getInputStream();
                imgName = shopImgFile.getOriginalFilename();
            }
            // TODO 应该把Multi part File 传入Service层
            ShopExecution se = shopService.modifyShop(shop, ImageHolder.builder().image(in).imageName(imgName).build());
            if (se.getState() == ShopStateEnum.CHECK.getState()) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error(se.getStateInfo());
            }
        }
        return AjaxResult.error("empty shopId");
    }

//    private static void inputStreamToFile(InputStream ins, File shopImgFile) {
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(shopImgFile);
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
