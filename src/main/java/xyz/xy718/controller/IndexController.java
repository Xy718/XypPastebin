package xyz.xy718.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

	@GetMapping("/")
	public String indexPage(Model model) {
		return "page";
	}
}
