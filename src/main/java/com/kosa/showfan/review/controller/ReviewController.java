package com.kosa.showfan.review.controller;

import com.kosa.showfan.controller.Controller;
import com.kosa.showfan.review.service.ReviewService;

public abstract class ReviewController implements Controller {
	protected ReviewService service;

	public ReviewController() {
		service = ReviewService.getInstance();
	}
}
