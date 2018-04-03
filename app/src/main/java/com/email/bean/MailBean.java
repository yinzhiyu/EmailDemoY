package com.email.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yinzhiyu on 2018-4-3.
 */

public class MailBean implements Parcelable{
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

    protected MailBean(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        messageID = in.readString();
        from = in.readString();
        to = in.readString();
        cc = in.readString();
        bcc = in.readString();
        subject = in.readString();
        sentdata = in.readString();
        content = in.readString();
        replysign = in.readByte() != 0;
        html = in.readByte() != 0;
        news = in.readByte() != 0;
        charset = in.readString();
    }

    public static final Creator<MailBean> CREATOR = new Creator<MailBean>() {
        @Override
        public MailBean createFromParcel(Parcel in) {
            return new MailBean(in);
        }

        @Override
        public MailBean[] newArray(int size) {
            return new MailBean[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSentdata() {
        return sentdata;
    }

    public void setSentdata(String sentdata) {
        this.sentdata = sentdata;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isReplysign() {
        return replysign;
    }

    public void setReplysign(boolean replysign) {
        this.replysign = replysign;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public boolean isNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(messageID);
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(cc);
        dest.writeString(bcc);
        dest.writeString(subject);
        dest.writeString(sentdata);
        dest.writeString(content);
        dest.writeByte((byte) (replysign ? 1 : 0));
        dest.writeByte((byte) (html ? 1 : 0));
        dest.writeByte((byte) (news ? 1 : 0));
        dest.writeString(charset);
    }
}
