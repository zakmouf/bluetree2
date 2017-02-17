package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zakmouf.bluetree.dao.ProfileDao;
import com.zakmouf.bluetree.dao.mapper.ProfileRowMapper;
import com.zakmouf.bluetree.domain.Profile;

@Component
public class ProfileDaoImpl extends BaseDaoImpl implements ProfileDao {

    @Override
    public Profile findProfile(Long id) {
	String sql = "select * from profiles where profile_id=?";
	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	return queryForObject(sql, args, argTypes, new ProfileRowMapper());
    }

    @Override
    public Profile findProfile(String name) {
	String sql = "select * from profiles where profile_name=?";
	Object[] args = { name };
	int[] argTypes = { Types.VARCHAR };
	return queryForObject(sql, args, argTypes, new ProfileRowMapper());
    }

    @Override
    public List<Profile> getProfiles() {
	String sql = "select * from profiles order by profile_name";
	Object[] args = {};
	int[] argTypes = {};
	return queryForList(sql, args, argTypes, new ProfileRowMapper());
    }

    @Override
    public void insertProfile(Profile profile) {
	String sql = "insert into profiles values (null, ?, 0)";
	Object[] args = { profile.getName() };
	int[] argTypes = { Types.VARCHAR };
	insert(sql, args, argTypes, profile);
    }

    @Override
    public void updateProfile(Profile profile) {
	String sql = "update profiles set profile_name=? where profile_id=?";
	Object[] args = { profile.getName(), profile.getId() };
	int[] argTypes = { Types.VARCHAR, Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    public void deleteProfile(Profile profile) {
	//
	String sql = "";
	Object[] args = { profile.getId() };
	int[] argTypes = { Types.NUMERIC };
	//
	sql = "select count(*) from market_lnk_profile where mlp_profile_id=?";
	if (queryForInteger(sql, args, argTypes) > 0) {
	    return;
	}
	//
	sql = "select count(*) from user_lnk_profile where ulp_profile_id=?";
	if (queryForInteger(sql, args, argTypes) > 0) {
	    return;
	}
	//
	sql = "select count(*) from profiles where profile_id=? and profile_default=1";
	if (queryForInteger(sql, args, argTypes) > 0) {
	    return;
	}
	//
	sql = "delete from profiles where profile_id=?";
	update(sql, args, argTypes);
    }

    @Override
    public Profile getDefaultProfile() {
	String sql = "select * from profiles where profile_default=1";
	Object[] args = {};
	int[] argTypes = {};
	return queryForObject(sql, args, argTypes, new ProfileRowMapper());
    }

    @Override
    public void setDefaultProfile(Profile profile) {
	//
	String sql = "update profiles set profile_default=0";
	Object[] args = {};
	int[] argTypes = {};
	update(sql, args, argTypes);
	//
	sql = "update profiles set profile_default=1 where profile_id=?";
	args = new Object[] { profile.getId() };
	argTypes = new int[] { Types.NUMERIC };
	update(sql, args, argTypes);
    }

}