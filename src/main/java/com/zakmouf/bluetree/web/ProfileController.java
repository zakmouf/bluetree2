package com.zakmouf.bluetree.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zakmouf.bluetree.dao.ProfileDao;
import com.zakmouf.bluetree.domain.Profile;

@Controller
public class ProfileController extends BaseController {

    private ProfileDao profileDao;

    @RequestMapping(value = "/admin/profile", method = RequestMethod.GET)
    public String getList(ModelMap model) {
	List<Profile> profiles = profileDao.getProfiles();
	Profile defaultProfile = profileDao.getDefaultProfile();
	model.put("profiles", profiles);
	model.put("defaultProfile", defaultProfile);
	return "admin/profile/list";
    }

    @RequestMapping(value = "/admin/profile/default", method = RequestMethod.GET)
    public String setDefault(@RequestParam("profile") Long profileId) throws Exception {
	Profile profile = profileDao.findProfile(profileId);
	logger.info(msg("profile default <{0}>", profile));
	profileDao.setDefaultProfile(profile);
	return "redirect:/admin/profile";
    }

    @RequestMapping(value = "/admin/profile/new", method = RequestMethod.GET)
    public String getNew() throws Exception {
	return "/admin/profile/new";
    }

}
