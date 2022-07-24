package vip.testops.order.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vip.testops.order.entities.dto.GoodsDTO;
import vip.testops.order.entities.resp.Response;

@FeignClient(value = "goods")
public interface GoodsClient {
    @GetMapping("/{id}/detail")
    Response<GoodsDTO> getGoods(@PathVariable long id);
}
