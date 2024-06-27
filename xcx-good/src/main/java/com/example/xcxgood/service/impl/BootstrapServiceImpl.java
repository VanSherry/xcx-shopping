package com.example.xcxgood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xcxcommon.result.Result;
import com.example.xcxgood.mapper.BootstrapMapper;
import com.example.xcxgood.service.BootstrapService;
import com.example.xcxpojo.entity.BootstrapEntity;
import com.example.xcxpojo.vo.BootstrapVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BootstrapServiceImpl extends ServiceImpl<BootstrapMapper, BootstrapEntity> implements BootstrapService {
    private final BootstrapMapper bootstrapMapper;

    /**
     * 查询轮播图
     * @return
     */
    public Result<ArrayList<BootstrapVO>> selectBootstrap() {
        QueryWrapper<BootstrapEntity> queryWrapper = new QueryWrapper<>();
        List<BootstrapEntity> list = bootstrapMapper.selectList(queryWrapper);
        ArrayList<BootstrapVO> arrayList = new ArrayList<>();
        for (BootstrapEntity entity : list) {
            BootstrapVO bootstrapVO = new BootstrapVO();
            bootstrapVO.setUrl(entity.getUrl());
            bootstrapVO.setGoodId(entity.getGoodId());
            arrayList.add(bootstrapVO);
        }
        return Result.success(arrayList);
    }
}
