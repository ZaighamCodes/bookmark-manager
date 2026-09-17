package com.savemark.app.repositories;

import com.savemark.app.models.Bookmark;
import com.savemark.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepo extends JpaRepository<Bookmark,Long> {
    List<Bookmark> findByUser(User user);
    Optional<Bookmark> findByBookmarkIdAndUser(
            Long BookmarkId,
            User user
    );
}
