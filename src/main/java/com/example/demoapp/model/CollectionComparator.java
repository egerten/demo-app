package com.example.demoapp.model;

import java.util.Comparator;

public class CollectionComparator implements Comparator<MyCollection> {
    @Override
    public int compare(MyCollection o1, MyCollection o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
