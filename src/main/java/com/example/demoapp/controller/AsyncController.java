package com.example.demoapp.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.example.demoapp.service.AsyncService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping(value="/")
public class AsyncController {

    private static Logger log = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private AsyncService service;

    /**
     * @param searchText
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/app", method = RequestMethod.GET)
    @ResponseBody
    public JsonNode testAsync(@RequestParam(value="searchText", required=false, defaultValue="search") @Pattern(regexp = "\\D+") String searchText) throws InterruptedException, ExecutionException, IOException {
        log.info("Started");

        CompletableFuture<String> albums = service.getAlbums(searchText);
        CompletableFuture<String> books = service.getBooks(searchText);

        // Async call for wait for 2 api call is finished
        CompletableFuture.allOf(albums, books).join();

        //Logging
        log.info("Albums--> " + albums.get());
        log.info("Books--> " + books.get());

        //To show the text as UTF-8 format
        byte ptext[] = books.get().getBytes();
        String result = new String(ptext, "UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(result);
        JsonNode actualObj = mapper.readTree(parser);


        return actualObj;
    }
}