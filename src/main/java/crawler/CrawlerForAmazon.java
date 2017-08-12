package crawler;

import java.io.*;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzc on 8/10/17.
 */

public class CrawlerForAmazon {
    private static final String AMAZON_QUERY_URL = "https://www.amazon.com/s/ref=nb_sb_noss?field-keywords=";
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36";
    private final String authUser = "ganjiayan";
    private final String authPassword = "Bi7jU9TI";
    private List<String> proxyList;
    private List<String> titleList;
    private List<String> categoryList;
    private BufferedWriter logBufferWriter;
    private int index = 0;

    public CrawlerForAmazon(String proxy_file, String log_file) {
        initProxyIPList(proxy_file);
    }

    private void initProxyIPList(String proxyfile) {
        proxyList = new ArrayList<String>();

        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(proxyfile));
            while((line = bufferedReader.readLine()) != null) {
                String[] proxy = line.split(",");
                String ip = proxy[0].trim();
                System.out.println(ip);
                proxyList.add(ip);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(authUser, authPassword.toCharArray());
            }
        });
    }
}
