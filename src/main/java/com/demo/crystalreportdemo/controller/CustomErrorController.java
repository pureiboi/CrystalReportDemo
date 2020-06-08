package com.demo.crystalreportdemo.controller;

import java.util.Locale;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.demo.crystalreportdemo.constant.MessageBundle;
import com.demo.crystalreportdemo.domain.User;

@Controller
@SessionAttributes("userInfo")
public class CustomErrorController extends BaseController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(Model model, Locale locale) {

		User user = (User) model.getAttribute("userInfo");
		if(user == null) {
			model.addAttribute("errMsg", getBuddleMessage(MessageBundle.ERR_INVALID_SESSION));
			return "forward:/";
		}
		else {

			model.addAttribute("errMsg", getBuddleMessage(MessageBundle.ERR_INVALID_PAGE));
			return "forward:/welcome";
		}
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}