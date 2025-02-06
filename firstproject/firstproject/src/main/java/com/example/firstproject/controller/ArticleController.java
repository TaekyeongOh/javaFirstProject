package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class ArticleController {

    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired // 스프링 부트가 미리 생성해놓은 객체 가져다가 자동 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        System.out.println(form.toString());

        // 1. DTO를 변환 ! Entity!
        Article article = form.toEntity();
        System.out.println(article.toString());

        // 2. Repository에게 Entity를 DB 안에 저장하게 함
        Article saved=articleRepository.save(article);
        System.out.println(saved.toString());

        return "redirect:/articles/"+ saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);

        // 1. id로 데이터를 가져옴
        Article articleEntity=articleRepository.findById(id).orElse(null);
        log.info(articleEntity.toString());
        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

         // 3. 보여줄 페이지를 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1. 모든 article 가져온다
        List<Article> articleEntityList = articleRepository.findAll();
        // 2. 가져온 article 묶음을 뷰로 전달
        model.addAttribute("articleList",articleEntityList);
        // 3. 뷰 페이지를 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        // 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("articles/update")
    public String update(ArticleForm form){
        log.info("update - "+ form.toString());

        // 1: DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2: 엔티티를 DB로 저장
        // 2-1: DB에서 기존 데이터를 가져옴
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2: 기존 데이터가 있다면, 값을 갱신
        if (target != null) {
            articleRepository.save(articleEntity);
            log.info("redirect:/articles/" + articleEntity.getId());
            return "redirect:/articles/" + articleEntity.getId();
        } else{
            log.info("id를 찾을 수 없음");
            return "/articles";
        }

        // 3: 수정 결과 페이지로 리다이렉트
        // return "redirect:/articles/" + articleEntity.getId();

    }
}