package com.email.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 关键字拦截
 */
@Entity
public class KeyWord {
    @Id(autoincrement = true)
    private Long keyId;
    private String keyword;
    @Generated(hash = 1845553980)
    public KeyWord(Long keyId, String keyword) {
        this.keyId = keyId;
        this.keyword = keyword;
    }
    @Generated(hash = 617591908)
    public KeyWord() {
    }
    public Long getKeyId() {
        return this.keyId;
    }
    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }
    public String getKeyword() {
        return this.keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}