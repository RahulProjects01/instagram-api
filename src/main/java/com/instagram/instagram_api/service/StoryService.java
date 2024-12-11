package com.instagram.instagram_api.service;

import com.instagram.instagram_api.exceptions.StoryException;
import com.instagram.instagram_api.exceptions.UserException;
import com.instagram.instagram_api.modal.Story;

import java.util.List;

public interface StoryService {

    public Story createStory(Story story, Integer userId) throws UserException;
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;

}
