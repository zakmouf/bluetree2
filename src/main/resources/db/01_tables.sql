
drop table if exists t_portfolio_stock;
drop table if exists t_portfolio;
drop table if exists t_market_stock;
drop table if exists t_market;
drop table if exists t_price;
drop table if exists t_stock;

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
alter table t_price add constraint fk_price_1 foreign key (stock_id) references t_stock (f_id) on delete cascade;

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

-- t_market_stock

drop table if exists t_market_stock;

create table t_market_stock (
  market_id  bigint unsigned not null,
  stock_id   bigint unsigned not null
)
engine = innodb;

alter table t_market_stock add constraint pk_market_stock primary key (market_id, stock_id);
alter table t_market_stock add constraint fk_market_stock_1 foreign key (market_id) references t_market (f_id) on delete cascade;
alter table t_market_stock add constraint fk_market_stock_2 foreign key (stock_id) references t_stock (f_id) on delete cascade;

-- t_portfolio

drop table if exists t_portfolio;

create table t_portfolio (
  f_id         bigint unsigned not null,
  f_name       varchar(255) not null,
  f_from_date  date not null,
  f_to_date    date not null,
  f_beta       double not null,
  f_size       int not null,
  market_id    bigint unsigned not null
)
engine = innodb;

alter table t_portfolio add constraint pk_portfolio primary key (f_id);
alter table t_portfolio add constraint ux_portfolio_1 unique key (f_name);
alter table t_portfolio add constraint fk_portfolio_1 foreign key (market_id) references t_market (f_id);

-- t_portfolio_stock

drop table if exists t_portfolio_stock;

create table t_portfolio_stock (
  portfolio_id  bigint unsigned not null,
  stock_id      bigint unsigned not null,
  f_weight      double not null
)
engine = innodb;

alter table t_portfolio_stock add constraint pk_portfolio_stock primary key (portfolio_id, stock_id);
alter table t_portfolio_stock add constraint fk_portfolio_stock_1 foreign key (portfolio_id) references t_portfolio (f_id) on delete cascade;
alter table t_portfolio_stock add constraint fk_portfolio_stock_2 foreign key (stock_id) references t_stock (f_id);
