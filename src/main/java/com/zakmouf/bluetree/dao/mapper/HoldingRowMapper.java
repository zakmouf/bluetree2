package com.zakmouf.bluetree.dao.mapper;

import com.zakmouf.bluetree.domain.Holding;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HoldingRowMapper extends BaseRowMapper implements RowMapper<Holding> {

    @Override
    public Holding mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapHolding(rs);
    }

}
