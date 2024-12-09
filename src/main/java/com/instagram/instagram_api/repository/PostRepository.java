package com.instagram.instagram_api.repository;

import com.instagram.instagram_api.modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    List<Post> findByUserId(@Param("userId") Integer userId);

    // Query for finding posts by a list of user IDs
    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds ORDER BY p.createdAt DESC")
    List<Post> findAllPostByUserIds(@Param("userIds") List<Integer> userIds);

}
