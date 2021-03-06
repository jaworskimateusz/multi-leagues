package pl.jaworskimateuszm.myleagues.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jaworskimateuszm.myleagues.mapper.LeagueMapper;
import pl.jaworskimateuszm.myleagues.model.*;

import java.util.List;

@Controller
@RequestMapping("/leagues")
public class LeagueController {

	private LeagueMapper leagueMapper;

	public LeagueController(LeagueMapper leagueMapper) {
		this.leagueMapper = leagueMapper;
	}

	@GetMapping("/list")
	public String getLeagues(Model model) {
		List<League> leagues = leagueMapper.findAll();
		model.addAttribute("leagues", leagues);
		return "/leagues/list-leagues";
	}

	@GetMapping("/disciplines")
	public String getDisciplines(Model model) {
		List<Discipline> disciplines = leagueMapper.findAllDisciplines();
		model.addAttribute("disciplines", disciplines);
		return "/leagues/list-disciplines";
	}

	@GetMapping("/search")
	public String searchLeague(@RequestParam("level") String level,
						 @RequestParam("confirm") int confirm,
						 @RequestParam("playerId") int playerId,
						 Model model) {
		List<League> leagues = leagueMapper.findAllByLevel(level);
		model.addAttribute("leagues", leagues);
		model.addAttribute("confirm", confirm);
		model.addAttribute("playerId", playerId);

		return "/leagues/list-leagues";
	}

	@GetMapping("/search/rank")
	public String searchRank(@RequestParam("surname") String surname,
						 @RequestParam("confirm") int confirm,
						 @RequestParam("playerId") int playerId,
						 Model model) {
		List<Rank> ranks = leagueMapper.findRankBySurname(surname);
		model.addAttribute("ranks", ranks);
		model.addAttribute("confirm", confirm);
		model.addAttribute("playerId", playerId);

		return "/leagues/rank";
	}

	@GetMapping("/rank")
	public String showRank(@RequestParam("leagueId") int leagueId, Model model) {
		List<Rank> ranks = leagueMapper.findLeagueRank(leagueId);
		model.addAttribute("ranks", ranks);
		return "/leagues/rank";
	}

}