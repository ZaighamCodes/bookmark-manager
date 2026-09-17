package com.savemark.app.controllers;

import com.savemark.app.dto.BookmarkRequest;
import com.savemark.app.models.Bookmark;
import com.savemark.app.models.User;
import com.savemark.app.repositories.BookmarkRepo;
import com.savemark.app.repositories.UserRepo;
import com.savemark.app.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class BookmarkController {
    @Autowired
    private BookmarkService bookmarkService;
    @GetMapping("/bookmarks")
    public ResponseEntity<?> getMyBookmarks() {
         return bookmarkService.getMyBookmarks();
    }

    @PostMapping("/bookmarks")
    public ResponseEntity<?> createBookmarks(@RequestBody BookmarkRequest request)
    {
        return bookmarkService.createBookmarks(request);

    }

    @DeleteMapping("/bookmarks/{bookmarkId}")
    public ResponseEntity<?> deleteBookmark(@PathVariable Long bookmarkId)
    {
        return bookmarkService.deleteBookmark(bookmarkId);
    }
}
