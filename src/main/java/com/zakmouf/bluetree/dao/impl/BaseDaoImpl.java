package com.zakmouf.bluetree.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.zakmouf.bluetree.dao.mapper.IntegerRowMapper;
import com.zakmouf.bluetree.domain.BaseEntity;

public abstract class BaseDaoImpl {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    public <T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType) {
	try {
	    return jdbcTemplate.queryForObject(sql, args, argTypes, requiredType);
	} catch (EmptyResultDataAccessException ex) {
	    return null;
	}
    }

    public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) {
	try {
	    return jdbcTemplate.queryForObject(sql, args, argTypes, rowMapper);
	} catch (EmptyResultDataAccessException ex) {
	    return null;
	}
    }

    protected <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) {
	return jdbcTemplate.query(sql, args, argTypes, rowMapper);
    }

    private Long currentId = System.currentTimeMillis();

    protected Long getNextId() {
	return currentId++;
    }

    protected int insert(String sql, Object[] args, int[] argTypes) throws DataAccessException {
	int result = jdbcTemplate.update(sql, args, argTypes);
	return result;
    }

    protected int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {
	int result = jdbcTemplate.update(sql, args, argTypes);
	return result;
    }

    protected int delete(String sql, Object[] args, int[] argTypes) throws DataAccessException {
	int result = jdbcTemplate.update(sql, args, argTypes);
	return result;
    }

    ////////

    protected int oldInsert(String sql, Object[] args, int[] argTypes, BaseEntity entity) throws DataAccessException {
	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, argTypes);
	factory.setReturnGeneratedKeys(true);
	PreparedStatementCreator creator = factory.newPreparedStatementCreator(args);
	int result = jdbcTemplate.update(creator, keyHolder);
	entity.setId(keyHolder.getKey().longValue());
	return result;
    }

    protected PreparedStatementCreator newPreparedStatementCreator(String sql, Object[] args, int[] argTypes) {
	PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, argTypes);
	factory.setReturnGeneratedKeys(true);
	PreparedStatementCreator creator = factory.newPreparedStatementCreator(args);
	return creator;
    }

    protected Integer oldQueryForInteger(String sql, Object[] args, int[] argTypes) throws DataAccessException {
	List<Integer> list = queryForList(sql, args, argTypes, new IntegerRowMapper());
	return list.isEmpty() ? null : list.get(0);
    }

}
