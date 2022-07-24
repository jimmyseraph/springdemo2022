package vip.testops.goods.entities.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import vip.testops.goods.entities.dto.GoodsDTO;

import java.util.List;

@Data
public class GoodsListResp {
    @JsonProperty("goods_list")
    private List<GoodsDTO> goodsList;
    private Pagination pagination;
}
