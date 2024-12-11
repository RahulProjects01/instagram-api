package com.instagram.instagram_api.repository;

import com.instagram.instagram_api.modal.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
