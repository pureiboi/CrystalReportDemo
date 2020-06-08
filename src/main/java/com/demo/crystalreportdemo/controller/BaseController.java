package com.demo.crystalreportdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class BaseController {

	private static final Logger logger = LogManager.getLogger(BaseController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	public String getBuddleMessage(String messageKey) {
		return getBuddleMessage(messageKey, null);
	}
	
	public String getBuddleMessage(String messageKey, Object[] args) {
		try {
			return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
		}
		catch(NoSuchMessageException ex) {
			logger.error(ex.getLocalizedMessage(), ex);
		}
		return messageKey;
	}
}
