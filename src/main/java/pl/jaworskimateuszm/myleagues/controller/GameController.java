package pl.jaworskimateuszm.myleagues.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jaworskimateuszm.myleagues.mapper.*;
import pl.jaworskimateuszm.myleagues.model.*;
import pl.jaworskimateuszm.myleagues.utils.Parser;

import javax.validation.Valid;
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
	
	@GetMapping("/add")
	public String showGameForm(Model model) {
		List<Round> rounds = roundMapper.findAll();
		gameMapper.insert(new Game(rounds.stream().findFirst().get().getRoundId(),0,0,0,0, new Date(), "-"));
		int id = gameMapper.findMaxGameId();
		Game game = gameMapper.findById(id);
		model.addAttribute("game", game);
		model.addAttribute("rounds",rounds);
		model.addAttribute("readWrite", true);
		return "/games/game-form";
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
	
	@PostMapping("/save")
	public String saveGame(@Valid @ModelAttribute("game") Game game, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors() || game.getRoundId() == -1) {
			redirectAttributes.addFlashAttribute("error", true);
			return "redirect:/games/add";
		}
		Game found = gameMapper.findById(game.getGameId());
		if (found != null)
			gameMapper.update(game);
		else
			gameMapper.insert(game);

		return "redirect:/games/list";
	}
	
	@GetMapping("/delete")
	public String deleteGame(@RequestParam("gameId") int id) {
		gameMapper.deleteById(id);
		return "redirect:/games/list";
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

	@GetMapping("/add-first-player-to-game")
	public String addFirstPlayerToGame(@RequestParam("playerId") int playerId,
									   @RequestParam("gameId") int gameId,
									   RedirectAttributes redirectAttributes) {
		gameMapper.updateFirstPlayerId(playerId, gameId);
		redirectAttributes.addAttribute("gameId", gameId);
		return "redirect:/games/update";
	}

	@GetMapping("/add-second-player-to-game")
	public String addSecondPlayerToGame(@RequestParam("playerId") int playerId,
										@RequestParam("gameId") int gameId,
										RedirectAttributes redirectAttributes) {
		gameMapper.updateSecondPlayerId(playerId, gameId);
		redirectAttributes.addAttribute("gameId", gameId);
		return "redirect:/games/update";
	}

	@GetMapping("/delete-first-player-from-game")
	public String deleteFirstPlayerFromGame(@RequestParam("playerId") int playerId,
											@RequestParam("gameId") int gameId,
											RedirectAttributes redirectAttributes) {
		gameMapper.updateFirstPlayerId(0, gameId);
		redirectAttributes.addAttribute("gameId", gameId);
		return "redirect:/games/update";
	}

	@GetMapping("/delete-second-player-from-game")
	public String deleteSecondPlayerFromGame(@RequestParam("playerId") int playerId,
											 @RequestParam("gameId") int gameId,
											 RedirectAttributes redirectAttributes) {
		gameMapper.updateSecondPlayerId(0, gameId);
		redirectAttributes.addAttribute("gameId", gameId);
		return "redirect:/games/update";
	}

	@GetMapping("/add-set")
	public String showSetForm(@RequestParam("gameId") int gameId, Model model) {
		GameSet gameSet = new GameSet();
		gameSet.setGameId(gameId);
		model.addAttribute("gameSet", gameSet);
		return "/games/set-form";
	}

	@GetMapping("/update-set")
	public String updateSet(@RequestParam("gameId") int gameId, @RequestParam("gameSetId") int gameSetId, Model model) {
		GameSet gameSet = setMapper.findById(gameSetId);
		gameSet.setGameId(gameId);
		model.addAttribute("gameSet", gameSet);
		model.addAttribute("gameId", gameId);
		return "/games/set-form";
	}

	@PostMapping("/save-set")
	public String saveSet(@ModelAttribute("gameSet") GameSet gameSet, RedirectAttributes redirectAttributes) {
		if (setMapper.findById(gameSet.getGameSetId()) != null) {
			setMapper.update(gameSet);
		} else {
			setMapper.insert(gameSet);
		}
		redirectAttributes.addAttribute("gameId", gameSet.getGameId());
		return "redirect:/games/update";
	}

	@GetMapping("/delete-set")
	public String deleteSet(@RequestParam("gameId") int gameId,
							@RequestParam("gameSetId") int gameSetId,
							RedirectAttributes redirectAttributes) {
		setMapper.deleteById(gameSetId);
		redirectAttributes.addAttribute("gameId", gameId);
		return "redirect:/games/update";
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