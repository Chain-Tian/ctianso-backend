package com.cyt.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyt.search.model.vo.VideoVO;
import org.springframework.stereotype.Component;

@Component
public class VideoDatasource implements Datasource<VideoVO> {
    @Override
    public Page<VideoVO> doSearch(String searchText, long pageNum, long pageSize) {
        return null;
    }
}
