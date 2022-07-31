package vip.testops.order.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vip.testops.order.clients.fallback.GoodsClientFallbackFactory;
import vip.testops.order.entities.dto.GoodsDTO;
import vip.testops.order.entities.resp.Response;

@FeignClient(value = "goods", fallbackFactory = GoodsClientFallbackFactory.class)
public interface GoodsClient {
    @GetMapping(value = "/goods/{id}/detail", consumes = "application/json")
    Response<GoodsDTO> getGoods(@PathVariable long id);
}
