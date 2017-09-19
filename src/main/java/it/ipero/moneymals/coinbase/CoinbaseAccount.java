package it.ipero.moneymals.coinbase;

import com.coinbase.api.Coinbase;
import com.coinbase.api.CoinbaseBuilder;

public class CoinbaseAccount {
	
	private static Coinbase cb;
	
	public static Coinbase getInstance () {
		if(cb == null) {
			cb = new CoinbaseBuilder()
                    .withApiKey(System.getenv(CoinbaseConstants.COINBASE_API_KEY), System.getenv(CoinbaseConstants.COINBASE_API_SECRET))
                    .build();
		} 
		return cb;
	}
}
