package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.repository.PostStore;

import java.util.Collection;

@Service
public class PostService {
    private final PostStore postStore;

    public PostService(PostStore postStore) {
        this.postStore = postStore;
    }

    public Collection<Post> findAll() {
        return postStore.findAll();
    }

    public Post findById(int id) {
        return postStore.findById(id);
    }

    public void add(Post post) {
        postStore.add(post);
    }

    public void update(Post post) {
        postStore.update(post);
    }
}
