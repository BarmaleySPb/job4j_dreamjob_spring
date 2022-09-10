package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final AtomicInteger id = new AtomicInteger(4);

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "job for junior",
                LocalDateTime.of(2022, 1, 15, 12, 10)));
        posts.put(2, new Post(2, "Middle Java Job", "job for middle",
                LocalDateTime.of(2022, 2, 25, 8, 15)));
        posts.put(3, new Post(3, "Senior Java Job", "job for senior",
                LocalDateTime.of(2022, 2, 5, 10, 15)));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void add(Post post) {
        post.setId(id.getAndIncrement());
        posts.put(post.getId(), post);
    }

    public void update(Post post) {
        posts.put(post.getId(), post);
    }
}