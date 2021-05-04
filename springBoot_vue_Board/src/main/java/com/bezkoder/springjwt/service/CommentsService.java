package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Board;
import com.bezkoder.springjwt.models.Comments;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.CommentsRequest;
import com.bezkoder.springjwt.repository.BoardRepository;
import com.bezkoder.springjwt.repository.CommentsRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentsService {

    private final UserRepository userRepository;
    private final CommentsRepository commentsRepository;
    private final BoardRepository boardRepository;

    public ResponseEntity<String> addComments(Long id, CommentsRequest commentsRequest) {
        Board board = boardRepository.findById(id).get();
        User user = userRepository.findById(commentsRequest.getId()).get();
        Comments comments = Comments.builder()
                .board(board)
                .user(user)
                .comment(commentsRequest.getComment())
                .localDateTime(LocalDateTime.now())
                .build();
        commentsRepository.save(comments);

        return ResponseEntity.ok("댓글이 등록되었습니다.");

    }
}
