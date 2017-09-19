package coinbase.api.v2.service;

import org.junit.Test;

import it.ipero.moneymals.coinbase.CoinbaseConstants;

public class TestCoinbaseApiKey {
    
    @Test
    public void testBuildApiKeyGet() throws Exception {
        CoinbaseApiKey key = CoinbaseApiKey.build(CoinbaseConstants.COINBASE_API_KEY, CoinbaseConstants.COINBASE_API_SECRET, Long.toString(System.currentTimeMillis() / 1000), "get", "/v2/user", "{json : \"OK\"}");
        System.out.println(key.getAccessKey());
        System.out.println(key.getAccessSign());
        System.out.println(key.getAccessTimestamp());
    }
    
    @Test
    public void testBuildApiKeyPost() throws Exception {
        CoinbaseApiKey key = CoinbaseApiKey.build(CoinbaseConstants.COINBASE_API_KEY, CoinbaseConstants.COINBASE_API_SECRET, Long.toString(System.currentTimeMillis() / 1000), "POST", "/v2/user", null);
        System.out.println(key.getAccessKey());
        System.out.println(key.getAccessSign());
        System.out.println(key.getAccessTimestamp());
    }

}
