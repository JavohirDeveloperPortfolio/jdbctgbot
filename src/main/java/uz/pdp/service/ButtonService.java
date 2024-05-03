package uz.pdp.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ButtonService {
    public ReplyKeyboardMarkup mainMenuUz(){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();

        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        KeyboardButton button1 = new KeyboardButton();
        button1.setText("Mening profilim");

        KeyboardButton button2 = new KeyboardButton();
        button2.setText("Sozlamalar");

        KeyboardButton button3 = new KeyboardButton();
        button3.setText("Do'stlarga ulashish");

        row1.add(button1);
        row2.add(button2);
        row2.add(button3);

        rowList.add(row1);
        rowList.add(row2);
        markup.setKeyboard(rowList);

        return markup;
    }

    public ReplyKeyboardMarkup mainMenuRu(){
        ReplyKeyboardMarkup markup1 = new ReplyKeyboardMarkup();

        List<KeyboardRow> rowList = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        KeyboardButton button1 = new KeyboardButton();
        button1.setText("мой профиль");

        KeyboardButton button2 = new KeyboardButton();
        button2.setText("настройки");

        KeyboardButton button3 = new KeyboardButton();
        button3.setText("Поделиться с друзьями");

        row1.add(button1);
        row2.add(button2);
        row2.add(button3);

        rowList.add(row1);
        rowList.add(row2);

        markup1.setKeyboard(rowList);

        return markup1;
    }
}
