package com.boc.crystalreportdemo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.boc.crystalreportdemo.domain.User;

@Controller
@SessionAttributes("userInfo")
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(Model model) {

		User user = (User) model.getAttribute("userInfo");
		if(user == null) {
			model.addAttribute("errMsg", "系统需要登入才能使用");
			return "forward:/";
		}
		else {
			model.addAttribute("errMsg", "你去到了不存在的页面， 或系统崩溃了");
			return "forward:/welcome";
		}
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}