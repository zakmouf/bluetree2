
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

-- v_market

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
