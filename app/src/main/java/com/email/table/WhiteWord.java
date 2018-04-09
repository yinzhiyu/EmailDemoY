package com.email.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 非垃圾邮件集
 */
@Entity
public class WhiteWord {
    @Id(autoincrement = true)
    private Long id;
    private String keyword;
    private int number;
    @Generated(hash = 157885519)
    public WhiteWord(Long id, String keyword, int number) {
        this.id = id;
        this.keyword = keyword;
        this.number = number;
    }
    @Generated(hash = 1600513869)
    public WhiteWord() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKeyword() {
        return this.keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public int getNumber() {
        return this.number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}