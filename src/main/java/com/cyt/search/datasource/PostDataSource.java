package com.cyt.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyt.search.model.dto.post.PostQueryRequest;
import com.cyt.search.model.vo.PostVO;
import com.cyt.search.service.PostService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 帖子服务实现
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Component
public class PostDataSource implements Datasource<PostVO> {


    @Resource
    private PostService postService;


    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);
        Page<PostVO> postVOPage = postService.listPostVoByPage(postQueryRequest, null);
        return postVOPage;
    }
}




