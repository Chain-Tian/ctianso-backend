package com.cyt.search.datasource;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyt.search.model.vo.VideoVO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.HttpCookie;
import java.util.List;

@Component
public class VideoDataSource implements Datasource<VideoVO> {


    List<HttpCookie> cookies;


    @Scheduled(fixedRate = 60 * 1000 * 5)
    public void getCookie() {
        HttpResponse response = HttpRequest.get("https://www.bilibili.com/").execute();
        cookies = response.getCookies();
    }

    @Override
    public Page<VideoVO> doSearch(String searchText, long pageNum, long pageSize) {
        String body = HttpRequest.get("https://api.bilibili.com/x/web-interface/search/type?search_type=video&keyword=" + searchText).cookie(cookies).execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(body);
        JSONObject data = jsonObject.get("data", JSONObject.class);
        JSONArray result = data.get("result", JSONArray.class);

        for (Object video : result) {
            JSONObject ob = JSONUtil.parseObj(video);
            String author = ob.getStr("author");
            String url = ob.getStr("arcurl");
            String title = ob.getStr("title");
            String pic = ob.getStr("pic");
            VideoVO videoVO = new VideoVO(author, url, title, pic);
            System.out.println();
        }

        return null;
    }
}
