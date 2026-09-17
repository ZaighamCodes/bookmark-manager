package com.savemark.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkRequest {
    @NonNull
    private String title;
    @NonNull
    private String url;
    private String description;
}
