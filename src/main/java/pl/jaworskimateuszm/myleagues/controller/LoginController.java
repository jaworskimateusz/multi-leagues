package pl.jaworskimateuszm.myleagues.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jaworskimateuszm.myleagues.mapper.UserMapper;
import pl.jaworskimateuszm.myleagues.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	private UserMapper userMapper;

	public LoginController(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@GetMapping("/home")
	public String showHomePage(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
		User user = userMapper.findByUsername(request.getRemoteUser());
		session.setAttribute("user", user);
		return "redirect:/games/list";
	}

	@GetMapping("/index")
	public String showIndex() {
		return "index";
	}
	
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		return "access-denied";
	}
	
}