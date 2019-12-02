package com.example.demoapp.service;

import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AsyncService {

    private static Logger log = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    /**
     * It gets albums from Itunes
     * @param  searchText the search text that wanted to be searched in Itunes music search API
     * @return CompletableFuture consists of the search results with in String
     */
    @Async("asyncExecutor")
    public CompletableFuture<String> getAlbums(String searchText) throws InterruptedException {
        log.info("getAlbum starts");

        String album = "";
        try {
            album = restTemplate.getForObject("http://localhost:8080/albums?searchText=" + searchText, String.class);
        }catch (HttpServerErrorException e){
            log.error("500 Internal Server error");
        }

        log.info("album, {}", album);
        Thread.sleep(1000L);
        log.info("album completed");

        return CompletableFuture.completedFuture(album);
    }
    /**
     * It gets albums from Itunes
     * @param  searchText the search text that wanted to be searched in Itunes music search API
     * @return CompletableFuture consists of the search results with in String
     */
    @Async("asyncExecutor")
    public CompletableFuture<String> getBooks(String searchText) throws InterruptedException
    {
        log.info("getBooks starts");
        String books = "";
        try {
            books = restTemplate.getForObject("http://localhost:8080/books?searchText=" + searchText, String.class);
        }catch (HttpServerErrorException e){
            log.error("500 Internal Server error");
        }
        log.info("books, {}", books);
        Thread.sleep(1000L);
        log.info("books completed");
        return CompletableFuture.completedFuture(books);
    }
}