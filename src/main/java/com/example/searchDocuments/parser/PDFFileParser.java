package com.example.searchDocuments.parser;



import com.example.searchDocuments.model.Resource;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;


@Component
public class PDFFileParser {
    //function parses pdf files to give output in string format
    public void parseFile(List<Resource> outputStream) throws TikaException, IOException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File("/home/gourish/sample.pdf")) ;
        ParseContext pcontext = new ParseContext();
        Parser pdfparser = new AutoDetectParser();
        pdfparser.parse(inputstream, handler, metadata, pcontext);
        System.out.println(handler.toString());
    }
}
