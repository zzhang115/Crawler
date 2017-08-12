package crawler;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by zzc on 8/10/17.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: Crawler <rawQueryDataFilePath> " +
                    "<adsDataFilePath> <proxyFilePath> <logFilePath>");
            System.exit(0);
        }
        ObjectMapper mapper = new ObjectMapper();
        String rawQueryDataFilePath = args[0];
        String adsDataFilePath = args[1];
        String proxyFilePath = args[2];
        String logFilePath = args[3];
        CrawlerForAmazon crawler = new CrawlerForAmazon(proxyFilePath, logFilePath);

    }
}
