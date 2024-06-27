package com.example.xcxgood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xcxcommon.result.Result;
import com.example.xcxpojo.entity.BootstrapEntity;
import com.example.xcxpojo.vo.BootstrapVO;

import java.util.ArrayList;

public interface BootstrapService extends IService<BootstrapEntity> {
    Result<ArrayList<BootstrapVO>> selectBootstrap();
}
