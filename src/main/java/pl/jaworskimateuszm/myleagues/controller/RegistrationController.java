package pl.jaworskimateuszm.myleagues.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jaworskimateuszm.myleagues.mapper.UserMapper;
import pl.jaworskimateuszm.myleagues.model.NewUser;
import pl.jaworskimateuszm.myleagues.model.Player;
import pl.jaworskimateuszm.myleagues.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;

@Controller
public class RegistrationController {

	private UserMapper userMapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public RegistrationController(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@GetMapping("/register")
	public String showRegistrationPage(Model model) {
		model.addAttribute("user", new NewUser());
		return "register";
	}

	@PostMapping("/register/save")
	public String save(@Valid @ModelAttribute("user") NewUser newUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors() ||
			userMapper.findByUsername(newUser.getUsername()) != null ||
			!newUser.getPassword().equals(newUser.getRepeatedPassword())
		) {
			redirectAttributes.addFlashAttribute("error", true);
			return "redirect:/register";
		}
		userMapper.insert(new User(
				newUser.getUsername(),
				passwordEncoder.encode(newUser.getPassword()),
				1,
				"PLAYER",
				newUser.getName(),
				newUser.getSurname(),
				newUser.getPesel(),
				newUser.getPhoneNumber()
		));
		userMapper.insertAuthority(newUser.getUsername(), "ROLE_PLAYER");
		return "login";
	}
	
}