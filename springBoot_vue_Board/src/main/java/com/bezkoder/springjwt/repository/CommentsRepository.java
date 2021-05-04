package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments,Long> {
}
