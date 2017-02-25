package com.zakmouf.bluetree.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zakmouf.bluetree.dao.ProfileDao;
import com.zakmouf.bluetree.domain.Profile;

@Controller
@RequestMapping("/admin/profile")
public class ProfileController extends BaseController {

    private ProfileDao profileDao;

    @Autowired
    public void setProfileDao(ProfileDao profileDao) {
	this.profileDao = profileDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getList() {
	List<Profile> profiles = profileDao.getProfiles();
	ModelAndView mav = new ModelAndView("admin/profile/list");
	mav.getModel().put("profiles", profiles);
	return mav;
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public ModelAndView setDefault(@RequestParam("profile") Long profileId) {
	Profile profile = profileDao.findProfile(profileId);
	logger.info(msg("profile default <{0}>", profile));
	profileDao.setDefaultProfile(profile);
	ModelAndView mav = new ModelAndView("redirect:/admin/profile");
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNew() {
	ProfileForm form = new ProfileForm();
	form.setName("name");
	ModelAndView mav = new ModelAndView("/admin/profile/new");
	mav.getModel().put("profile", form);
	return mav;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView submitNew(@ModelAttribute ProfileForm form) {
	String name = form.getName();
	if (profileDao.findProfile(name) != null) {
	    ModelAndView mav = new ModelAndView("/admin/profile/new");
	    mav.getModel().put("form", form);
	    return mav;
	}
	Profile profile = new Profile();
	profile.setName(name);
	profileDao.insertProfile(profile);
	ModelAndView mav = new ModelAndView("redirect:/admin/profile");
	return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam("profile") Long profileId) {
	Profile profile = profileDao.findProfile(profileId);
	ProfileForm form = new ProfileForm();
	form.setName(profile.getName());
	ModelAndView mav = new ModelAndView("/admin/profile/edit");
	mav.getModel().put("profile", form);
	return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView submitEdit(@RequestParam("profile") Long profileId, @ModelAttribute ProfileForm form) {
	String name = form.getName();
	Profile profile = profileDao.findProfile(name);
	if (profile != null) {
	    if (!profile.getId().equals(profileId)) {
		ModelAndView mav = new ModelAndView("/admin/profile/edit");
		mav.getModel().put("form", form);
		return mav;
	    }
	}
	profile = profileDao.findProfile(profileId);
	profile.setName(name);
	logger.info(msg("update profile <{0}>", profile));
	profileDao.updateProfile(profile);
	ModelAndView mav = new ModelAndView("redirect:/admin/profile");
	return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView doDelete(@RequestParam("profile") Long profileId) {
	Profile profile = profileDao.findProfile(profileId);
	logger.info(msg("delete profile <{0}>", profile));
	profileDao.deleteProfile(profile);
	ModelAndView mav = new ModelAndView("redirect:/admin/profile");
	return mav;
    }

}
