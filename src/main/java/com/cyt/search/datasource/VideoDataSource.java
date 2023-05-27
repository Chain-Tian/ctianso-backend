package com.cyt.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyt.search.model.vo.VideoVO;
import com.cyt.search.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class VideoDataSource implements Datasource<VideoVO> {

    @Resource
    private UserService userService;

    @Override
    public Page<VideoVO> doSearch(String searchText, long pageNum, long pageSize) {
        return null;
    }
}
