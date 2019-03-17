package com.miller.o2o.service.impl;

import com.miller.o2o.service.PersonInfoService;
import org.springframework.stereotype.Service;

/**
 * Created by miller on 2019/3/17
 *
 * @author Miller
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {


    @Override
    public boolean isAdmin() {
        return true;
    }
}
