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
import uz.pdp.service.ButtonService;

import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {
    private final UserRepository userRepository =
            new UserRepository();
    private final ButtonService buttonService = new ButtonService();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();

            if (text.equals("/start")) {
                if (userRepository.getUserByChatId(chatId) == null) {
                    userRepository.insertUser(chatId, BotState.MAIN_PAGE);
                }
                userRepository.updateBotState(chatId,BotState.MAIN_PAGE);
                SendMessage message = new SendMessage();
                message.setText("Xush kelibsiz. Tilni tanlang");
                message.setChatId(chatId);

                ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
                KeyboardRow row1 = new KeyboardRow(List.of(new KeyboardButton("O'zbek tili")));
                KeyboardRow row2 = new KeyboardRow(List.of(new KeyboardButton("Rus tili")));
                markup.setKeyboard(List.of(row1, row2));
                message.setReplyMarkup(markup);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else {
                User user = userRepository.getUserByChatId(chatId);
                if (user.getBotState() == BotState.MAIN_PAGE) {
                    if (text.equals("O'zbek tili")) {
                        userRepository.updateLang(chatId, "uz");
                        userRepository.updateBotState(chatId, BotState.UZ_PAGE);
                        SendMessage message = new SendMessage();
                        message.setText("O'zbek tili tanlandi");
                        message.setChatId(chatId);
                        message.setReplyMarkup(buttonService.mainMenuUz());
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (text.equals("Rus tili")) {
                        userRepository.updateLang(chatId, "ru");
                        userRepository.updateBotState(chatId, BotState.RU_PAGE);
                        SendMessage message = new SendMessage();
                        message.setText("Rus tili tanlandi");
                        message.setReplyMarkup(buttonService.mainMenuRu());
                        message.setChatId(chatId);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (user.getBotState() == BotState.UZ_PAGE) {
                    SendMessage message = new SendMessage();
                    if (text.equals("Mening profilim")){
                        message.setText("Mening profilim tanlandi");
                        message.setChatId(chatId);
                    } else if (text.equals("Sozlamalar")) {
                        message.setText("Sozlamalar tanlandi");
                        message.setChatId(chatId);
                    } else if (text.equals("Do'stlarga ulashish")) {
                        message.setText("Do'stlarga ulashish tanlandi");
                        message.setChatId(chatId);
                    }
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                } else if (user.getBotState() == BotState.RU_PAGE) {
                    if (text.equals("мой профиль")){

                    } else if (text.equals("настройки")) {

                    } else if (text.equals("Поделиться с друзьями")) {

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

    private ReplyKeyboardMarkup menuUz(){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();

        KeyboardButton button1 = new KeyboardButton();
        button1.setText("Mening profilim");

        KeyboardButton button2 = new KeyboardButton();
        button2.setText("Sozlamalar");

        KeyboardButton button3 = new KeyboardButton();
        button3.setText("Do'stlarga ulashish");

        KeyboardRow row1 = new KeyboardRow();
        row1.add(button1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(button2);
        row2.add(button3);
        rowList.add(row1);
        rowList.add(row2);
        markup.setKeyboard(rowList);
        return markup;
    }
}
