package com.example.searchDocuments.service;

import com.example.searchDocuments.exception.DocumentIndexDeleteException;
import com.example.searchDocuments.model.Resource;
import com.example.searchDocuments.model.ResponseForSearch;
import com.example.searchDocuments.model.TextDocument;
import com.example.searchDocuments.repositories.TextDocumentRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextDocumentService {
    @Autowired
    private TextDocumentRepository textDocumentRepository;

    public void addTextDocument(TextDocument textDocument)
    {
        TextDocument r = textDocumentRepository.save(textDocument);
         System.out.println(r.getId());
    }
    public void addAllTextDocuments(List<TextDocument> textDocuments) {
        textDocumentRepository.saveAll(textDocuments);

    }
    public List<ResponseForSearch> findDocuments(String keyWord) {
            if(keyWord.contains(" "))
            {
                throw new InvalidDataAccessApiUsageException("search query should contain single word");
            }
           List<TextDocument> results = textDocumentRepository.findByContentContaining(keyWord);
            List<ResponseForSearch> responseForSearch = results.stream().map(result-> new ResponseForSearch(result.getFileName(),result.getWebURL())).collect(Collectors.toList());
            return responseForSearch;
    }

    public void removeAllDocuments() throws DocumentIndexDeleteException {
        try {
            textDocumentRepository.deleteAll();
        } catch (Exception e) {
            throw new DocumentIndexDeleteException(e);
        }

    }

    public void createTextDocumentFromResource(Map<Resource,String>resourceStringMap)
    {
        List<TextDocument> textDocuments  = new ArrayList<>();
        for(Map.Entry<Resource,String> resourceStringEntry : resourceStringMap.entrySet())
        {
            if(!resourceStringEntry.getKey().getFileName().equals("steganography.pdf"))
            {
                String id = resourceStringEntry.getKey().getId();
                String name = resourceStringEntry.getKey().getFileName();
                String webLink = resourceStringEntry.getKey().getWebLink();
                String content = resourceStringEntry.getValue();
                textDocuments.add(new TextDocument(id, name, webLink, content));
            }
        }
        addAllTextDocuments(textDocuments);
    }
}
