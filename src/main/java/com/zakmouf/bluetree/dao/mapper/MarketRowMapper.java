package com.zakmouf.bluetree.dao.mapper;

import com.zakmouf.bluetree.domain.Market;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MarketRowMapper extends BaseRowMapper implements RowMapper<Market> {

    @Override
    public Market mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapMarket(rs);
    }

}
