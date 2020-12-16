package pl.jaworskimateuszm.myleagues.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jaworskimateuszm.myleagues.mapper.*;
import pl.jaworskimateuszm.myleagues.model.*;
import pl.jaworskimateuszm.myleagues.utils.Parser;

import java.util.Date;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/games")
public class GameController {

	private GameMapper gameMapper;
	private UserMapper userMapper;
	private RoundMapper roundMapper;
	private SetMapper setMapper;

	public GameController(GameMapper gameMapper, UserMapper userMapper, RoundMapper roundMapper, SetMapper setMapper) {
		this.gameMapper = gameMapper;
		this.userMapper = userMapper;
		this.roundMapper = roundMapper;
		this.setMapper = setMapper;
	}

	@GetMapping("/list")
	public String getGames(Model model) {
		List<Game> games = gameMapper.findAll();
		model.addAttribute("games", getGameWithPlayers(games));
		return "/games/list-games";
	}

	@GetMapping("/update")
	public String updateGame(@RequestParam("gameId") int id, Model model) {
		Game game = gameMapper.findById(id);
		List<Round> rounds = roundMapper.findAll();
		List<GameSet> gameSets = setMapper.findAllByGameId(id);
		User firstPlayer = userMapper.findById(game.getFirstPlayerId());
		User secondPlayer = userMapper.findById(game.getSecondPlayerId());
		model.addAttribute("game", game);
		model.addAttribute("firstPlayer", firstPlayer);
		model.addAttribute("secondPlayer", secondPlayer);
		model.addAttribute("secondPlayer", secondPlayer);
		model.addAttribute("rounds", rounds);
		model.addAttribute("gameSets", gameSets);
		model.addAttribute("readWrite", true);
		return "/games/game-form";
	}

	@GetMapping("/search")
	public String searchGame(@RequestParam("place") String place,
						 @RequestParam("dateFrom") String dateFromText,
						 @RequestParam("dateTo") String dateToText,
						 Model model) {
		List<Game> games;
		Date dateFrom = Parser.stringToDate(dateFromText);
		Date dateTo = Parser.stringToDate(dateToText);
		if (!place.isEmpty())
			games = gameMapper.findAllByPlace(place);
		else
			games = gameMapper.findAll();
		if (dateFrom != null)
			games = games.stream().filter(game -> game.getGameDate().after(dateFrom)).collect(Collectors.toList());
		if (dateTo != null)
			games = games.stream().filter(game -> game.getGameDate().before(dateTo)).collect(Collectors.toList());

		model.addAttribute("games", getGameWithPlayers(games));
		return "/games/list-games";
	}

	@GetMapping("/update-set")
	public String updateSet(@RequestParam("gameId") int gameId, @RequestParam("gameSetId") int gameSetId, Model model) {
		GameSet gameSet = setMapper.findById(gameSetId);
		gameSet.setGameId(gameId);
		model.addAttribute("gameSet", gameSet);
		model.addAttribute("gameId", gameId);
		return "/games/set-form";
	}


	@GetMapping("/detail")
	public String showGameDetail(@RequestParam("gameId") int id, Model model) {
		Game game = gameMapper.findById(id);
		Round rounds = roundMapper.findById(game.getRoundId());
		List<GameSet> gameSets = setMapper.findAllByGameId(id);
		User firstPlayer = userMapper.findById(game.getFirstPlayerId());
		User secondPlayer = userMapper.findById(game.getSecondPlayerId());
		model.addAttribute("game", game);
		model.addAttribute("firstPlayer", firstPlayer);
		model.addAttribute("secondPlayer", secondPlayer);
		model.addAttribute("secondPlayer", secondPlayer);
		model.addAttribute("rounds", rounds);
		model.addAttribute("gameSets", gameSets);
		model.addAttribute("readWrite", false);
		return "/games/game-form";
	}

	private List<GameWithPlayersView> getGameWithPlayers(List<Game> games) {
		List<GameWithPlayersView> gamesWithPlayers = new ArrayList<>();
		games.forEach(game -> {
			int firstPlayerId = game.getFirstPlayerId();
			int secondPlayerId = game.getSecondPlayerId();
			gamesWithPlayers.add(new GameWithPlayersView(
					game.getGameId(),
					game.getRoundId(),
					game.getFirstPlayerScore(),
					game.getSecondPlayerScore(),
					game.getFirstPlayerId(),
					game.getSecondPlayerId(),
					game.getGameDate(),
					game.getPlace(),
					firstPlayerId != 0 ?
							userMapper.findById(firstPlayerId) != null ? userMapper.findById(firstPlayerId).getName() : ""
							: "",
					firstPlayerId != 0 ?
							userMapper.findById(firstPlayerId) != null ? userMapper.findById(firstPlayerId).getSurname() : ""
									: "",
					secondPlayerId != 0 ?
							userMapper.findById(secondPlayerId) != null ? userMapper.findById(secondPlayerId).getName() : ""
							: "",
					secondPlayerId != 0 ?
							userMapper.findById(secondPlayerId) != null ? userMapper.findById(secondPlayerId).getSurname() : ""
							: ""
			));
		});
		return gamesWithPlayers;
	}

}