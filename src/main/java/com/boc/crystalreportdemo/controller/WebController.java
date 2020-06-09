package com.boc.crystalreportdemo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.boc.crystalreportdemo.domain.User;
import com.boc.crystalreportdemo.service.UserService;

@Controller
@SessionAttributes("userInfo")
public class WebController {

	private static final Logger logger = LogManager.getLogger(WebController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String prepareLogin(Model model) {
		logger.info("prepareLogin");

		User user = (User) model.getAttribute("userInfo");

		model.addAttribute("fullName", user.getFullName());
		model.addAttribute("userInfo", user);
		return "welcome";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(@ModelAttribute User userObj, Model model) {

		logger.info("model:: " + model);
		logger.info("userObj:: " + userObj);
		try {
			User user = userService.getUserByEntity(userObj);

			model.addAttribute("fullName", user.getFullName());
			model.addAttribute("userInfo", user);
			return "welcome";
		} catch (NullPointerException ex) {
			logger.error("NullPointerException while trying to locate user", ex);
			model.addAttribute("errMsg", "用户不存在");
			model.addAttribute("model", new User());
		}

		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String doLogout(Model model, SessionStatus status) {
		logger.info("navigate to logout");

		model.addAttribute("userInfo", null);
		status.setComplete();
		return "forward:/";
	}

	@RequestMapping("/")
	public String index(Model model) {
		logger.info("index method");
		model.addAttribute("model", new User());
		return "login";
	}

}
