package ru.job4j.dreamjob.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostDBStoreTest {
    private static PostDBStore store;
    private static BasicDataSource pool;

    @BeforeEach
    public void initStore() {
        pool = new Main().loadPool();
        store = new PostDBStore(pool);
    }

    @AfterEach
    public void clearStore() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from post")
        ) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenCreatePost() {
        Post post = new Post(0, "Java Job", "Java",
                LocalDateTime.of(2022, 1, 15, 12, 10),
                new City(1, "Moscow"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    void whenUpdatePost() {
        Post post = new Post(0, "Java Job", "Java",
                LocalDateTime.of(2022, 1, 15, 12, 10),
                new City(1, "Moscow"));
        store.add(post);
        post.setName("new Post");
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo("new Post");
    }

    @Test
    void whenFindAllPosts() {
        Post firstPost = new Post(0, "Java Job", "Java",
                LocalDateTime.of(2022, 1, 15, 12, 10),
                new City(1, "Moscow"));
        Post secondPost = new Post(1, "Java Job", "Java",
                LocalDateTime.of(2022, 1, 15, 12, 10),
                new City(1, "Moscow"));
        store.add(firstPost);
        store.add(secondPost);
        assertThat(store.findAll()).isEqualTo(List.of(firstPost, secondPost));
    }

    @Test
    void whenFindPostById() {
        Post firstPost = new Post(0, "Java Job", "Java",
                LocalDateTime.of(2022, 1, 15, 12, 10),
                new City(1, "Moscow"));
        Post secondPost = new Post(1, "Java Job", "Java",
                LocalDateTime.of(2022, 1, 15, 12, 10),
                new City(1, "Moscow"));
        Post thirdPost = new Post(2, "Java Job", "Java",
                LocalDateTime.of(2022, 1, 15, 12, 10),
                new City(1, "Moscow"));
        store.add(firstPost);
        store.add(secondPost);
        store.add(thirdPost);
        Post findPost = store.findById(secondPost.getId());
        assertThat(findPost).isEqualTo(secondPost);
    }
}