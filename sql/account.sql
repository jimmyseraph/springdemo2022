create table t_account (
    `accountId`     bigint auto_increment primary key comment '账号ID',
    `accountName`   varchar(20) unique not null comment '账户名',
    `email`         varchar(40) not null comment '邮箱',
    `password`      char(64)    not null comment 'sha256摘要密码',
    `createTime`    datetime    null comment '账户创建时间',
    `lastLoginTime` datetime    null comment '账户最后登陆时间'
);