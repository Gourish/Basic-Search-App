package com.example.searchDocuments.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.OutputStream;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Resource {
    private String fileName;
    private String webLink;
    private byte[] content;
}
