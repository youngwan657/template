package com.tiny.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiny.dao.DocumentDao;
import com.tiny.document.Document;

@Repository
public class DocumentRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentRepository.class);

	@Autowired
	private DocumentDao documentDao;

	public void save(Document document) {
		documentDao.saveDocument(document);
	}

	public List<Document> getAll() {
		return documentDao.getAllDocument();
	}
	
	public Document getLastDocument() {
		return documentDao.getLastDocument();
	}

	public void delete(Integer documentId) {
		documentDao.deleteDocument(documentId);
	}
}