package vip.testops.goods.services;

import vip.testops.goods.entities.criteria.GoodsCriteria;
import vip.testops.goods.entities.dto.GoodsDTO;
import vip.testops.goods.entities.resp.GoodsListResp;
import vip.testops.goods.entities.resp.Response;

public interface GoodsService {
    void doList(GoodsCriteria criteria, Response<GoodsListResp> response);
    void doDetail(long id, Response<GoodsDTO> response);
}
