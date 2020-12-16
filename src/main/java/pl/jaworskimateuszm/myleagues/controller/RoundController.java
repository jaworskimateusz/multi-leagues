package pl.jaworskimateuszm.myleagues.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jaworskimateuszm.myleagues.mapper.RoundMapper;
import pl.jaworskimateuszm.myleagues.model.Round;
import pl.jaworskimateuszm.myleagues.utils.Parser;

@Controller
@RequestMapping("/rounds")
public class RoundController {

	private RoundMapper roundMapper;

	public RoundController(RoundMapper roundMapper) {
		this.roundMapper = roundMapper;
	}

	@GetMapping("/list")
	public String getRounds(Model model) {
		List<Round> rounds = roundMapper.findAll();
		model.addAttribute("rounds", rounds);
		return "/rounds/list-rounds";
	}

	@GetMapping("/search")
	public String searchRound(@RequestParam("number") String num,
						 @RequestParam("confirm") int confirm,
						 @RequestParam("playerId") int playerId,
						 Model model,
						 RedirectAttributes redirectAttributes) {
		int number = Parser.stringToInt(num);
		if (number == -1) {
			redirectAttributes.addFlashAttribute("error", true);
			return "redirect:/rounds/list";
		}
		List<Round> rounds = roundMapper.findAllByNumber(number);
		model.addAttribute("rounds", rounds);
		model.addAttribute("confirm", confirm);
		model.addAttribute("playerId", playerId);

		return "/rounds/list-rounds";
	}
}