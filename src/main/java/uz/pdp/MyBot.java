package uz.pdp;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "OnlineTexnikaN1bot";
    }

    public MyBot(String botToken) {
        super(botToken);
    }
}
