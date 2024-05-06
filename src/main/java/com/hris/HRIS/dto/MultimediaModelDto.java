package com.hris.HRIS.dto;

import com.hris.HRIS.model.MultimediaModel;

import java.util.Base64;
import java.util.List;

public class MultimediaModelDto {
    private String id;
    private String userId;
    private String channelId;
    private String chatId;
    private String fileBase64; // Serialize Binary to base64 string
    private String title;
    private String status;
    private List<String> likes;
    private List<String> comments;
    private List<String> shares;
    private String timestamp;
    private String contentType;
    private String sharedUserId;
    private String sharedUserCaption;
    private String sharedUserTimestamp;

    public MultimediaModelDto(MultimediaModel multimediaModel) {
        this.id = multimediaModel.getId();
        this.userId = multimediaModel.getUserId();
        this.channelId = multimediaModel.getChannelId();
        this.chatId = multimediaModel.getChatId();
        this.fileBase64 = Base64.getEncoder().encodeToString(multimediaModel.getFile().getData());
        this.title = multimediaModel.getTitle();
        this.status = multimediaModel.getStatus();
        this.likes = multimediaModel.getLikes();
        this.comments = multimediaModel.getComments();
        this.shares = multimediaModel.getShares();
        this.timestamp = multimediaModel.getTimestamp();
        this.contentType = multimediaModel.getContentType();
        this.sharedUserId = multimediaModel.getSharedUserId();
        this.sharedUserCaption = multimediaModel.getSharedUserCaption();
        this.sharedUserTimestamp = multimediaModel.getSharedUserTimestamp();
    }
}

