package org.example.repository;

import org.example.exception.NotFoundException;
import org.example.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

// Stub
public class PostRepository {
    private ConcurrentHashMap<Long, Post> postList = new ConcurrentHashMap<>();

    private AtomicLong count = new AtomicLong(0);

    public List<Post> all() {
        List<Post> list = postList.values().stream()
                .collect(Collectors.toList());
        return list;
    }

    public Optional<Post> getById(long id) {
        Post post = postList.get(id);
        return Optional.ofNullable(post);
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(count.incrementAndGet());
            postList.put(post.getId(), post);
            return post;
        } else {
            Optional<Post> postOptional = Optional.ofNullable(postList.get(post.getId()));
            if (postOptional.isEmpty()) {
                throw new NotFoundException("Post not found");
            }
            postList.put(post.getId(), post);
            return postList.get(post.getId());
        }
    }

    public void removeById(long id) {
        postList.remove(id);
    }
}
