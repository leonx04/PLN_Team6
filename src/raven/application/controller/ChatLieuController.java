package raven.application.controller;

import java.util.List;
import raven.application.model.ChatLieuModel;
import raven.application.service.ChatLieuService;

public class ChatLieuController {
    private ChatLieuService chatLieuService;

    public ChatLieuController() {
        this.chatLieuService = new ChatLieuService();
    }

    public List<ChatLieuModel> getAllChatLieu() {
        return chatLieuService.getALLChatLieu();
    }

    public String generateNewChatLieuID() {
        return chatLieuService.getNewIDCL();
    }

    public int addChatLieu(ChatLieuModel chatLieu) {
        return chatLieuService.insert(chatLieu);
    }

    public int updateChatLieu(ChatLieuModel chatLieu, String id) {
        return chatLieuService.update(chatLieu, id);
    }

    public int deleteChatLieu(String id) {
        return chatLieuService.delete(id);
    }
}
