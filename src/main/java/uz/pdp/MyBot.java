package uz.pdp;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.model.BotState;
import uz.pdp.model.User;
import uz.pdp.model.UserRepository;

import java.util.List;

public class MyBot extends TelegramLongPollingBot {
    private final UserRepository userRepository =
            new UserRepository();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();

            if (text.equals("/start")){
                if (userRepository.getUserByChatId(chatId) == null){
                    userRepository.insertUser(chatId, BotState.MAIN_PAGE);
                }
                SendMessage message = new SendMessage();
                message.setText("Xush kelibsiz. Tilni tanlang");
                message.setChatId(chatId);

                ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
                KeyboardRow row1 = new KeyboardRow(List.of(new KeyboardButton("O'zbek tili")));
                KeyboardRow row2 = new KeyboardRow(List.of(new KeyboardButton("Ro's tili")));
                markup.setKeyboard(List.of(row1,row2));
                message.setReplyMarkup(markup);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else {
                User user = userRepository.getUserByChatId(chatId);
                if (user.getBotState() == BotState.MAIN_PAGE){
                    if (text.equals("O'zbek tili")){
                        userRepository.updateLang(chatId,"uz");
                        SendMessage message = new SendMessage();
                        message.setText("O'zbek tili tanlandi");
                        message.setChatId(chatId);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (text.equals("Ro's tili")) {
                        SendMessage message = new SendMessage();
                        message.setText("Ro's tili tanlandi");
                        message.setChatId(chatId);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "OnlineTexnikaN1bot";
    }

    public MyBot(String botToken) {
        super(botToken);
    }
}
