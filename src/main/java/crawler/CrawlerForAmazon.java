package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzc on 8/10/17.
 */

public class CrawlerForAmazon {
    private static final String AMAZON_QUERY_URL = "https://www.amazon.com/s/ref=nb_sb_noss?field-keywords=";
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36";
    private static final String USER_AGENT2 = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0";
    private final String authUser = "ganjiayan";
    private final String authPassword = "Bi7jU9TI";
    private List<String> proxyList;
    private List<String> titleList;
    private List<String> categoryList;
    private BufferedWriter logBufferWriter;
    private int proxyIndex = 0;

    public CrawlerForAmazon(String proxy_file, String log_file) {
        initProxyIPList(proxy_file);
//        testProxy();
    }

    private void initProxyIPList(String proxyfile) {
        proxyList = new ArrayList<String>();

        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(proxyfile));
            while ((line = bufferedReader.readLine()) != null) {
                String[] proxy = line.split(",");
                String ip = proxy[0].trim();
                proxyList.add(ip);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置代理
        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyUser", authUser);
        System.setProperty("http.proxyPassword", authPassword);
        System.setProperty("http.proxyHost", "199.241.144.137");
        System.setProperty("http.proxyPort", "61336");
        String test_url = "http://www.toolsvoid.com/what-is-my-ip-address";

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(authUser, authPassword.toCharArray());
            }
        });
        try {
            Document doc = Jsoup.connect(test_url).userAgent("Mozilla").timeout(10000).get();
            String iP = doc.select("body > section.articles-section > div > div > div > div.col-md-8.display-flex > div > div.table-responsive > table > tbody > tr:nth-child(1) > td:nth-child(2) > strong").first().text(); //get used IP.
            System.out.println("IP-Address: " + iP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initHtmlSelector() {
        titleList = new ArrayList<String>();
        titleList.add(" > div > div > div > div.a-fixed-left-grid-col.a-col-right > div.a-row.a-spacing-small > div:nth-child(1)  > a > h2");
        titleList.add(" > div > div > div > div.a-fixed-left-grid-col.a-col-right > div.a-row.a-spacing-small > a > h2");

        categoryList = new ArrayList<String>();
        categoryList.add("#refinements > div.categoryRefinementsSection > ul.forExpando > li > a > span.boldRefinementLink");
        categoryList.add("#refinements > div.categoryRefinementsSection > ul.forExpando > li:nth-child(1) > a > span.boldRefinementLink");
    }

    private void initLog(String logPath) {
        try {
            File log = new File(logPath);
            if (!log.exists()) {
                log.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(log.getAbsoluteFile());
            logBufferWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProxy() {
        if (proxyIndex == proxyList.size()) {
            proxyIndex = 0;
        }
        String proxy = proxyList.get(proxyIndex++);
        System.setProperty("socketProxyHost", proxy);
    }
//199.241.144.137,60099,61336
    private void testProxy() {
        System.setProperty("socksProxyHost", "199.241.144.137"); // set proxy server
        System.setProperty("socksProxyPort", "61336"); // set proxy port
        String test_url = "http://www.toolsvoid.com/what-is-my-ip-address";
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection(Proxy.NO_PROXY);
            connection.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            Document doc = Jsoup.connect(test_url).userAgent(USER_AGENT2).timeout(10000).get();
//            String iP = doc.select("body > section.articles-section > div > div > div > div.col-md-8.display-flex > div > div.table-responsive > table > tbody > tr:nth-child(1) > td:nth-child(2) > strong").first().text(); //get used IP.
//            System.out.println("IP-Address: " + iP);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

