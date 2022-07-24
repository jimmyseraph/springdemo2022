package vip.testops.goods.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import vip.testops.goods.entities.criteria.GoodsCriteria;
import vip.testops.goods.entities.dto.GoodsDTO;
import vip.testops.goods.providers.GoodsSQLProvider;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Select("select * from t_goods where `id`=#{id}")
    GoodsDTO getGoodsById(long id);

    @SelectProvider(type = GoodsSQLProvider.class, method = "selectByCriteria")
    List<GoodsDTO> getGoodsListByCriteria(GoodsCriteria criteria);

    @SelectProvider(type = GoodsSQLProvider.class, method = "selectCountByCriteria")
    int countGoodsByCriteria(GoodsCriteria criteria);
}
