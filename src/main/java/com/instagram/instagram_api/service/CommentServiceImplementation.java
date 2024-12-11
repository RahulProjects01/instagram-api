package com.instagram.instagram_api.service;

import com.instagram.instagram_api.dto.UserDto;
import com.instagram.instagram_api.exceptions.CommentException;
import com.instagram.instagram_api.exceptions.PostException;
import com.instagram.instagram_api.exceptions.UserException;
import com.instagram.instagram_api.modal.Comment;
import com.instagram.instagram_api.modal.Post;
import com.instagram.instagram_api.modal.User;
import com.instagram.instagram_api.repository.CommentRepository;
import com.instagram.instagram_api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {

        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.setUser(userDto);
        comment.setCreatedAt(LocalDateTime.now());
        Comment createdComment = commentRepository.save(comment);

        post.getComments().add(createdComment);

        postRepository.save(post);
        return createdComment;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws CommentException {

        Optional<Comment> opt = commentRepository.findById(commentId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new CommentException("Comment is not exist with id: " + commentId);
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException {

        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.getLikesByUsers().add(userDto);

        return commentRepository.save(comment);
    }

    @Override
    public Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException {

        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        comment.getLikesByUsers().remove(userDto);
        return commentRepository.save(comment);
    }
}
