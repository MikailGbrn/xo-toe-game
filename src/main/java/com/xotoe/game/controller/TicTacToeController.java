package com.xotoe.game.controller;

import com.xotoe.game.dto.GameDataDTO;
import com.xotoe.game.dto.MoveRequest;
import com.xotoe.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TicTacToeController {

    private final GameService gameService;
    private final GameDataDTO gameData;

    @Autowired
    public TicTacToeController(
            GameService gameService,
            GameDataDTO gameData
    ) {
        this.gameService = gameService;
        this.gameData = gameData;
    }

    @GetMapping("/")
    public String index(Model model) {
        gameData.setTileSize(3); // set default value
        model.addAttribute("gameData", gameData);
        return "index";
    }

    /**
     * start the game with tile size data
     *
     * @param gameData
     * @param result
     * @return model and view of the game
     */
    @PostMapping("/start-game")
    public ModelAndView startGame(@ModelAttribute("gameData") GameDataDTO gameData, BindingResult result) {
        this.gameData.setTileSize(gameData.tileSize);
        return this.playGame(gameData.getTileSize());
    }

    /**
     * Player takes turn in the game simultaneously checking for
     * next player and check if the game has ended with winner or tie
     *
     * @param move
     * @return Map of object with string key
     */
    @PostMapping("/make-move")
    @ResponseBody
    public Map<String, Object> makeMove(@RequestBody MoveRequest move) {
        Integer tileSize = gameData.getTileSize();
        Integer winCondition = gameData.getTileSize() > 5 ? 4 : 3;
        gameService.makeMove(move.getRow(), move.getCol(), gameService.getNextPlayer());
        Map<String, Object> response = new HashMap<>();
        response.put("board", gameService.getBoard());
        response.put("nextPlayer", gameService.getNextPlayer());
        response.put("winner", gameService.checkWinner(tileSize, winCondition));
        response.put("draw", gameService.isDraw(tileSize, winCondition));
        return response; // send back updated board
    }

    /**
     * create game's board size with added data
     *
     * @param tileSize
     * @return model and view of the game
     */
    @GetMapping("/play-game")
    public ModelAndView playGame(
            @RequestParam(value = "tileSize") Integer tileSize
    ) {
        return this.xoToe(tileSize);
    }

    /**
     * create game's board size based on tile size
     * and determine the game's rule based on tile size
     *
     * @param tileSize
     * @return model and view of the game
     */
    @RequestMapping({"/play-game"})
    public ModelAndView xoToe(
            @RequestParam("tileSize") Integer tileSize
    ) {
        gameService.setBoard(tileSize);
        ModelAndView modelAndView = new ModelAndView("tac");
        String[][] board = new String[tileSize][tileSize];
        Arrays.stream(board).forEach((row) -> Arrays.fill(row, " "));
        if (tileSize > 5) {
            modelAndView.addObject("gameRule", "Place 4 in a row!");
        } else {
            modelAndView.addObject("gameRule", "Place 3 in a row!");
        }
        modelAndView.addObject("board", board);
        return modelAndView;
    }
}
