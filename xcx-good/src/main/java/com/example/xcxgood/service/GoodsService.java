package com.example.xcxgood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xcxcommon.result.Result;
import com.example.xcxpojo.dto.UpdateGoodDTO;
import com.example.xcxpojo.dto.UploadGoodsDTO;
import com.example.xcxpojo.entity.GoodsEntity;
import com.example.xcxpojo.vo.GoodByIdVO;
import com.example.xcxpojo.vo.GoodsVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public interface GoodsService extends IService<GoodsEntity> {
    Result<String> uploadGood(UploadGoodsDTO uploadGoodsDTO,MultipartFile file, HttpServletRequest request);

    Result<ArrayList<GoodsVO>> selectGoods(int pageNum,int pageSize);

    Result<ArrayList<GoodsVO>> selectGoodsASC(int pageNum, int pageSize);

    Result<ArrayList<GoodsVO>> selectGoodsDESC(int pageNum, int pageSize);

    Result<ArrayList<GoodsVO>> search(String keyword);

    GoodByIdVO selectGoodsById(Long id);

    void deleteById(Long id);

    void updateById(UpdateGoodDTO updateGoodDTO);
}
