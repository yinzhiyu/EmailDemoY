package com.email.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class MailBList {
    @Id(autoincrement = true)
    private Long mailBId;
    private String mailAdress;
    @Generated(hash = 1093519509)
    public MailBList(Long mailBId, String mailAdress) {
        this.mailBId = mailBId;
        this.mailAdress = mailAdress;
    }
    @Generated(hash = 1560097767)
    public MailBList() {
    }
    public Long getMailBId() {
        return this.mailBId;
    }
    public void setMailBId(Long mailBId) {
        this.mailBId = mailBId;
    }
    public String getMailAdress() {
        return this.mailAdress;
    }
    public void setMailAdress(String mailAdress) {
        this.mailAdress = mailAdress;
    }

}
