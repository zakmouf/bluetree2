package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zakmouf.bluetree.dao.UserDao;
import com.zakmouf.bluetree.dao.mapper.ProfileRowMapper;
import com.zakmouf.bluetree.dao.mapper.UserRowMapper;
import com.zakmouf.bluetree.domain.Profile;
import com.zakmouf.bluetree.domain.User;

@Component
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    @Override
    public User findUser(Long id) {
	String sql = "select * from users where user_id=?";
	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	return queryForObject(sql, args, argTypes, new UserRowMapper());
    }

    @Override
    public User findUser(String login) {
	String sql = "select * from users where user_login=?";
	Object[] args = { login };
	int[] argTypes = { Types.VARCHAR };
	return queryForObject(sql, args, argTypes, new UserRowMapper());
    }

    @Override
    public List<User> getUsers() {
	String sql = "select * from users order by user_login";
	Object[] args = {};
	int[] argTypes = {};
	return queryForList(sql, args, argTypes, new UserRowMapper());
    }

    @Override
    public void insertUser(User user) {
	String sql = "insert into users values (null, ?, ?, ?)";
	Object[] args = { user.getLogin(), user.getPassword(), user.getMail() };
	int[] argTypes = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
	insert(sql, args, argTypes, user);
    }

    @Override
    public void updateUser(User user) {
	String sql = "update users set user_password=?, user_mail=? where user_id=?";
	Object[] args = { user.getPassword(), user.getMail(), user.getId() };
	int[] argTypes = { Types.VARCHAR, Types.VARCHAR, Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    public List<Profile> getProfiles(User user) {
	String sql = "select * from user_lnk_profile, profiles where ulp_user_id=? and ulp_profile_id=profile_id";
	Object[] args = { user.getId() };
	int[] argTypes = { Types.NUMERIC };
	return queryForList(sql, args, argTypes, new ProfileRowMapper());
    }

    @Override
    public void setProfiles(User user, List<Profile> profiles) {
	//
	String sql = "delete from user_lnk_profile where ulp_user_id=?";
	Object[] args = { user.getId() };
	int[] argTypes = { Types.NUMERIC };
	update(sql, args, argTypes);
	//
	for (Profile profile : profiles) {
	    sql = "insert into user_lnk_profile values (?, ?)";
	    args = new Object[] { user.getId(), profile.getId() };
	    argTypes = new int[] { Types.NUMERIC, Types.NUMERIC };
	    update(sql, args, argTypes);
	}
    }

}
