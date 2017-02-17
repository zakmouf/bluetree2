package com.zakmouf.bluetree.dao.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.zakmouf.bluetree.dao.mapper.DateRowMapper;
import com.zakmouf.bluetree.dao.mapper.IntegerRowMapper;
import com.zakmouf.bluetree.domain.BaseEntity;

public abstract class BaseDaoImpl {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String msg(String pattern, Object... arguments) {
	return MessageFormat.format(pattern, arguments);
    }

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    protected PreparedStatementCreator newPreparedStatementCreator(String sql, Object[] args, int[] argTypes) {
	PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, argTypes);
	factory.setReturnGeneratedKeys(true);
	PreparedStatementCreator creator = factory.newPreparedStatementCreator(args);
	return creator;
    }

    protected Integer queryForInteger(String sql, Object[] args, int[] argTypes) throws DataAccessException {
	List<Integer> list = queryForList(sql, args, argTypes, new IntegerRowMapper());
	return list.isEmpty() ? null : list.get(0);
    }

    protected Date queryForDate(String sql, Object[] args, int[] argTypes) throws DataAccessException {
	List<Date> list = queryForList(sql, args, argTypes, new DateRowMapper());
	return list.isEmpty() ? null : list.get(0);
    }

    public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) {
	List<T> list = queryForList(sql, args, argTypes, rowMapper);
	return list.isEmpty() ? null : list.get(0);
    }

    protected <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
	    throws DataAccessException {
	return jdbcTemplate.query(sql, args, argTypes, rowMapper);
    }

    protected int insert(String sql, Object[] args, int[] argTypes, BaseEntity entity) throws DataAccessException {
	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, argTypes);
	factory.setReturnGeneratedKeys(true);
	PreparedStatementCreator creator = factory.newPreparedStatementCreator(args);
	int result = jdbcTemplate.update(creator, keyHolder);
	entity.setId(keyHolder.getKey().longValue());
	return result;
    }

    protected int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {
	int result = jdbcTemplate.update(sql, args, argTypes);
	return result;
    }

}
