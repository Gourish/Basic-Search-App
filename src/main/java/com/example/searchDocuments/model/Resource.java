package com.example.searchDocuments.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.OutputStream;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Resource {
    @Id
    private String id;
    private String fileName;
    private String webLink;
    private byte[] content;
}
