package com.miller.o2o.web.frontend;

import com.miller.o2o.common.AjaxResult;
import com.miller.o2o.entity.HeadLine;
import com.miller.o2o.entity.ShopCategory;
import com.miller.o2o.service.HeadLineService;
import com.miller.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by miller on 2019/3/25
 *
 * @author Miller
 */
@Controller
@RequestMapping("/frontend")
public class MainPageController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private HeadLineService headLineService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult init() {
        List<ShopCategory> shopCategoryList = shopCategoryService.getList(null);
        List<HeadLine> headLineList = headLineService.getList(HeadLine.builder().enableStatus(1).build());
        return AjaxResult.success().put("shopCategoryList", shopCategoryList)
                .put("headLineList", headLineList);
    }
}
