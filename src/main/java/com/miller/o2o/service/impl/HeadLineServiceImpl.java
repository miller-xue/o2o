package com.miller.o2o.service.impl;

import com.miller.o2o.dao.HeadLineDao;
import com.miller.o2o.entity.HeadLine;
import com.miller.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by miller on 2019/3/25
 *
 * @author Miller
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;

    @Override
    public List<HeadLine> getList(HeadLine condition) {
        return headLineDao.select(condition);
    }

}
