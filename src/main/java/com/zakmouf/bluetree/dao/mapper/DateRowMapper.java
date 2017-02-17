package com.zakmouf.bluetree.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class DateRowMapper extends BaseRowMapper implements RowMapper<Date> {

    @Override
    public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
	return mapDate(rs, rowNum);
    }

}
