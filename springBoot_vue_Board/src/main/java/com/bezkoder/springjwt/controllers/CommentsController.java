package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Board;
import com.bezkoder.springjwt.models.Comments;
import com.bezkoder.springjwt.payload.request.CommentsRequest;
import com.bezkoder.springjwt.payload.response.AllBoardResponse;
import com.bezkoder.springjwt.payload.response.CommentsResponse;
import com.bezkoder.springjwt.repository.BoardRepository;
import com.bezkoder.springjwt.repository.CommentsRepository;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import com.bezkoder.springjwt.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class CommentsController {

    private final CommentsService commentsService;
    private final CommentsRepository commentsRepository;
    private final BoardRepository boardRepository;

    @PostMapping("/comments/{id}")
    public ResponseEntity<?> comments(@PathVariable("id") Long id,
                                      @RequestBody CommentsRequest commentsRequest) {

       return ResponseEntity.ok(commentsService.addComments(id,commentsRequest));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentsResponse>> findAllComments(@PathVariable("id") Long id) {
        Board board = boardRepository.findById(id).get();
        List<Comments> comments = board.getComments();
        List<CommentsResponse> collect = comments.stream()
                .map(m -> new CommentsResponse(m.getUser().getUsername(),m.getComment(),m.getLocalDateTime()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }
}
