package vip.testops.order.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import vip.testops.order.entities.dto.OrderDTO;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("insert into t_order values(null, #{customId}, now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    int addOrder(OrderDTO orderDTO);

    @Select("select * from t_order where customId=#{customId}")
    List<OrderDTO> getOrderListByCustomId(long customId);

    @Select("select * from t_order where `id`=#{id}")
    OrderDTO getOrderById(long id);
}
