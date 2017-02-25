
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

drop table if exists market_lnk_indice;

create table market_lnk_indice (
  mli_market_id int not null,
  mli_indice_id int not null,
  primary key (mli_market_id, mli_indice_id)
);

create index ix_market_lnk_indice_1 on market_lnk_indice (mli_market_id);
create index ix_market_lnk_indice_2 on market_lnk_indice (mli_indice_id);

drop table if exists markets;

create table markets (
  market_id int auto_increment primary key, 
  market_name varchar(255) unique not null,
  market_riskless double not null
);

drop table if exists prices;

create table prices (
  price_stock_id int not null, 
  price_date date not null,
  price_value double not null,
  primary key (price_stock_id, price_date)
);

create index ix_prices_1 on prices (price_stock_id);
create index ix_prices_2 on prices (price_date);

drop table if exists market_lnk_stock;

create table market_lnk_stock (
  mls_market_id int not null,
  mls_stock_id int not null,
  primary key (mls_market_id, mls_stock_id)
);

create index ix_market_lnk_stock_1 on market_lnk_stock (mls_market_id);
create index ix_market_lnk_stock_2 on market_lnk_stock (mls_stock_id);

drop table if exists stocks;

create table stocks (
  stock_id int auto_increment primary key, 
  stock_symbol varchar(50) unique not null,
  stock_name varchar(255) not null
);

drop table if exists portfolios;

create table portfolios (
  portfolio_id int auto_increment primary key, 
  portfolio_name varchar(255) not null,
  portfolio_from_date date not null,
  portfolio_to_date date not null,
  portfolio_beta double not null, 
  portfolio_size int not null,
  portfolio_executed bool not null,
  portfolio_error varchar(255) null
);

create index ix_portfolios_1 on portfolios (portfolio_executed);

drop table if exists portfolio_lnk_market;

create table portfolio_lnk_market (
  plm_portfolio_id int not null,
  plm_market_id int not null,
  primary key (plm_portfolio_id, plm_market_id)
);

create index ix_portfolio_lnk_market_1 on portfolio_lnk_market (plm_portfolio_id);
create index ix_portfolio_lnk_market_2 on portfolio_lnk_market (plm_market_id);

drop table if exists portfolio_lnk_user;

create table portfolio_lnk_user (
  plu_portfolio_id int not null,
  plu_user_id int not null,
  primary key (plu_portfolio_id, plu_user_id)
);

create index ix_portfolio_lnk_user_1 on portfolio_lnk_user (plu_portfolio_id);
create index ix_portfolio_lnk_user_2 on portfolio_lnk_user (plu_user_id);

drop table if exists portfolio_lnk_stock;

create table portfolio_lnk_stock (
  pls_portfolio_id int not null,
  pls_stock_id int not null,
  pls_weight double not null,
  primary key (pls_portfolio_id, pls_stock_id)
);

create index ix_portfolio_lnk_stock_1 on portfolio_lnk_stock (pls_portfolio_id);
create index ix_portfolio_lnk_stock_2 on portfolio_lnk_stock (pls_stock_id);



