
-- mysqld --initialize-insecure
-- mysqld --console
-- mysql -u root
-- set password for 'root'@'localhost' = password ('mysql');
-- mysqladmin -u root -p shutdown

-- drop database if exists bluetree;
-- create database bluetree character set 'utf8';
-- drop user if exists 'bluetree'@'localhost';
-- create user 'bluetree'@'localhost';
-- set password for 'bluetree'@'localhost' = password ('mysql');
-- grant all privileges on bluetree.* to 'bluetree'@'localhost' with grant option;

-- t_market

drop table if exists t_market;

create table t_market (
  f_id        bigint unsigned not null,
  f_name      varchar (255) not null,
  f_riskless  double not null,
  indice_id   bigint unsigned not null
)
engine = innodb;

alter table t_market add constraint pk_market primary key (f_id);
alter table t_market add constraint ux_market_1 unique key (f_name);
alter table t_market add constraint fk_market_1 foreign key (indice_id) references t_stock (f_id);

CREATE OR REPLACE VIEW v_market
AS
SELECT  m.f_id AS market_id,
        m.f_name AS market_name,
        m.f_riskless as market_riskless,
        s.stock_id AS indice_id,
        s.stock_symbol AS indice_symbol,
        s.stock_name AS indice_name,
        s.stock_date_count AS indice_date_count,
        s.stock_first_date AS indice_first_date,
        s.stock_last_date AS indice_last_date
FROM    t_market m
        LEFT JOIN v_stock s ON s.stock_id = m.indice_id;

drop table if exists t_market_stock;

create table t_market_stock (
  market_id  bigint unsigned not null,
  stock_id   bigint unsigned not null
)
engine = innodb;

alter table t_market_stock add constraint pk_market_stock primary key (market_id, stock_id);
alter table t_market_stock add constraint fk_market_stock_1 foreign key (market_id) references t_market (f_id) on delete cascade;
alter table t_market_stock add constraint fk_market_stock_2 foreign key (stock_id) references t_stock (f_id);

-- t_stock

drop table if exists t_stock;

create table t_stock (
  f_id      bigint unsigned not null,
  f_symbol  varchar (50) not null,
  f_name    varchar (255)
)
engine = innodb;

alter table t_stock add constraint pk_stock primary key (f_id);
alter table t_stock add constraint ux_stock_1 unique key (f_symbol);

-- t_price

drop table if exists t_price;

create table t_price (
  stock_id  bigint unsigned not null,
  f_date    date not null,
  f_value   double not null
)
engine = innodb;

alter table t_price add constraint pk_price primary key (stock_id, f_date);
alter table t_price add index ix_price_1 (stock_id);
alter table t_price add constraint fk_price_1 foreign key (stock_id) references t_stock (f_id) on delete cascade;

-- v_price

CREATE OR REPLACE VIEW v_price
AS
SELECT p.stock_id,
       p.f_date AS price_date,
       p.f_value AS price_value
FROM   t_price p;

-- v_stock_price

CREATE OR REPLACE VIEW v_stock_price
AS
SELECT   p.stock_id,
         count(1) AS stock_date_count,
         min(p.price_date) AS stock_first_date,
         max(p.price_date) AS stock_last_date
FROM     v_price p
GROUP BY p.stock_id;

-- v_stock

CREATE OR REPLACE VIEW v_stock
AS
SELECT  s.f_id AS stock_id,
        s.f_symbol AS stock_symbol,
        s.f_name AS stock_name,
        p.stock_date_count,
        p.stock_first_date,
        p.stock_last_date
FROM    t_stock s
        LEFT JOIN v_stock_price p ON p.stock_id = s.f_id;

--

drop table if exists portfolios;

create table portfolios (
  portfolio_id int auto_increment primary key, 
  portfolio_name varchar(255) not null,
  portfolio_from_date date not null,
  portfolio_to_date date not null,
  portfolio_beta double not null, 
  portfolio_size int not null
);

drop table if exists portfolio_lnk_market;

create table portfolio_lnk_market (
  plm_portfolio_id int not null,
  plm_market_id int not null,
  primary key (plm_portfolio_id, plm_market_id)
);

create index ix_portfolio_lnk_market_1 on portfolio_lnk_market (plm_portfolio_id);
create index ix_portfolio_lnk_market_2 on portfolio_lnk_market (plm_market_id);

drop table if exists portfolio_lnk_stock;

create table portfolio_lnk_stock (
  pls_portfolio_id int not null,
  pls_stock_id int not null,
  pls_weight double not null,
  primary key (pls_portfolio_id, pls_stock_id)
);

create index ix_portfolio_lnk_stock_1 on portfolio_lnk_stock (pls_portfolio_id);
create index ix_portfolio_lnk_stock_2 on portfolio_lnk_stock (pls_stock_id);



