package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Test
    void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post"),
                new Post(2, "New post")
        );

        Model model = mock(Model.class);
        MockHttpSession session = new MockHttpSession();
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);

        PostController postController = new PostController(
                postService,
                cityService
        );

        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    void whenAddPost() {
        Post input = new Post(1, "New post");

        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);

        PostController postController = new PostController(
                postService,
                cityService
        );

        String page = postController.createPost(input);
        verify(postService).add(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    void whenUpdatePost() {
        Post input = new Post(1, "New post");
        Post newInput = new Post(1, "The newest post");

        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);

        PostController postController = new PostController(
                postService,
                cityService
        );

        postController.createPost(input);
        String page = postController.updatePost(newInput);
        verify(postService).add(input);
        verify(postService).update(newInput);
        assertThat(page).isEqualTo("redirect:/posts");
    }
}