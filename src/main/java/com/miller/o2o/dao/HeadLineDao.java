package com.miller.o2o.dao;

import com.miller.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by miller on 2019/3/25
 *
 * @author Miller
 */
public interface HeadLineDao {

    List<HeadLine> select(HeadLine condition);

    HeadLine selectById(long id);
}
