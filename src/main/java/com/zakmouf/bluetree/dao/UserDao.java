package com.zakmouf.bluetree.dao;

import java.util.List;

import com.zakmouf.bluetree.domain.Profile;
import com.zakmouf.bluetree.domain.User;

public interface UserDao {

    User findUser(Long id);

    User findUser(String login);

    List<User> getUsers();

    void insertUser(User user);

    void updateUser(User user);

    List<Profile> getProfiles(User user);

    void setProfiles(User user, List<Profile> profiles);

}