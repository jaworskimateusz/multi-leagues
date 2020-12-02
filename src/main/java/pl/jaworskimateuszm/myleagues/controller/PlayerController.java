package pl.jaworskimateuszm.myleagues.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jaworskimateuszm.myleagues.mapper.LeagueMapper;
import pl.jaworskimateuszm.myleagues.mapper.PlayerMapper;
import pl.jaworskimateuszm.myleagues.mapper.UserMapper;
import pl.jaworskimateuszm.myleagues.model.League;
import pl.jaworskimateuszm.myleagues.model.Player;
import pl.jaworskimateuszm.myleagues.model.PlayerDetail;
import pl.jaworskimateuszm.myleagues.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/players")
public class PlayerController {

	private UserMapper userMapper;
	private LeagueMapper leagueMapper;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	public PlayerController(UserMapper userMapper, LeagueMapper leagueMapper) {
		this.userMapper = userMapper;
		this.leagueMapper = leagueMapper;
	}

	@GetMapping("/list")
	public String listPlayers(Model model) {
		List<User> players = userMapper.findAllByRole("PLAYER");
		model.addAttribute("players", players);
		return "/players/list-players";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("player", new User());
		List<League> leagues = leagueMapper.findAll();
		model.addAttribute("leagues", leagues);
		return "/players/player-form";
	}

	@GetMapping("/update")
	public String update(@RequestParam("playerId") int id, Model model) {
		User player = userMapper.findById(id);
		List<League> leagues = leagueMapper.findAll();
		model.addAttribute("leagues", leagues);
		model.addAttribute("player", player);
		return "/players/player-form";
	}

	@GetMapping("/detail")
	public String detail(@RequestParam("playerId") int id, Model model) {
		User player = userMapper.findById(id);
		List<PlayerDetail> details = userMapper.findAllDetailById(id);
		model.addAttribute("player", player);
		model.addAttribute("details", details);
		return "/players/player-detail";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("player") User player, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", true);
			return "redirect:/players/add";
		}
		if (userMapper.findById(player.getUserId()) != null) {
			userMapper.update(player);
			Arrays.stream(player.getLeagueIds()).forEach(leagueId -> userMapper.updatePlayerLeague(player.getUserId(), leagueId));
		} else {
			userMapper.insert(player);
			Arrays.stream(player.getLeagueIds()).forEach(leagueId -> userMapper.insertPlayerLeague(player.getUserId(), leagueId));
		}

		return "redirect:/players/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("playerId") int playerId) {
		userMapper.deletePlayerLeagueById(playerId);
		userMapper.deleteByIdFromAuthorities(userMapper.findById(playerId).getUsername());
		userMapper.deleteById(playerId);
		return "redirect:/login";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("pesel") String pesel,
						 @RequestParam("gameId") int gameId,
						 @RequestParam("whichOne") String whichOne,
						 Model model) {
		List<User> players = userMapper.searchBy(pesel);
		model.addAttribute("players", players);
		model.addAttribute("gameId", gameId);
		model.addAttribute("whichOne", whichOne.equals("") ? null : whichOne);
		return "/players/list-players";
	}

	@GetMapping("/choose-player")
	public String choosePlayer(@RequestParam("gameId") int gameId, @RequestParam("whichOne") String whichOne, Model model) {
		List<User> players = userMapper.findAllByRole("PLAYER");
		model.addAttribute("gameId", gameId);
		model.addAttribute("players", players);
		model.addAttribute("whichOne", whichOne);
		return "/players/list-players";
	}

	@GetMapping("/user-data")
	public String updateUserData(HttpServletRequest request, Model model) {
		User player = userMapper.findByUsername(request.getRemoteUser());
		model.addAttribute("player", player);
		return "/users/user-form-user-data";
	}

	@PostMapping("/save-user-data")
	public String saveUserData(@Valid @ModelAttribute("player") User player, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", true);
			return "redirect:/players/user-data";
		}
		userMapper.update(player);
		return "redirect:/players/list";
	}

	@GetMapping("/login-data")
	public String updateLoginData(HttpServletRequest request, Model model) {
		User player = userMapper.findByUsername(request.getRemoteUser());
		model.addAttribute("player", player);
		return "/users/user-form-login-data";
	}

	@PostMapping("/save-login-data")
	public String saveLoginData(@Valid @ModelAttribute("player") User player, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors() ||
			!BCrypt.checkpw(player.getActualPassword(), player.getPassword()) ||
			!player.getNewPassword().equals(player.getRepeatNewPassword())
		) {
			redirectAttributes.addFlashAttribute("error", true);
			return "redirect:/players/login-data";
		}
		player.setPassword(bCryptPasswordEncoder.encode(player.getNewPassword()));
		userMapper.update(player);
		return "redirect:/players/list";
	}

}