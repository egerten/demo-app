package com.example.demoapp.controller;
import com.example.demoapp.model.MyCollection;
import com.example.demoapp.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping(value="/")
public class DemoController {
    private final DemoService demoService;
    private static Logger log = LoggerFactory.getLogger(DemoController.class);

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @RequestMapping(value = "/albums", method = RequestMethod.GET)
    public List<MyCollection> getAlbums(@RequestParam(value="searchText", required=false, defaultValue="search") @Pattern(regexp = "\\D+") String searchText) throws IOException {
        log.info("Albums api started");

        return demoService.getAlbums(searchText);
    }
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<MyCollection> getBooks(@RequestParam(value="searchText", required=false, defaultValue="search") @Pattern(regexp = "\\D+") String searchText) throws IOException {
        log.info("Books api started");

        return demoService.getBooks(searchText);
    }
    @RequestMapping(value="/")
    public String Hello(){
        return "Search Books and Musics";
    }
}
