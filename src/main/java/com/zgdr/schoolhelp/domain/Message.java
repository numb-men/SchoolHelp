package com.zgdr.schoolhelp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * message
 * 消息表映射对象
 *
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/28
 **/

@Entity(name = "message")
public class Message {

    /*消息ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//使用默认生成方式（MySQL）：自增
    private Integer messageId;

    /*消息发送者ID*/
    private Integer send;

    /*消息接受者ID*/
    private Integer accet;

    /*消息内容*/
    private String messageContent;

    /*消息类别*/
    private String messageType;

    /*发送时间*/
    private Date sendTime;

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", send=" + send +
                ", accet=" + accet +
                ", messageContent='" + messageContent + '\'' +
                ", messageType='" + messageType + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getSend() {
        return send;
    }

    public void setSend(Integer send) {
        this.send = send;
    }

    public Integer getAccet() {
        return accet;
    }

    public void setAccet(Integer accet) {
        this.accet = accet;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Message() {
    }

    public Message(Integer send, Integer accet, String messageContent, String messageType, Date sendTime) {
        this.send = send;
        this.accet = accet;
        this.messageContent = messageContent;
        this.messageType = messageType;
        this.sendTime = sendTime;
    }
}
