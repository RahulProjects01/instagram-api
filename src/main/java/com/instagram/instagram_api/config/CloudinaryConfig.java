package com.instagram.instagram_api.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private final String CLOUD_NAME = "dlghlec5s";  // Replace with your actual cloud name
    private final String API_KEY = "494627686941265";  // Replace with your actual API key
    private final String API_SECRET = "f7HjJEe9kQmhqhRQWqcfRqPsRYw";  // Replace with your actual API secret

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);  // Corrected key
        config.put("api_key", API_KEY);        // Corrected key
        config.put("api_secret", API_SECRET);  // Corrected key
        return new Cloudinary(config);
    }


}
