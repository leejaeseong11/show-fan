package com.kosa.showfan.show.controller;

import com.kosa.showfan.controller.Controller;
import com.kosa.showfan.show.service.ShowService;

public abstract class ShowController implements Controller {
	protected ShowService service;
	
	public ShowController() {
		service = ShowService.getInstance();
	}
}
