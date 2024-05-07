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
                userRepository.updateBotState(chatId, BotState.MAIN_PAGE);
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
                switch (user.getBotState()) {
                    case MAIN_PAGE -> {
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
                    }
                    case UZ_PAGE -> {
                        SendMessage message = new SendMessage();
                        if (text.equals("Mening profilim")) {
                            userRepository.updateBotState(chatId, BotState.MY_PROFILE);
                            message.setText("Mening profilim tanlandi");
                            message.setChatId(chatId);
                            message.setReplyMarkup(buttonService.backMenu());
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
                    }
                    case RU_PAGE -> {
                        if (text.equals("мой профиль")) {

                        } else if (text.equals("настройки")) {

                        } else if (text.equals("Поделиться с друзьями")) {

                        }
                    }
                    case MY_PROFILE -> {
                        if (text.equals("Ortga")) {
                            userRepository.updateBotState(chatId,BotState.UZ_PAGE);
                            SendMessage message = new SendMessage();
                            message.setText("Quyidagilardan birini tanlang");
                            message.setChatId(chatId);
                            message.setReplyMarkup(buttonService.mainMenuUz());
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
    }

    @Override
    public String getBotUsername() {
        return "OnlineTexnikaN1bot";
    }

    public MyBot(String botToken) {
        super(botToken);
    }
}
