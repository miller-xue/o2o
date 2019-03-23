package com.miller.o2o.service.impl;

import com.miller.o2o.dao.ProductImgDao;
import com.miller.o2o.entity.ProductImg;
import com.miller.o2o.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by miller on 2019/3/19
 *
 * @author Miller
 */
@Service
public class ProductImgServiceImpl implements ProductImgService {

    @Autowired
    private ProductImgDao productImgDao;



    @Override
    public boolean batchAdd(List<ProductImg> imgList) {
        return productImgDao.batchInsert(imgList) > 0;
    }

    @Override
    public boolean deleteByProductId(long productId) {
        // TODO 删除图片文件
        // 删除数据库信息
        return true;
    }
}