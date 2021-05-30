package com.example.searchDocuments.parser;



import com.example.searchDocuments.model.Resource;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class FileParser {
    //function parses pdf files to give output in string format
    public Map<Resource,String> parseFile(List<Resource> resources) throws TikaException, IOException, SAXException {
        Map<Resource,String> parsedContent = new HashMap<>();
        Parser parser = new AutoDetectParser();
        //FileInputStream inputstream = new FileInputStream(new File("/home/gourish/sample.pdf")) ;
        for(Resource resource : resources) {
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            ParseContext pcontext = new ParseContext();
            ByteArrayInputStream inputstream = new ByteArrayInputStream(resource.getContent());
            parser.parse(inputstream, handler, metadata, pcontext);
            parsedContent.put(resource,handler.toString());
        }
            return parsedContent;
    }
}
