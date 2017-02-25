package com.zakmouf.bluetree.dao;

import java.util.List;

import com.zakmouf.bluetree.domain.Profile;

public interface ProfileDao {

    Profile findProfile(Long id);

    Profile findProfile(String name);

    List<Profile> getProfiles();

    void insertProfile(Profile profile);

    void updateProfile(Profile profile);

    void deleteProfile(Profile profile);

    void setDefaultProfile(Profile profile);

}