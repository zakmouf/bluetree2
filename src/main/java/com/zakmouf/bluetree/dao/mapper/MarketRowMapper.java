package com.zakmouf.bluetree.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.zakmouf.bluetree.domain.Market;

public class MarketRowMapper extends BaseRowMapper implements RowMapper<Market> {

    @Override
    public Market mapRow(ResultSet rs, int rowNum) throws SQLException {
	return mapMarket(rs);
    }

}
