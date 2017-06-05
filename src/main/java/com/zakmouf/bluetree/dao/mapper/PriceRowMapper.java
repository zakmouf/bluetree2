package com.zakmouf.bluetree.dao.mapper;

import com.zakmouf.bluetree.domain.Price;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceRowMapper extends BaseRowMapper implements RowMapper<Price> {

    @Override
    public Price mapRow(ResultSet rs, int rowNum) throws SQLException {
        Price price = mapPrice(rs);
        return price;
    }

}
