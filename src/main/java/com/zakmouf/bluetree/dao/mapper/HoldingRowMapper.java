package com.zakmouf.bluetree.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.zakmouf.bluetree.domain.Holding;

public class HoldingRowMapper extends BaseRowMapper implements RowMapper<Holding> {

    @Override
    public Holding mapRow(ResultSet rs, int rowNum) throws SQLException {
	return mapHolding(rs);
    }

}
