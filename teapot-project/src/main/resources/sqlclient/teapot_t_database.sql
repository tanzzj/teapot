create table t_database_bak
(
    id                 int auto_increment,
    databaseName       varchar(32)  not null comment '数据库名称',
    databaseId         char(8)      not null,
    databaseConnection varchar(255) not null,
    username           varchar(32)  not null,
    password           varchar(32)  not null,
    databaseType       varchar(16)  not null,
    env                varchar(8)   not null comment '环境',
    constraint t_database_id_uindex
        unique (id)
);

alter table t_database
    add primary key (id);

INSERT INTO teapot.t_database (id, databaseName, databaseId, databaseConnection, username, password, databaseType, env) VALUES (1, 'mysql@hw', '123', '114.116.14.26:3306', 'tanzj', 'tanzj
', 'mysql', 'dev');
INSERT INTO teapot.t_database (id, databaseName, databaseId, databaseConnection, username, password, databaseType, env) VALUES (4, 'mysql@ali', '2m2Htxgs', 'jdbc:mysql://120.78.157.251:3306/teapot', 'root', 'Teamer2018.', 'mysql', 'dev');
INSERT INTO teapot.t_database (id, databaseName, databaseId, databaseConnection, username, password, databaseType, env) VALUES (5, 'mysql@test', 'tk1ux6LM', 'jdbc:mysql://120.78.157.251:3306/teapot', 'root', 'Teamer2018.', 'mysql', 'dev');
INSERT INTO teapot.t_database (id, databaseName, databaseId, databaseConnection, username, password, databaseType, env) VALUES (6, 'my', 'Cpkp16aS', 'jdbc:mysql://120.78.157.251:3306/teapot', 'root', 'Teamer2018.', 'mysql', 'dev');
INSERT INTO teapot.t_database (id, databaseName, databaseId, databaseConnection, username, password, databaseType, env) VALUES (7, '额', 'TsLVW0gJ', '3', 'root', 'Teamer2018.', 'MYSQL', 'dev');
INSERT INTO teapot.t_database (id, databaseName, databaseId, databaseConnection, username, password, databaseType, env) VALUES (8, 'D', 'YtsQx91b', '222', 'root', 'SSS', 'S', 'dev');
INSERT INTO teapot.t_database (id, databaseName, databaseId, databaseConnection, username, password, databaseType, env) VALUES (9, 'com', 'vLTdt0i1', '2 ipsum', 'ad labore ut in', 'SSS', 'ut', 'dev');
INSERT INTO teapot.t_database (id, databaseName, databaseId, databaseConnection, username, password, databaseType, env) VALUES (10, 'com', '2', 'consectetur ipsum', 'ad labore ut in', 'ad incididunt exercitation non', 'ut', 'dev');