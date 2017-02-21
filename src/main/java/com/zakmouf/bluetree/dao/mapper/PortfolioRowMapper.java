package com.zakmouf.bluetree.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.zakmouf.bluetree.domain.Portfolio;

public class PortfolioRowMapper extends BaseRowMapper implements RowMapper<Portfolio> {

    @Override
    public Portfolio mapRow(ResultSet rs, int rowNum) throws SQLException {
	return mapPortfolio(rs);
    }

}