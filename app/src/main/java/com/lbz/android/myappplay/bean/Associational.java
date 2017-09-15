package com.lbz.android.myappplay.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by elitemc on 2017/9/14.
 */

public class Associational implements Serializable{

    /**
     * command : false
     * host : http://f3.market.xiaomi.com/download/
     * suggestion : ["直播吧","直播tv","直播帝","直播客","一直播"]
     * suggestionList : [{"external":false,"word":"直播吧"},{"external":false,"word":"直播tv"},{"external":false,"word":"直播帝"},{"external":false,"word":"直播客"},{"external":false,"word":"一直播"}]
     * thumbnail : http://t1.market.xiaomi.com/thumbnail/
     * uid : 7b6af
     */

    private boolean command;
    private String host;
    private String thumbnail;
    private String uid;
    private List<String> suggestion;
    private List<SuggestionListBean> suggestionList;

    public boolean isCommand() {
        return command;
    }

    public void setCommand(boolean command) {
        this.command = command;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(List<String> suggestion) {
        this.suggestion = suggestion;
    }

    public List<SuggestionListBean> getSuggestionList() {
        return suggestionList;
    }

    public void setSuggestionList(List<SuggestionListBean> suggestionList) {
        this.suggestionList = suggestionList;
    }

    public static class SuggestionListBean implements Serializable {
        /**
         * external : false
         * word : 直播吧
         */

        private boolean external;
        private String word;

        public boolean isExternal() {
            return external;
        }

        public void setExternal(boolean external) {
            this.external = external;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }
    }
}
