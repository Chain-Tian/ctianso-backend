package com.cyt.search.manager;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyt.search.common.ErrorCode;
import com.cyt.search.datasource.*;
import com.cyt.search.exception.ThrowUtils;
import com.cyt.search.model.dto.search.SearchRequest;
import com.cyt.search.model.entity.Picture;
import com.cyt.search.model.enums.SearchTypeEnum;
import com.cyt.search.model.vo.PostVO;
import com.cyt.search.model.vo.SearchVO;
import com.cyt.search.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class SearchFacade {
    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private UserDataSource userDatasource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest req) {

        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);

        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        // 如果关键字为空，搜索出所有数据
        if (searchTypeEnum == null) {
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                // 查图片
                return pictureDataSource.doSearch(searchText, current, pageSize);
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                // 查帖子
                return postDataSource.doSearch(searchText, current, pageSize);

            });
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                // 查用户
                return userDatasource.doSearch(searchText, current, pageSize);
            });
            try {
                CompletableFuture.allOf(postTask, pictureTask, userTask).join();
                // 组装返回体
                SearchVO searchVO = new SearchVO();
                Page<PostVO> postPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();
                Page<UserVO> userPage = userTask.get();
                return searchVO;
            } catch (Exception e) {
                log.error("查询异常", e);
                throw new RuntimeException(e);
            }
        } else {
            // 返回体
            SearchVO searchVO = new SearchVO();
            Datasource datasource = dataSourceRegistry.getDatasourceByType(type);
            Page page = datasource.doSearch(searchText, current, pageSize);
            searchVO.setDataList(page.getRecords());
            return searchVO;
        }
    }
}
