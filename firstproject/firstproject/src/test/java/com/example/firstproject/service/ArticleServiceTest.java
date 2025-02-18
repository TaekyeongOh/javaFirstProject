package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ArticleServiceTest {

    @Autowired ArticleService articleService;

    @Test
    void index() {
        // 예상
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        Article d = new Article(4L,"당신의 인생 영화는?", "댓글 ㄱ");
        Article e = new Article(5L,"당신의 소울 푸드는?", "댓글 ㄱㄱ");
        Article f = new Article(6L,"당신의 취미는?", "댓글 ㄱㄱㄱ");

        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c, d, e, f));

        // 실제
        List<Article> articles = articleService.index();

        // 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공____존재하는_id_입력() {
        // 예상
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        // 실제
        Article article = articleService.show(id);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    void show_실패____존재하지_않는_id_입력() {
        // 예상
        Long id = -1L;
        Article expected = null;
        // 실제
        Article article = articleService.show(id);
        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_성공____title과_content만_있는_dto_입력() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(7L, title, content);
        // 실제
        Article article = articleService.create(dto);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void create_실패____id가_포함된_dto_입력() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;
        // 실제
        Article article = articleService.create(dto);
        // 비교
        assertEquals(expected, article);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}