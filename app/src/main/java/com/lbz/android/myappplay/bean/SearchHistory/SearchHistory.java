package com.lbz.android.myappplay.bean.SearchHistory;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lbz on 2017/9/18.
 */

@Entity
public class SearchHistory {
    @Id
    private Long id;
    private String name;
    @Transient
    private int tempUsageCount; // not persisted
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 822577210)
    public SearchHistory(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    @Override
    public String toString() {
        return "SearchHistory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

