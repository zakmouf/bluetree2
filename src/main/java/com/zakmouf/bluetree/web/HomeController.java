package com.zakmouf.bluetree.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView gtHome() {
	ModelAndView mav = new ModelAndView("home");
	return mav;
    }

}
