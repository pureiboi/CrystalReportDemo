package com.demo.crystalreportdemo.controller;

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

import com.demo.crystalreportdemo.constant.MessageBundle;
import com.demo.crystalreportdemo.domain.User;
import com.demo.crystalreportdemo.service.business.AuthenticationService;

@Controller
@SessionAttributes("userInfo")
public class WebController extends BaseController {

	private static final Logger logger = LogManager.getLogger(WebController.class);

	@Autowired
	private AuthenticationService authService;

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

		logger.info("model:: {}", model);
		logger.info("userObj:: {}", userObj);

		if (authService.isUserExists(userObj.getUserName())) {

			User userInfo = authService.getUserAccount(userObj);

			model.addAttribute("fullName", userInfo.getFullName());
			model.addAttribute("userInfo", userInfo);
			return "welcome";
		} else {
			model.addAttribute("errMsg", getBuddleMessage(MessageBundle.ERR_INVALID_USER));
			prepareData(model);
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

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String index(Model model) {
		logger.info("index method - GET");

		prepareData(model);
		
		return "login";
	}
	
	private void prepareData(Model model) {
		
		model.addAttribute("model", new User());

	}

}
