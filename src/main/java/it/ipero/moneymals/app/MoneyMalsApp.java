package it.ipero.moneymals.app;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import it.ipero.moneymals.bot.MoneyMalsBot;

public class MoneyMalsApp {

	public static void main(String[] args) {
		// Initialize Api Context
        ApiContextInitializer.init();
        
        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();
        
        // Register our bot
        try {
            botsApi.registerBot(new MoneyMalsBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}

}
