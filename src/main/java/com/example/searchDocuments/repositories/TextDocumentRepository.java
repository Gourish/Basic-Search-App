package com.example.searchDocuments.repositories;

import com.example.searchDocuments.model.ResponseForSearch;
import com.example.searchDocuments.model.TextDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextDocumentRepository extends ElasticsearchRepository<TextDocument,String> {
    public List<TextDocument> findByContentContaining(String keyWord);
}
