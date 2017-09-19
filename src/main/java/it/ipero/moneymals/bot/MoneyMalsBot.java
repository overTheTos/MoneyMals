package it.ipero.moneymals.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MoneyMalsBot extends TelegramLongPollingBot {

	private final String USER_AGENT = "Mozilla/5.0";
	private boolean running = false;
	
	public String getBotUsername() {
		// Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "moneymals";
	}

	@Override
	public String getBotToken() {
		return "438565031:AAGb9WBM-qR-DPPYi3ypSmsa_Wm6UQ3NHwI";
	}
	
	public void onUpdateReceived(Update update) {
		String outputMessage = null;
		String url = null;
		// We check if the update has a message and the message has text
	    if (update.hasMessage() && update.getMessage().hasText()) {
	        // Set variables
	        String messageText = update.getMessage().getText();
	        long chat_id = update.getMessage().getChatId();

	        if(messageText.contains("/sellETHprice")) {
	        	url = "https://api.coinbase.com/v2/prices/ETH-EUR/sell";
	        }
	        else if(messageText.contains("/buyETHprice")) {
	        	url = "https://api.coinbase.com/v2/prices/ETH-EUR/buy";
	        }
	        else if(messageText.contains("/sellBTCprice")) {
	        	url = "https://api.coinbase.com/v2/prices/BTC-EUR/sell";
	        }
	        else if(messageText.contains("/buyBTCprice")) {
	        	url = "https://api.coinbase.com/v2/prices/BTC-EUR/buy";
	        }
	        else if(messageText.contains("/startMonitor")) {
	        	sendMessage(chat_id, "Inizio monitoraggio variazioni.");
	        	running = true;
	        	while(isRunning()) {
	        		try {
						Thread.sleep(5000);
						String response = sendGet("https://api.coinbase.com/v2/prices/ETH-EUR/spot");
		        		sendMessage(chat_id, response);
					} catch (Exception e) {
						sendMessage(chat_id, e.getMessage());
					}
	        		
	        	}
	        }
	        else if(messageText.contains("/stopMonitor")) {
	        	sendMessage(chat_id, "Fine monitoraggio variazioni.");
	        	running = false;
	        }
	        
	        if(url!=null) {
	        	try {
					outputMessage = sendGet(url);
					
					JSONObject obj = new JSONObject(outputMessage);
					
					JSONObject res = obj.getJSONObject("data");
					String base = res.getString("base");
					String currency = res.getString("currency");
					String amount = res.getString("amount");
					
					outputMessage = "Base: "+base+", Currency: "+ currency+": " + amount;
					
				} catch (IOException e) {
					outputMessage = "Error: " + e.getMessage();
				}
	        }
	        
	        if(outputMessage!=null) {
		        SendMessage message = new SendMessage() // Create a message object object
		                .setChatId(chat_id)
		                .setText(outputMessage);
		        try {
		            sendMessage(message); // Sending our message object to user
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
	        }
	    }
	}

	private String sendGet(String url) throws IOException {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();
	}

	private void sendMessage(long chat_id, String text) {
		SendMessage message = new SendMessage() // Create a message object object
		        .setChatId(chat_id)
		        .setText(text);
		try {
		    sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
		    e.printStackTrace();
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	
}
