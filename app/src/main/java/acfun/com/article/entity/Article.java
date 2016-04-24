/*
 * Copyright (C) 2013 Yrom Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package acfun.com.article.entity;

import android.text.Editable;
import android.text.Html;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import org.xml.sax.XMLReader;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
{
  "status": 200,
  "msg": "ok",
  "data": {
    "fullArticle": {
      "txt": ".....",
      "views": 29554,
      "comments": 452,
      "stows": 12,
      "releaseDate": 1377511408000,
      "description": "折戟了，而随着中国战队的落败，一股风暴加地震正在袭来。一个普通看客眼中的发展史",
      "user": {
        "userId": 319714,
        "username": "续-R",
        "userImg": "http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201408/19173024pofa.jpg"
      },
      "channel": {
        "channelId": 110,
        "channelName": ""
      },
      "tags": [
        "DOTA",
        "兄贵解说",
        "原创"
      ],
      "viewOnly": 0,
      "channelId": 110,
      "contentId": 797651,
      "title": "【DOTA】随着中国战队的落败，一股风暴加地震正在袭来",
      "cover": "http://static.acfun.mm111.net/dotnet/20120923/style/image/cover.png"
    }
  }
}
 * @author Yrom
 * 
 */
public class Article {
    public int id;
    public String title;
    public long postTime;
    public int views;
    public int comments;
    public int stows;
    public int channelId;
    public ArrayList<String> imgUrls;
    public ArrayList<SubContent> contents;
    public String description;
    public boolean isRecommend;
    public static class SubContent {
        public String subTitle;
        public String content;
    }


    private final static String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性


    private static String TAG = "Article";
    private static Pattern imageReg = Pattern.compile("<img.+?src=[\"|'](.+?)[\"|']");


    public static Article newArticle(JSONObject articleJson)  {
            Article article = new Article();
            article.imgUrls = new ArrayList<>();
            // parse info
            article.title = articleJson.getString("title");
            article.postTime = articleJson.getLongValue("releaseDate");
            article.id = articleJson.getIntValue("contentId");
            article.description = articleJson.getString("description");
            // statistics
            article.views = articleJson.getIntValue("views");
            article.comments = articleJson.getIntValue("comments");
            article.stows = articleJson.getIntValue("stows");
            // sub contents and images
            article.contents = new ArrayList<>();
            parseContentArray(articleJson, article);
            // channel
            article.channelId = articleJson.getIntValue("channelId");
            return article;

    }

    static Pattern pageReg = Pattern.compile("\\[NextPage\\]([^\\[\\]]+)\\[/NextPage\\]");
    
    private static void parseContentArray(JSONObject articleJson, Article article)  {


        String fullText = articleJson.getString("txt");
        Matcher matcher = pageReg.matcher(fullText);
        int start = 0;
        while(matcher.find() && start < fullText.length()){
            Log.d("test", "Find next page tag: "+matcher.group());
            int index = matcher.start();
            SubContent content = new SubContent();
            content.subTitle = matcher.group(1).replaceAll("<span[^>]+>", "").replaceAll("</span>", "");
            content.content = fullText.substring(start, index);
            start = matcher.end();
            findImageUrls(article, content);
            article.contents.add(content);
        }
        if(article.contents.isEmpty()){
            SubContent content = new SubContent();
            content.content = fullText;

            content.subTitle = article.title;
            findImageUrls(article, content);
            article.contents.add(content);
        }
    }

    private static void findImageUrls(Article article, SubContent content) {
        Matcher imageMatcher = imageReg.matcher(content.content);
        while (imageMatcher.find()) {
            article.imgUrls.add(imageMatcher.group(1));
        }
    }

}


