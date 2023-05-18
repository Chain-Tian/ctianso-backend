package com.cyt.search.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cyt.search.esdao.PostEsDao;
import com.cyt.search.model.entity.Post;
import com.cyt.search.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 全量同步帖子到 es
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Resource
    private PostEsDao postEsDao;

    @Override
    public void run(String... args) {
        String json = "{\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"reviewStatus\":1,\"current\":1}";
        String url = "https://www.code-nav.cn/api/post/list/page/vo";
        String res = HttpRequest.post(url)
                .body(json)
                .execute()
                .body();
        System.out.println(res);

        // 2. json 转对象
        Map<String, Object> map = new JSONUtil().toBean(res, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject rec = (JSONObject) record;
            System.out.println();
            Post post = new Post();
            post.setTitle(rec.getStr("title"));
            post.setContent(rec.getStr("content"));
            JSONArray tags = rec.getJSONArray("tags");
            String jsonStr = JSONUtil.toJsonStr(tags);
            post.setTags(jsonStr);
            post.setThumbNum(0);
            post.setFavourNum(0);
            post.setUserId(1L);
            postList.add(post);
        }
        boolean b = postService.saveBatch(postList);
        if (b) {
            log.info("初始化帖子列表成功，条数 ={}", postList.size());
        } else {
            log.info("初始化失败");
        }
    }
}
