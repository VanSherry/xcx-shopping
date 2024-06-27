package com.example.xcxgood.controller;

import com.example.xcxcommon.result.Result;
import com.example.xcxgood.service.BootstrapService;
import com.example.xcxgood.service.CategoryService;
import com.example.xcxgood.service.GoodsClickService;
import com.example.xcxgood.service.GoodsService;
import com.example.xcxpojo.dto.UpdateGoodDTO;
import com.example.xcxpojo.dto.UploadGoodsDTO;
import com.example.xcxpojo.entity.GoodsEntity;
import com.example.xcxpojo.vo.BootstrapVO;
import com.example.xcxpojo.vo.CategoryVO;
import com.example.xcxpojo.vo.GoodByIdVO;
import com.example.xcxpojo.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/goods")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GoodsController {
    private final GoodsService goodsService;
    private final GoodsClickService goodsClickService;
    private final BootstrapService bootstrapService;
    private final CategoryService categoryService;

    /**
     * 保存商品
     * @param uploadGoodsDTO
     * @param file
     * @param request
     * @return
     */

    @PostMapping("/uploadGood")
    public Result<String> uploadGoods(@RequestPart("json") UploadGoodsDTO uploadGoodsDTO,@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        return goodsService.uploadGood(uploadGoodsDTO,file,request);
    }

    /**
     * 查询商品信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/selectGoods")
    public Result<ArrayList<GoodsVO>> selectGoods(int pageNum,int pageSize){
        return goodsService.selectGoods(pageNum,pageSize);
    }

    /**
     * 查询商品信息(按价格升序)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/selectGoodsASC")
    public Result<ArrayList<GoodsVO>> selectGoodsASC(int pageNum,int pageSize){
        return goodsService.selectGoodsASC(pageNum,pageSize);
    }

    /**
     * 查询商品信息(按价格降序)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/selectGoodsDESC")
    public Result<ArrayList<GoodsVO>> selectGoodsDESC(int pageNum,int pageSize){
        return goodsService.selectGoodsDESC(pageNum,pageSize);
    }

    /**
     * 根据商品名称模糊查询商品
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public Result<ArrayList<GoodsVO>> search(String keyword){
        return goodsService.search(keyword);
    }

    /**
     * 在加入购物车时，将点击量记录
     * @param goodId
     */
    @PutMapping()
    public void click(Long goodId){
        goodsClickService.insert(goodId);
    }

    /**
     * 根据商品id查询商品信息
     * @return
     */
    @GetMapping("/selectGoodsById")
    public GoodByIdVO selectGoodsById(Long id){
        return goodsService.selectGoodsById(id);
    }

    /**
     * 查询轮播图
     * @return
     */
    @GetMapping("/selectBootstrap")
    public Result<ArrayList<BootstrapVO>> selectBootstrap(){
        return bootstrapService.selectBootstrap();
    }

    /**
     * 根据分类查询商品信息
     * @return
     */
    @GetMapping("/selectGoodsByCategory")
    public Result<ArrayList<CategoryVO>> selectGoodsByCategory(Integer category){
        return categoryService.selectGoodsByCategory(category);
    }

    /**
     * 删除商品
     */
    @PostMapping("/deleteById")
    public Result<String> deleteById(Long id){
        goodsService.deleteById(id);
        return Result.success("删除商品成功");
    }
    /**
     * 编辑商品
     */
    @PostMapping("/updateById")
    public Result<String> updateById(UpdateGoodDTO updateGoodDTO){
        goodsService.updateById(updateGoodDTO);
        return Result.success("编辑商品成功");
    }
}
