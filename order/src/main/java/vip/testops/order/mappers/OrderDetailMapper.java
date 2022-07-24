package vip.testops.order.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import vip.testops.order.entities.dto.OrderDetailDTO;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    @Insert("insert into t_order_detail values(null, #{orderId}, #{goodsId}, #{price}, #{amount})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    int addOrderDetail(OrderDetailDTO orderDetailDTO);

    @Select("select * from t_order_detail where orderId=#{orderId}")
    List<OrderDetailDTO> getDetailByOrderId(long orderId);
}
