package pl.jaworskimateuszm.myleagues.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jaworskimateuszm.myleagues.mapper.DisciplineMapper;
import pl.jaworskimateuszm.myleagues.mapper.LeagueMapper;
import pl.jaworskimateuszm.myleagues.mapper.RoundMapper;
import pl.jaworskimateuszm.myleagues.mapper.SeasonMapper;
import pl.jaworskimateuszm.myleagues.model.*;
import pl.jaworskimateuszm.myleagues.utils.Parser;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/leagues")
public class LeagueController {

	private LeagueMapper leagueMapper;
	private DisciplineMapper disciplineMapper;

	public LeagueController(LeagueMapper leagueMapper, DisciplineMapper disciplineMapper) {
		this.leagueMapper = leagueMapper;
		this.disciplineMapper = disciplineMapper;
	}

	@GetMapping("/list")
	public String listLeagues(Model model) {
		List<League> leagues = leagueMapper.findAll();
		model.addAttribute("leagues", leagues);
		return "/leagues/list-leagues";
	}

	@GetMapping("/disciplines")
	public String listDisciplines(Model model) {
		List<Discipline> disciplines = disciplineMapper.findAll();
		model.addAttribute("disciplines", disciplines);
		return "/leagues/list-disciplines";
	}

	@GetMapping("/search")
	public String search(@RequestParam("level") String level,
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