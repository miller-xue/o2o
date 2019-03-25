package com.miller.o2o.service;

import com.miller.o2o.entity.HeadLine;

import java.util.List;

/**
 * Created by miller on 2019/3/25
 *
 * @author Miller
 */
public interface HeadLineService {

    List<HeadLine> getList(HeadLine condition);
}
