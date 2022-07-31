package vip.testops.order.clients.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import vip.testops.order.clients.GoodsClient;
import vip.testops.order.entities.dto.GoodsDTO;
import vip.testops.order.entities.resp.Response;

@Component
public class GoodsClientFallbackFactory implements FallbackFactory<GoodsClient> {

    @Override
    public GoodsClient create(Throwable cause) {
        return new GoodsClient() {
            @Override
            public Response<GoodsDTO> getGoods(long id) {
                Response<GoodsDTO> response = new Response<>();
                response.internalError("goods service is not available");
                return response;
            }
        };
    }
}
