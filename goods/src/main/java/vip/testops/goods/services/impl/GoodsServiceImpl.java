package vip.testops.goods.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.testops.goods.entities.criteria.GoodsCriteria;
import vip.testops.goods.entities.dto.GoodsDTO;
import vip.testops.goods.entities.resp.GoodsListResp;
import vip.testops.goods.entities.resp.Pagination;
import vip.testops.goods.entities.resp.Response;
import vip.testops.goods.mappers.GoodsMapper;
import vip.testops.goods.services.GoodsService;

import java.util.List;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {
    private GoodsMapper goodsMapper;

    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public void doList(GoodsCriteria criteria, Response<GoodsListResp> response) {
        // 1 select from db with criteria
        // if pageSize = null, set pageSize = 10;
        if(criteria.getPageSize() == null || criteria.getPageSize() == 0) {
            criteria.setPageSize(10);
        }
        if(criteria.getCurrent() == null || criteria.getCurrent() == 0) {
            criteria.setCurrent(1);
        }
        List<GoodsDTO> list = goodsMapper.getGoodsListByCriteria(criteria);
        int total = goodsMapper.countGoodsByCriteria(criteria);

        // 2 package response
        GoodsListResp goodsListResp = new GoodsListResp();
        goodsListResp.setGoodsList(list);
        Pagination pagination = new Pagination();
        pagination.setPageSize(criteria.getPageSize());
        pagination.setCurrent(criteria.getCurrent());
        pagination.setTotal(total);
        goodsListResp.setPagination(pagination);
        response.successWithData(goodsListResp);
    }

    @Override
    public void doDetail(long id, Response<GoodsDTO> response) {
        GoodsDTO goodsDTO = goodsMapper.getGoodsById(id);
        response.successWithData(goodsDTO);
    }
}
