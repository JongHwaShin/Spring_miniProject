package jonghwa.Spring_miniProject.service.posts;

import jonghwa.Spring_miniProject.domain.posts.Posts;
import jonghwa.Spring_miniProject.domain.posts.PostsRepository;
import jonghwa.Spring_miniProject.web.dto.PostsListResponseDto;
import jonghwa.Spring_miniProject.web.dto.PostsResponseDto;
import jonghwa.Spring_miniProject.web.dto.PostsSaveRequestDto;
import jonghwa.Spring_miniProject.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당게시글이 없습니다 id= "+id));

            posts.update(requestDto.getTitle(),requestDto.getContent());

            return id;

    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당게시글이 없습니다. id= "+id));
        return new PostsResponseDto(entity);
    }

    @Transactional
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다 id ="+id));


        postsRepository.delete(posts);




    }

}
