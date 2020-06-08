package com.boc.crystalreportdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.boc.crystalreportdemo.domain.Document;
import com.boc.crystalreportdemo.domain.User;
import com.boc.crystalreportdemo.repositories.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService{

	@Autowired
	private DocumentRepository documentRepository;

	@Override
	public Iterable<Document> getAllDocument() {
		return documentRepository.findAll();
	}

	@Override
	public Document getDocumentById(String id) {
		return documentRepository.getOne(id);
	}

	@Override
	public Document saveDocument(Document doc) {
		return documentRepository.save(doc);
	}

	@Override
	public void deleteDocument(String id) {
		documentRepository.deleteById(id);
	}

	@Override
	public List<Document> getDocumentByUserId(String userId) {
	
		
		User user = new User();
		user.setUserId(userId);
		Document doc = new Document();
		doc.setUser(user);
		
		List<Document> result = documentRepository.findAll(Example.of(doc));
		return result;
	}


}
