package com.example.whatrubbish.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Document newDoc = Jsoup.connect("https://lajifenleiapp.com/").get();
    }
}
