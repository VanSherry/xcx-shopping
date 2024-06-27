package com.example.xcxgood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xcxcommon.result.Result;
import com.example.xcxpojo.entity.CategoryEntity;
import com.example.xcxpojo.vo.CategoryVO;

import java.util.ArrayList;

public interface CategoryService extends IService<CategoryEntity> {
    Result<ArrayList<CategoryVO>> selectGoodsByCategory(Integer category);
}
