package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
//@NoArgsConstructor
public class ArticleForm {

    private Long id;
    private String title;
    private String content;

    @Override
    public String toString() {
        return "ArticleForm{" +
                "id="+ id+'\'' +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Article toEntity() {
        return new Article(id, title, content);
    }
}