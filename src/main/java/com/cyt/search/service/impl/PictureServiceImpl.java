package com.cyt.search.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyt.search.common.ErrorCode;
import com.cyt.search.exception.BusinessException;
import com.cyt.search.model.entity.Picture;
import com.cyt.search.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片服务
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Service
@Slf4j
public class PictureServiceImpl implements PictureService {
    @Override
    public Page<Picture> searchPicture(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1) * pageSize;
        String url = String.format("https://cn.bing.com/images/search?q=%s&first=%s", searchText, current);
        Document doc = null;
        List<Picture> pictures;
        try {
            doc = Jsoup.connect(url).get();
            Elements elements = doc.select(".iuscp.isv");
            pictures = new ArrayList<>();
            for (Element element : elements) {
                if (pictures.size() >= pageSize) {
                    break;
                }
                // 获取标题
                String title = element.select(".inflnk").attr("aria-label");
                // 获取图片
                String m = element.select(".iusc").attr("m");
                JSONObject j = JSONUtil.parseObj(m);
                String imgUrl = j.get("murl", String.class);
//            System.out.println("title:" + title);
//            System.out.println("imgUrl:" + imgUrl);
                pictures.add(new Picture(title, imgUrl));
            }
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Page<Picture> picturePage = new Page<>(pageNum, pageSize);
        picturePage.setRecords(pictures);
        return picturePage;
    }
}




