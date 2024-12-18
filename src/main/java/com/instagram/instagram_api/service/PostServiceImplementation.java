package com.instagram.instagram_api.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.instagram.instagram_api.config.CloudinaryConfig;
import com.instagram.instagram_api.dto.UserDto;
import com.instagram.instagram_api.exceptions.PostException;
import com.instagram.instagram_api.exceptions.UserException;
import com.instagram.instagram_api.modal.Post;
import com.instagram.instagram_api.modal.User;
import com.instagram.instagram_api.repository.PostRepository;
import com.instagram.instagram_api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    public static final Logger logger = LoggerFactory.getLogger(PostService.class);


    @Override
    public Post createPost(String caption, String location, String image, Integer userId) throws UserException {
        // Find the user by ID
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new UserException("User not found for ID: " + userId);
        }

        logger.info("Creating post for user: {}", user.getUsername());

        // Validate the image URL
        if (image == null || image.trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be null or empty.");
        }

        // Create the Post object
        Post post = new Post();
        post.setCaption(caption);        // Set the caption
        post.setLocation(location);      // Set the location
        post.setImage(image);         // Set the image URL directly
        post.setCreatedAt(LocalDateTime.now());  // Set the creation time

        // Set user details
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());
        post.setUser(userDto);

        // Save the post in the database
        logger.info("Saving post for user: {}", user.getUsername());
        return postRepository.save(post);
    }


    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {

        Post post = findPostById(postId);

        User user = userService.findUserById(userId);
        if (post.getUser().getId().equals(user.getId())) {
            postRepository.deleteById(post.getId());
            return "Post Deleted Successfully";
        }
        throw new PostException("You Can't delete other user's post");
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {

        List<Post> posts = postRepository.findByUserId(userId);

        if (posts.size() == 0) {
            throw new UserException("this user does not have any Post");
        }
        return posts;
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> opt = postRepository.findById(postId);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new PostException("Post not found with Id " + postId);
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {

        List<Post> posts = postRepository.findAllPostByUserIds(userIds);

        if (posts.size() == 0) {
            throw new PostException("Post not available");
        }
        return posts;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws UserException, PostException {

        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (!user.getSavedPost().contains(post)) {
            user.getSavedPost().add(post);
            userRepository.save(user);
        }
        return "Post Saved Successfully ";
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
            userRepository.save(user);
        }
        return "Post Remove Successfully ";
    }

    @Override
    public Post likePost(Integer PostId, Integer userId) throws UserException, PostException {

        Post post = findPostById(PostId);
        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.getLikedByUsers().add(userDto);

        return postRepository.save(post);
    }

    @Override
    public Post unLikePost(Integer PostId, Integer userId) throws UserException, PostException {
        Post post = findPostById(PostId);
        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.getLikedByUsers().remove(userDto);

        return postRepository.save(post);
    }


}
