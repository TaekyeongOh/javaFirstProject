package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController // RestAPI용 컨트롤러, 데이터(JSON)를 반환함
@RequestMapping("/api/articles")
public class ArticleApiController {

    private final ArticleRepository articleRepository;

    // GET - 모든 게시글 조회
    @GetMapping
    public ResponseEntity<List<Article>> index() {
        List<Article> articles = articleRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }

    // GET - 특정 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<Article> show(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    // POST - 게시글 생성
    @PostMapping
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Article created = articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PATCH - 게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                          @RequestBody ArticleForm dto) {
        // 1. DTO -> 엔티티
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article);

        // 2. 타겟 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리
        if (target == null || !id.equals(article.getId())) {
            log.info("잘못된 요청! id: {}, article: {}", id, article);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 4. 업데이트 및 정상 응답
        target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // DELETE - 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 대상 삭제
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
