package com.example.demoapp.service;

import com.example.demoapp.model.CollectionComparator;
import com.example.demoapp.model.MyCollection;
import com.example.demoapp.model.Info;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
@Service
public class DemoService {
    private static Logger log = LoggerFactory.getLogger(AsyncService.class);

    private static final int COUNT = 5;

    private List<MyCollection> collections = new ArrayList<>();

    /**
     * Returns the results of the Itunes Music API search results
     * for a given content.
     * <p>
     * This method always returns immediately, whether or not the
     * albums exists related with the search term.
     * @param  searchText the search text that wanted to be searched in Itunes music search API
     * @return      the list of collection returns from API in MyCollection type that contains
     * id, title, authors/artists, and whether it is a book or an album
     */
    public List<MyCollection> getAlbums(@Pattern(regexp = "\\D+") String searchText) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();

        String albums = restTemplate.getForObject(
                "https://itunes.apple.com/search?term="+searchText+"&limit=5", String.class);
        if(albums.length() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Albums not found");
        }else {
            try {
                JsonParser parser = factory.createParser(albums);
                JsonNode albumJson = mapper.readTree(parser);

                for (int i = 0; i < albumJson.get("resultCount").intValue(); i++) {
                    String artist = albumJson.get("results").get(i).get("artistName").toString();
                    String title = albumJson.get("results").get(i).get("trackName").toString();
                    MyCollection c = new MyCollection(i, title, artist, Info.ALBUM);
                    collections.add(c);
                }
            } catch (Error e) {
                log.error(e.toString());
            }
        }
        Collections.sort(collections, new CollectionComparator());
        return collections;
    }
    /**
     * Returns the results of the Google Books API search results
     * for a given content.
     * <p>
     * This method always returns immediately, whether or not the
     * books exists related with the search term.
     * @param  searchText the search text that wanted to be searched in Google books API
     * @return      the list of collection returns from API in MyCollection type that contains
     * id, title, authors/artists, and whether it is a book or an album
     */
    public List<MyCollection> getBooks(@Pattern(regexp = "\\D+") String searchText) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();

        String jsonString = "";

        jsonString = restTemplate.getForObject(
                "https://www.googleapis.com/books/v1/volumes?q={searchText}&maxResults=5", String.class, searchText);

        JsonParser parser = factory.createParser(jsonString);
        JsonNode actualObj = mapper.readTree(parser);

        for (int i = 0; i < COUNT; i++) {
            List<String> artists = new ArrayList<>();
            if(actualObj != null) {
                JsonNode author = actualObj.get("items").get(i).get("volumeInfo").get("authors");
                if(author != null) {
                    for (int j = 0; j < author.size(); j++) {
                        artists.add(author.get(j).toString());
                    }
                    String title = actualObj.get("items").get(i).get("volumeInfo").get("title").toString();
                    MyCollection c = new MyCollection(i, title, artists, Info.BOOK);
                    collections.add(c);
                }
            }
        }

        Collections.sort(collections, new CollectionComparator());
        return collections;
    }
}
