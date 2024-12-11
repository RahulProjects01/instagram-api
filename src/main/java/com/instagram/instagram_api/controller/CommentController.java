package com.instagram.instagram_api.controller;

import com.instagram.instagram_api.exceptions.CommentException;
import com.instagram.instagram_api.exceptions.PostException;
import com.instagram.instagram_api.exceptions.UserException;
import com.instagram.instagram_api.modal.Comment;
import com.instagram.instagram_api.modal.User;
import com.instagram.instagram_api.service.CommentService;
import com.instagram.instagram_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

        @PostMapping("/create/{postId}")
    public ResponseEntity<Comment> createCommentHandler(@PathVariable Integer postId, @RequestBody Comment comment, @RequestHeader("Authorization") String token) throws UserException, PostException {

        User user = userService.findUserProfile(token);
        Comment createdComment = commentService.createComment(comment, postId, user.getId());
        return new ResponseEntity<Comment>(createdComment, HttpStatus.OK);
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<Comment> likeCommentHandler(@RequestHeader("Authorization") String token, @PathVariable Integer commentId) throws UserException, CommentException {

        User user = userService.findUserProfile(token);
        Comment comment = commentService.likeComment(commentId, user.getId());

        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

    @PutMapping("/unlike/{commentId}")
    public ResponseEntity<Comment> unLikeCommentHandler(@RequestHeader("Authorization") String token, @PathVariable Integer commentId) throws UserException, CommentException {

        User user = userService.findUserProfile(token);
        Comment comment = commentService.unlikeComment(commentId, user.getId());

        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

}
