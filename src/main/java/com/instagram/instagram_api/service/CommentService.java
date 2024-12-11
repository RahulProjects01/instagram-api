package com.instagram.instagram_api.service;

import com.instagram.instagram_api.exceptions.CommentException;
import com.instagram.instagram_api.exceptions.PostException;
import com.instagram.instagram_api.exceptions.UserException;
import com.instagram.instagram_api.modal.Comment;

public interface CommentService {

    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException;

    public Comment findCommentById(Integer commentId) throws CommentException;

    public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException;

    public Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException;

}
