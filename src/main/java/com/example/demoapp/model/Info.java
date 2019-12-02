package com.example.demoapp.model;

/**
 * Info Enum to specify whether it is a book or album
 */
public enum Info {
    BOOK {
        public String toString() {
            return "BOOK";
        }
    },

    ALBUM {
        public String toString() {
            return "ALBUM";
        }
    }
}
