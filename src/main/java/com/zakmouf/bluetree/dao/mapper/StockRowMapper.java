package com.zakmouf.bluetree.dao.mapper;

import com.zakmouf.bluetree.domain.Stock;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockRowMapper extends BaseRowMapper implements RowMapper<Stock> {

    @Override
    public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
        Stock stock = mapStock(rs);
        return stock;
    }

}
