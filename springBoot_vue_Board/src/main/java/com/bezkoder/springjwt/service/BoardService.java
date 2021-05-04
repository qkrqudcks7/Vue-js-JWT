package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Board;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.BoardRequest;
import com.bezkoder.springjwt.payload.response.BoardResponse;
import com.bezkoder.springjwt.repository.BoardRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardService {

    public final UserRepository userRepository;

    public final BoardRepository boardRepository;

    public ResponseEntity<String> addBoard(UserDetailsImpl userDetails, BoardRequest boardRequest) {
        User user = userRepository.findById(userDetails.getId()).get();
        Board board = Board.builder()
                .user(user)
                .subject(boardRequest.getSubject())
                .text(boardRequest.getText())
                .localDateTime(LocalDateTime.now())
                .viewCount(0L)
                .build();

        boardRepository.save(board);

        return ResponseEntity.ok("글 작성이 완료되었습니다.");
    }

    public void countView(Board board) {
        board.plusViewCount();

        boardRepository.save(board);
    }

    public ResponseEntity<String> modifyBoard(BoardResponse boardResponse) {
        User user = userRepository.findById(boardResponse.getId()).get();
        Board board = Board.builder()
                .id(boardResponse.getId())
                .subject(boardResponse.getSubject())
                .text(boardResponse.getText())
                .viewCount(boardResponse.getViewCount())
                .user(user)
                .localDateTime(boardResponse.getLocalDateTime()).build();

        boardRepository.save(board);

        return ResponseEntity.ok("글 수정이 완료되었습니다.");
    }
}
