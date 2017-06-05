package com.zakmouf.bluetree.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerRowMapper extends BaseRowMapper implements RowMapper<Integer> {

    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return getInteger(rs, 1);
    }

}
