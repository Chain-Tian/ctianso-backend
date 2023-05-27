package com.cyt.search.controller;

import com.cyt.search.common.BaseResponse;
import com.cyt.search.common.ResultUtils;
import com.cyt.search.manager.SearchFacade;
import com.cyt.search.model.dto.search.SearchRequest;
import com.cyt.search.model.vo.SearchVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/search")
@Slf4j
@Api(tags = "聚合搜索")
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    @Cacheable(value = "search", key = "#searchRequest.type+'_'+#searchRequest.searchText+'_'+#searchRequest.current+'_'+#searchRequest.pageSize", cacheManager = "cacheManager5min")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest req) {
        SearchVO searchVO = searchFacade.searchAll(searchRequest, req);
        return ResultUtils.success(searchVO);
    }

}
