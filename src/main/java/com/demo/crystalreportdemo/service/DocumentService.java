package com.demo.crystalreportdemo.service;

import java.util.List;

import com.demo.crystalreportdemo.domain.Document;

public interface DocumentService {

    public Iterable<Document> getAllDocument();

    public Document getDocumentById(String id);

    public Document saveDocument(Document doc);

    public void deleteDocument(String id);
    
    public List<Document> getDocumentByUserId(String id);
}
