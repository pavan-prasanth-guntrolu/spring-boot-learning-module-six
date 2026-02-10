package com.learning.module_6.utils;

import com.learning.module_6.dto.PostDTO;
import com.learning.module_6.entities.UserEntity;
import com.learning.module_6.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurityService {
    private final PostService postService;

    public boolean isOwnerOf(Long postId){
        UserEntity user=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post=postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }
}
