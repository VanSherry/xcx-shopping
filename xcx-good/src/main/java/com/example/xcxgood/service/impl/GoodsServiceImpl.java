package com.example.xcxgood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xcxcommon.constant.StateConstant;
import com.example.xcxcommon.result.Result;
import com.example.xcxgood.Entity.MinioEntity;
import com.example.xcxgood.mapper.GoodsMapper;
import com.example.xcxgood.service.GoodsService;
import com.example.xcxpojo.dto.UpdateGoodDTO;
import com.example.xcxpojo.dto.UploadGoodsDTO;
import com.example.xcxpojo.entity.GoodsEntity;
import com.example.xcxpojo.vo.GoodByIdVO;
import com.example.xcxpojo.vo.GoodsVO;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsEntity> implements GoodsService {
    private final MinioClient minioClient;
    private final MinioEntity minioEntity;

    private final GoodsMapper goodsMapper;
    /*
      时间格式化
     *//*
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");*/

    /**
     * 图片保存路径，自动从yml文件中获取数据
     */
    @Value("${file-save-path}")
    private String fileSavePath;


    public Result<String> uploadGood(UploadGoodsDTO uploadGoodsDTO,MultipartFile file, HttpServletRequest request) {
        /*//1.后半段目录：  2020/03/15
        String directory = simpleDateFormat.format(new Date());
        *//**
         *  2.文件保存目录  D:../images/2020/03/15/
         *//*
        File dir = new File(fileSavePath + directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        log.info("图片上传，保存位置：" + fileSavePath + directory);
        //3.给文件重新设置一个名字
        //后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFileName= UUID.randomUUID().toString().replaceAll("-", "")+suffix;
        //4.创建这个新文件
        File newFile = new File(fileSavePath + directory + newFileName);
        //5.复制操作
        try {
            file.transferTo(newFile);
            //协议 :// ip地址 ：端口号 / 文件目录(/images/2020/03/15/xxx.jpg)
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/images/" + directory + newFileName;
            log.info("图片上传，访问URL：" + url);

            //保存商品
            GoodsEntity goodsEntity = new GoodsEntity();
            goodsEntity.setName(uploadGoodsDTO.getName());
            goodsEntity.setDescription(uploadGoodsDTO.getDescription());
            goodsEntity.setPrice(uploadGoodsDTO.getPrice());
            goodsEntity.setState(StateConstant.ONSALE);
            goodsEntity.setUrl(url);
            //TODO: 创建时间与更新时间的AOP
            goodsEntity.setUpTime(LocalDateTime.now());
            goodsEntity.setUpdateTime(LocalDateTime.now());

            goodsMapper.insert(goodsEntity);
            return Result.success(url);
        } catch (IOException e) {
            return Result.error("IO异常");
        }*/
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFileName= UUID.randomUUID().toString().replaceAll("-", "")+suffix;
        try{
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioEntity.getBucket())
                            .object(newFileName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            String url = minioEntity.getEndPoint() + "/" + minioEntity.getBucket() + "/" + newFileName;
            log.info(url);

            GoodsEntity goodsEntity = new GoodsEntity();
            goodsEntity.setName(uploadGoodsDTO.getName());
            goodsEntity.setDescription(uploadGoodsDTO.getDescription());
            goodsEntity.setPrice(uploadGoodsDTO.getPrice());
            goodsEntity.setState(StateConstant.ONSALE);
            goodsEntity.setUrl(url);
            //TODO: 创建时间与更新时间的AOP
            goodsEntity.setUpTime(LocalDateTime.now());
            goodsEntity.setUpdateTime(LocalDateTime.now());

            goodsMapper.insert(goodsEntity);

            return Result.success(url);

        }catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return Result.error("上传图片到mino失败：" + e.getMessage());
        }

    }

    /**
     * 查询商品信息
     * @return
     */
    public Result<ArrayList<GoodsVO>> selectGoods(int pageNum,int pageSize) {
        Page<GoodsEntity> page = new Page<>(pageNum,pageSize);
        QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",1);

        Page<GoodsEntity> goodsEntityPage = goodsMapper.selectPage(page, queryWrapper);
        ArrayList<GoodsVO> goods = new ArrayList<>();
        List<GoodsEntity> records = goodsEntityPage.getRecords();

        for (GoodsEntity record : records) {
            GoodsVO goodsVO = new GoodsVO();
            goodsVO.setId(String.valueOf(record.getId()));
            goodsVO.setName(record.getName());
            goodsVO.setDescription(record.getDescription());
            goodsVO.setPrice(record.getPrice());
            goodsVO.setUrl(record.getUrl());
            goods.add(goodsVO);
        }

        log.info(goods.toString());
        return Result.success(goods);
    }

    /**
     * 查询商品信息(按价格升序)
     * @return
     */
    public Result<ArrayList<GoodsVO>> selectGoodsASC(int pageNum,int pageSize) {
        Page<GoodsEntity> page = new Page<>(pageNum,pageSize);
        QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("price");

        Page<GoodsEntity> goodsEntityPage = goodsMapper.selectPage(page, queryWrapper);
        ArrayList<GoodsVO> goods = new ArrayList<>();
        List<GoodsEntity> records = goodsEntityPage.getRecords();

        for (GoodsEntity record : records) {
            GoodsVO goodsVO = new GoodsVO();
            goodsVO.setId(String.valueOf(record.getId()));
            goodsVO.setName(record.getName());
            goodsVO.setDescription(record.getDescription());
            goodsVO.setPrice(record.getPrice());
            goodsVO.setUrl(record.getUrl());
            goods.add(goodsVO);
        }

        log.info(goods.toString());
        return Result.success(goods);
    }

    /**
     * 查询商品信息(按价格降序)
     * @return
     */
    public Result<ArrayList<GoodsVO>> selectGoodsDESC(int pageNum,int pageSize) {
        Page<GoodsEntity> page = new Page<>(pageNum,pageSize);
        QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("price");

        Page<GoodsEntity> goodsEntityPage = goodsMapper.selectPage(page, queryWrapper);
        ArrayList<GoodsVO> goods = new ArrayList<>();
        List<GoodsEntity> records = goodsEntityPage.getRecords();

        for (GoodsEntity record : records) {
            GoodsVO goodsVO = new GoodsVO();
            goodsVO.setId(String.valueOf(record.getId()));
            goodsVO.setName(record.getName());
            goodsVO.setDescription(record.getDescription());
            goodsVO.setPrice(record.getPrice());
            goodsVO.setUrl(record.getUrl());
            goods.add(goodsVO);
        }

        log.info(goods.toString());
        return Result.success(goods);
    }

    /**
     * 根据商品名称模糊查询商品
     * @param keyword
     * @return
     */
    public Result<ArrayList<GoodsVO>> search(String keyword) {
        QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper<>();
        //防止sql注入
        queryWrapper.like("name", StringUtils.isBlank(keyword) ? null : keyword);

        List<GoodsEntity> goodsEntities = goodsMapper.selectList(queryWrapper);
        ArrayList<GoodsVO> goods = new ArrayList<>();

        for (GoodsEntity record : goodsEntities) {
            GoodsVO goodsVO = new GoodsVO();
            goodsVO.setId(String.valueOf(record.getId()));
            goodsVO.setName(record.getName());
            goodsVO.setDescription(record.getDescription());
            goodsVO.setPrice(record.getPrice());
            goodsVO.setUrl(record.getUrl());
            goods.add(goodsVO);
        }
        log.info(goods.toString());
        return Result.success(goods);
    }

    @Override
    public GoodByIdVO selectGoodsById(Long id) {
        GoodsEntity goodsEntity = lambdaQuery().eq(GoodsEntity::getId,id).eq(GoodsEntity::getState,1).one();
        GoodByIdVO goodByIdVO = new GoodByIdVO();

        goodByIdVO.setState(goodsEntity.getState());
        goodByIdVO.setDescription(goodsEntity.getDescription());
        goodByIdVO.setPrice(goodsEntity.getPrice());
        goodByIdVO.setName(goodsEntity.getName());
        goodByIdVO.setUpTime(goodsEntity.getUpTime());
        goodByIdVO.setUpdateTime(goodsEntity.getUpdateTime());
        goodByIdVO.setUrl(goodsEntity.getUrl());
        goodByIdVO.setId(String.valueOf(goodsEntity.getId()));

        return goodByIdVO;
    }

    /**
     * 根据id删除商品
     * @param id
     */
    public void deleteById(Long id) {
        GoodsEntity one = lambdaQuery().eq(GoodsEntity::getId, id).one();
        one.setState(0);
        goodsMapper.updateById(one);
    }

    /**
     * 更新商品
     * @param
     */
    @Override
    public void updateById(UpdateGoodDTO updateGoodDTO) {
        GoodsEntity one = lambdaQuery().eq(GoodsEntity::getId, updateGoodDTO.getId()).one();
        one.setUrl(updateGoodDTO.getUrl());
        one.setPrice(updateGoodDTO.getPrice());
        one.setName(updateGoodDTO.getName());
        one.setDescription(updateGoodDTO.getDescription());
        goodsMapper.updateById(one);
    }
}
