package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Board;
import com.bezkoder.springjwt.payload.request.BoardRequest;
import com.bezkoder.springjwt.payload.response.AllBoardResponse;
import com.bezkoder.springjwt.payload.response.BoardResponse;
import com.bezkoder.springjwt.repository.BoardRepository;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import com.bezkoder.springjwt.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    public final BoardRepository boardRepository;

    public final BoardService boardService;

    @GetMapping("/getall")
    public Result getAllBoard() {
        List<Board> all = boardRepository.findAll();
        List<AllBoardResponse> collect = all.stream()
                .map(m -> new AllBoardResponse(m.getId(),m.getUser().getUsername(),m.getSubject(),m.getText(),m.getViewCount(),m.getLocalDateTime()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @PostMapping("/addboard")
    public ResponseEntity<?> addBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @Valid @RequestBody BoardRequest boardRequest) {


        return boardService.addBoard(userDetails, boardRequest);
    }

    @GetMapping("/getoneboard/{boardId}")
    public ResponseEntity<?> getOneBoard(@PathVariable("boardId") Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        boardService.countView(board);
        AllBoardResponse allBoardResponse = new AllBoardResponse(board.getId(),board.getUser().getUsername(),board.getSubject(),board.getText(),board.getViewCount(),board.getLocalDateTime());

        return new ResponseEntity<>(allBoardResponse,HttpStatus.OK);
    }

    @PutMapping("/getoneboard")
    public ResponseEntity<?> modifyBoard(@Valid @RequestBody BoardResponse boardResponse) {
        boardService.modifyBoard(boardResponse);

        return ResponseEntity.ok("등록됨");
    }

    @DeleteMapping("/getoneboard/{boardId}")
    public void deleteOneBoard(@PathVariable("boardId") Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
