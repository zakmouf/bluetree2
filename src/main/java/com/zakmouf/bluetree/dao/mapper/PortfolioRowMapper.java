package com.zakmouf.bluetree.dao.mapper;

import com.zakmouf.bluetree.domain.Portfolio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PortfolioRowMapper extends BaseRowMapper implements RowMapper<Portfolio> {

    @Override
    public Portfolio mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapPortfolio(rs);
    }

}
