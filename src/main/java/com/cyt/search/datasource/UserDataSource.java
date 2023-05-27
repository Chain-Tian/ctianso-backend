package com.cyt.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyt.search.model.dto.user.UserQueryRequest;
import com.cyt.search.model.vo.UserVO;
import com.cyt.search.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户服务实现
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Component
public class UserDataSource implements Datasource<UserVO> {


    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);
        Page<UserVO> userVOPage = userService.listUserVoByPage(userQueryRequest);
        return userVOPage;
    }
}
