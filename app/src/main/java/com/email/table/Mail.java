package com.email.table;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Mail {
    @Id(autoincrement = true)
    private Long id;
    private String messageID;
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String sentdata;
    private String content;
    private boolean replysign;
    private boolean html;
    private boolean news;
//    private ArrayList<String> attachments;
    private String charset;
    private int usefulType;
    @Generated(hash = 1508775443)
    public Mail(Long id, String messageID, String from, String to, String cc,
            String bcc, String subject, String sentdata, String content,
            boolean replysign, boolean html, boolean news, String charset,
            int usefulType) {
        this.id = id;
        this.messageID = messageID;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.sentdata = sentdata;
        this.content = content;
        this.replysign = replysign;
        this.html = html;
        this.news = news;
        this.charset = charset;
        this.usefulType = usefulType;
    }
    @Generated(hash = 1943431032)
    public Mail() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMessageID() {
        return this.messageID;
    }
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
    public String getFrom() {
        return this.from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return this.to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getCc() {
        return this.cc;
    }
    public void setCc(String cc) {
        this.cc = cc;
    }
    public String getBcc() {
        return this.bcc;
    }
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSentdata() {
        return this.sentdata;
    }
    public void setSentdata(String sentdata) {
        this.sentdata = sentdata;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public boolean getReplysign() {
        return this.replysign;
    }
    public void setReplysign(boolean replysign) {
        this.replysign = replysign;
    }
    public boolean getHtml() {
        return this.html;
    }
    public void setHtml(boolean html) {
        this.html = html;
    }
    public boolean getNews() {
        return this.news;
    }
    public void setNews(boolean news) {
        this.news = news;
    }
    public String getCharset() {
        return this.charset;
    }
    public void setCharset(String charset) {
        this.charset = charset;
    }
    public int getUsefulType() {
        return this.usefulType;
    }
    public void setUsefulType(int usefulType) {
        this.usefulType = usefulType;
    }
}
