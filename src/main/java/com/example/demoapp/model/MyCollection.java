package com.example.demoapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edagul Erten
 *
 */
public class MyCollection {
    private int id;
    private String title;
    private List<String> authors;
    private Info info;

    public MyCollection() {

    }

    public void Collection(){
        this.authors = new ArrayList<String>();
    }
    public MyCollection(int id, String title, String author, Info info) {
        this.id = id;
        this.title = title;
        this.authors = new ArrayList<String>();
        this.authors.add(author);
        this.info = info;
    }

    /**
     * @param id
     * @param title
     * @param authors
     * @param info
     */
    public MyCollection(int id, String title, List authors, Info info) {
        this.id = id;
        this.title = title;
        this.authors = new ArrayList<String>();
        this.authors = authors;
        this.info = info;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", info=" + info +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(ArrayList authors) {
        this.authors = authors;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return this.authors;
    }

    public Info getInfo() {
        return info;
    }
}
