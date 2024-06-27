package com.example.xcxgood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xcxcommon.result.Result;
import com.example.xcxcommon.utils.GoodVOToGoodEntity;
import com.example.xcxgood.mapper.CategoryMapper;
import com.example.xcxgood.service.CategoryService;
import com.example.xcxgood.service.GoodsService;
import com.example.xcxpojo.entity.CategoryEntity;
import com.example.xcxpojo.entity.GoodsEntity;
import com.example.xcxpojo.vo.CategoryVO;
import com.example.xcxpojo.vo.GoodByIdVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {
    private final GoodsService goodsService;
    /**
     * 根据分类查询商品信息
     * @return
     */
    public Result<ArrayList<CategoryVO>> selectGoodsByCategory(Integer category) {
        List<CategoryEntity> categoryEntities = lambdaQuery().eq(CategoryEntity::getCategory, category).list();

        ArrayList<CategoryVO> result = new ArrayList<>();

        for (CategoryEntity categoryEntity : categoryEntities) {
            GoodByIdVO goodByIdVO = goodsService.selectGoodsById(categoryEntity.getGoodId());
            GoodsEntity goodsEntity = GoodVOToGoodEntity.exchange(goodByIdVO);


            CategoryVO categoryVO = new CategoryVO();

            categoryVO.setId(String.valueOf(goodsEntity.getId()));
            categoryVO.setName(goodsEntity.getName());
            categoryVO.setUrl(goodsEntity.getUrl());
            categoryVO.setPrice(goodsEntity.getPrice());
            categoryVO.setDescription(goodsEntity.getDescription());

            result.add(categoryVO);
        }

        return Result.success(result);
    }
}
