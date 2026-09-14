package com.savemark.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookmarkController {

    @GetMapping("bookmarks")
    public String getBookmarks()
    {
        return "Hello i am working";
    }
}
