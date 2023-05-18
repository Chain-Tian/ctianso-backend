package com.cyt.search.model.dto.search;


import com.cyt.search.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 查询关键词
     */
    private String searchText;
    /**
     * 类型
     */
    private String type;
}
