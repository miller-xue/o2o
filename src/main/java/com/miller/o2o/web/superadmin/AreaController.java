package com.miller.o2o.web.superadmin;

import com.miller.o2o.entity.Area;
import com.miller.o2o.service.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by miller on 2019/2/11
 *
 * @author Miller
 */
@Controller
@RequestMapping("/superadmin/area")
@Slf4j
public class AreaController {

    @Autowired
    private AreaService areaService;


    @RequestMapping(value = "/listarea", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listArea() {
        log.info("===start===");
        long startTime = System.currentTimeMillis();
        Map<String, Object> model = new HashMap<>();
        List<Area> list = null;
        try {
            list = areaService.getList();
            model.put("success", true);
            model.put("rows", list);
            model.put("total", list.size());
        } catch (Exception e) {
            e.printStackTrace();
            model.put("success", false);
            model.put("errMsg", e.toString());
        }
        log.error("test error");
        long endTime = System.currentTimeMillis();
        log.debug("costTime:[{}ms]", endTime - startTime);

        log.info("===end===");
        return model;
    }

}
