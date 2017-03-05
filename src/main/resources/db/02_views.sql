
-- v_price

CREATE OR REPLACE VIEW v_price
AS
SELECT  p.stock_id,
        p.f_date AS price_date,
        p.f_value AS price_value
FROM    t_price p;

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

-- v_portfolio

CREATE OR REPLACE VIEW v_portfolio
AS
SELECT  p.f_id AS portfolio_id,
        p.f_name AS portfolio_name,
        p.f_from_date as portfolio_from_date,
        p.f_to_date AS portfolio_to_date,
        p.f_beta AS portfolio_beta,
        p.f_size AS portfolio_size,
        m.market_id,
        m.market_name,
        m.market_riskless,
        m.indice_id,
        m.indice_symbol,
        m.indice_name,
        m.indice_date_count,
        m.indice_first_date,
        m.indice_last_date
FROM    t_portfolio p
LEFT JOIN v_market m ON m.market_id = p.market_id;

-- v_holding

CREATE OR REPLACE VIEW v_holding
AS
SELECT  h.portfolio_id,
        s.stock_id,
        s.stock_symbol,
        s.stock_name,
        s.stock_date_count,
        s.stock_first_date,
        s.stock_last_date,
        h.f_weight AS holding_weight
FROM    t_holding h
LEFT JOIN v_stock s ON s.stock_id = h.stock_id;
