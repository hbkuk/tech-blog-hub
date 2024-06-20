package com.techbloghub.core.post.application;

import com.techbloghub.common.util.ApplicationTest;
import com.techbloghub.core.post.domain.Post;
import com.techbloghub.core.post.domain.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.stream.IntStream;

import static com.techbloghub.core.blog.domain.Blog.우아한형제들;
import static com.techbloghub.core.post.util.PostTestUtil.게시글_테스트_데이터_생성하기;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // 테스트 메서드간 캐싱 동작으로 인한 Spring 컨테이너 초기화
@DisplayName("게시글 서비스 Mock 테스트")
public class PostServiceMockTest extends ApplicationTest {

    @Autowired
    PostService postService;

    @MockBean
    PostRepository postRepository;

    @Test
    @DisplayName("페이지 정보에 따른 게시글 목록 조회 요청은 캐싱된다.")
    void 페이지_정보에_따른_게시글_목록_캐싱_확인() {
        // given
        List<Post> 생성된_게시글_목록 = 게시글_테스트_데이터_생성하기(우아한형제들, 10);
        Pageable 페이지_정보 = PageRequest.of(1, 10, Sort.Direction.DESC, "publishAt");

        when(postRepository.findAll(페이지_정보)).thenReturn(new PageImpl<>(생성된_게시글_목록));

        // when
        IntStream.range(0, 10).forEach((count) -> postService.findAll(페이지_정보));

        // then
        verify(postRepository, times(1)).findAll(페이지_정보);
    }

    @Test
    @DisplayName("페이지 정보가 다른 경우 게시글 목록 조회 요청은 캐싱되지 않는다.")
    void 페이지_정보가_다른_경우_게시글_목록_조회_요청은_캐싱되지_않는다() {
        // given
        List<Post> 생성된_게시글_목록 = 게시글_테스트_데이터_생성하기(우아한형제들, 10);
        Pageable 첫번째_페이지_정보 = PageRequest.of(1, 10, Sort.Direction.DESC, "publishAt");
        Pageable 두번째_페이지_정보 = PageRequest.of(2, 10, Sort.Direction.DESC, "publishAt");

        when(postRepository.findAll(첫번째_페이지_정보)).thenReturn(new PageImpl<>(생성된_게시글_목록));
        when(postRepository.findAll(두번째_페이지_정보)).thenReturn(new PageImpl<>(생성된_게시글_목록));

        // when
        IntStream.range(0, 10).forEach((count) -> {
            postService.findAll(첫번째_페이지_정보);
            postService.findAll(두번째_페이지_정보);
        });

        // then
        verify(postRepository, times(1)).findAll(첫번째_페이지_정보);
        verify(postRepository, times(1)).findAll(두번째_페이지_정보);
    }
}
