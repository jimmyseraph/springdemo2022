package vip.testops.goods.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.testops.goods.entities.criteria.GoodsCriteria;
import vip.testops.goods.entities.dto.GoodsDTO;
import vip.testops.goods.entities.resp.GoodsListResp;
import vip.testops.goods.entities.resp.Response;
import vip.testops.goods.services.GoodsService;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    private GoodsService goodsService;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping("/list")
    public Response<GoodsListResp> list(@RequestBody GoodsCriteria criteria) {
        Response<GoodsListResp> response = new Response<>();
        // deep into service
        goodsService.doList(criteria, response);
        return response;
    }

    @GetMapping("/{id}/detail")
    public Response<GoodsDTO> getGoods(@PathVariable long id) {
        Response<GoodsDTO> response = new Response<>();
        // deep into service
        goodsService.doDetail(id, response);
        return response;
    }
}
