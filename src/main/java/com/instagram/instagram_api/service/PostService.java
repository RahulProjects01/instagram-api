package com.instagram.instagram_api.service;

import com.instagram.instagram_api.exceptions.PostException;
import com.instagram.instagram_api.exceptions.UserException;
import com.instagram.instagram_api.modal.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostService {

    public Post createPost(String caption, String location, String image, Integer userId) throws UserException;

    public String deletePost(Integer postId, Integer userId) throws UserException, PostException;

    public List<Post> findPostByUserId(Integer userId) throws UserException;

    public Post findPostById(Integer postId) throws PostException;

    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException;

    public String savedPost(Integer postId, Integer userId) throws UserException, PostException;

    public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException;

    public Post likePost(Integer PostId, Integer userId) throws UserException, PostException;

    public Post unLikePost(Integer PostId, Integer userId) throws UserException, PostException;
}
