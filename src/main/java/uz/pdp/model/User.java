package uz.pdp.model;

public class User {
    private String chatId;
    private String fullName;
    private String role;
    private String phoneNumber;
    private BotState botState;

    public User(String chatId, BotState botState) {
        this.chatId = chatId;
        this.botState = botState;
    }

    public User() {}

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
    }
}

