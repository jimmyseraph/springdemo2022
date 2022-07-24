create table t_order(
    `id` bigint auto_increment primary key comment 'oder id',
    `customId` bigint comment 'custom ID',
    `createTime` datetime comment 'order create time'
) comment 'order info';

create table t_order_detail(
    `id` bigint auto_increment primary key comment 'detail ID',
    `orderId` bigint comment 'order Id',
    `goodsId` bigint comment 'goods ID',
    `price` decimal(10,2) comment 'goods price',
    `amount` int comment 'goods amount'
) comment 'order detail';