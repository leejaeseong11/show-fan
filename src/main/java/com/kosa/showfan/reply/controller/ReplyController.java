package com.kosa.showfan.reply.controller;

import com.kosa.showfan.controller.Controller;
import com.kosa.showfan.reply.service.ReplyService;

public abstract class ReplyController implements Controller {

	protected ReplyService service;

	public ReplyController() {
		service = service.getInstance();
	}
}
