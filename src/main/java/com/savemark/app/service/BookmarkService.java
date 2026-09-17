package com.savemark.app.service;

import com.savemark.app.dto.BookmarkRequest;
import com.savemark.app.models.Bookmark;
import com.savemark.app.models.User;
import com.savemark.app.repositories.BookmarkRepo;
import com.savemark.app.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookmarkService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BookmarkRepo bookmarkRepo;
    public ResponseEntity<?> getMyBookmarks()
    {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String username = authentication.getName();
        User user = userRepo.findByUsername(username);
        List<Bookmark> bookmarks = bookmarkRepo.findByUser(user);
        return ResponseEntity.ok(bookmarks);
    }

    public ResponseEntity<?> createBookmarks(BookmarkRequest request) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        User user=userRepo.findByUsername(username);
        Bookmark bookmark=new Bookmark();
        bookmark.setUser(user);
        bookmark.setTitle(request.getTitle());
        bookmark.setUrl(request.getUrl());
        bookmark.setDescription(request.getDescription());
        bookmarkRepo.save(bookmark);
        return ResponseEntity.ok(Map.of("message","bookmark created"));
    }

    public ResponseEntity<?> deleteBookmark(Long id) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userRepo.findByUsername(username);
        Optional<Bookmark> bookmark=bookmarkRepo.findByBookmarkIdAndUser(id,user);
        if(bookmark.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","Bookmark Not found"));
        }
        bookmarkRepo.delete(bookmark.get());
        return ResponseEntity.ok(Map.of("message","bookmark deleted successfully"));
    }
}
