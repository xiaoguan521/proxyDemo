package xyz.sy007.proxydemo.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.sy007.proxydemo.dao.UrlResponseRepository;
import xyz.sy007.proxydemo.entity.UrlResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProxySelector;

@Service
public class UrlResponseService {
    @Autowired
    private UrlResponseRepository repository;
// -Dhttps.proxyHost=localhost -Dhttps.proxyPort=10809
    public boolean processUrl(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String responseData = restTemplate.getForObject(url, String.class);
            UrlResponse urlResponse = new UrlResponse();
            urlResponse.setUrl(url);
            urlResponse.setResponseData(responseData);
            repository.save(urlResponse);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean processUrl2(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
//            HttpHost proxy = new HttpHost("127.0.0.1", Integer.parseInt("10809"));
//            RequestConfig requestConfig=RequestConfig.custom().setConnectTimeout(50000).setSocketTimeout(60000).setConnectionRequestTimeout(100000).setRedirectsEnabled(false).setProxy(proxy).build();
//            httpGet.setConfig(requestConfig);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                // Check if the request was successful (status code 2xx)
                if (response.getStatusLine().getStatusCode() / 100 == 2) {
                    // If successful, read and print the response content
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                    }
                    UrlResponse urlResponse = new UrlResponse();
                    urlResponse.setUrl(url);
                    urlResponse.setResponseData(String.valueOf(content));
                    repository.save(urlResponse);
//                    System.out.println("Response Content: " + content.toString());
                    return true; // Indicate successful processing
                } else {
                    // If not successful, log an error
                    System.err.println("Error: " + response.getStatusLine().getStatusCode() + " - " +
                            response.getStatusLine().getReasonPhrase());
                    return false; // Indicate unsuccessful processing
                }
            }
        } catch (IOException e) {
            // Handle IOException (e.g., connection timeout, network issues)
            e.printStackTrace();
            return false; // Indicate unsuccessful processing
        }

    }
    public boolean processUrl3(String url) {
        try{
        String content=req(url);
                    UrlResponse urlResponse = new UrlResponse();
                    urlResponse.setUrl(url);
                    urlResponse.setResponseData(String.valueOf(content));
                    repository.save(urlResponse);
//                    System.out.println("Response Content: " + content.toString());
                    return true; // Indicate successful processing


        } catch (Exception e) {
            // Handle IOException (e.g., connection timeout, network issues)
            e.printStackTrace();
            return false; // Indicate unsuccessful processing
        }

    }
    public static String req(String url){
        CloseableHttpClient httpClient=null;
        String res="";
        try {
            HttpHost proxy = new HttpHost("127.0.0.1", Integer.parseInt("10809"));
            RequestConfig requestConfig=RequestConfig.custom().setConnectTimeout(50000).setSocketTimeout(60000).setConnectionRequestTimeout(100000).setRedirectsEnabled(false).setProxy(proxy).build();
            httpClient=HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
//            SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(
//                    ProxySelector.getDefault());
//             httpClient = HttpClients.custom()
//                    .setRoutePlanner(routePlanner)
//                    .build();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            res = EntityUtils.toString(execute.getEntity());

        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
