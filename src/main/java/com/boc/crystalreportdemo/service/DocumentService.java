package com.boc.crystalreportdemo.service;

import java.util.List;

import com.boc.crystalreportdemo.domain.Document;

public interface DocumentService {

    public Iterable<Document> getAllDocument();

    public Document getDocumentById(String id);

    public Document saveDocument(Document doc);

    public void deleteDocument(String id);
    
    public List<Document> getDocumentByUserId(String id);
}
