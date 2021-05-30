package com.example.searchDocuments.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "search")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TextDocument {
    @Id
    private String id;
    @Field(name = "name")
    private String fileName;
    private String webURL;
    private String content;

}