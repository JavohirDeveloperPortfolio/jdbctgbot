package uz.pdp;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.model.Animal;

public class Main {
    public static void main(String[] args) {
        Animal mol = new Animal();
        mol.setName("Qo'y");
        System.out.println(mol.getName());

        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(new MyBot("6356478587:AAGVGkmQA7m4csJryP6ySZeEyI7AVZ7501E"));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}