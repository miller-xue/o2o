package com.miller.o2o.service;

import com.miller.o2o.dto.ShopExecution;
import com.miller.o2o.entity.Shop;

import java.io.File;

/**
 * Created by miller on 2019/2/17
 *
 * @author Miller
 */
public interface ShopService {

    ShopExecution add(Shop shop, File shopImg);
}
