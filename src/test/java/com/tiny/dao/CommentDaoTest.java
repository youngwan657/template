package com.tiny.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tiny.comment.Comment;
import com.tiny.common.CommonTest;
import com.tiny.document.Document;

public class CommentDaoTest extends CommonTest {
	@Autowired
	private CommentDao commentDao;

	@Autowired
	private DocumentDao documentDao;
	
	private Document document;

	@Transactional
	@Before
	public void setUp() {
		documentDao.save(getDocument());
		document = documentDao.getLast();
	}

	@Test
	public void testInsertComment() {
		// Given
		int count = commentDao.count();

		// When
		commentDao.save(getComment(document.getDocumentId()));

		// Then
		assertThat(commentDao.count(), is(count + 1));
	}
	
	@Test
	public void testGetComments() {
		// Given
		commentDao.save(getComment(document.getDocumentId()));
		
		// When
		List<Comment> comments = commentDao.get(document.getDocumentId());

		// Then
		assertThat(comments.get(0).getProviderUserId(), is(getComment(document.getDocumentId()).getProviderUserId()));
	}

	@Test
	public void testUpdateComment() {
		// Given
		commentDao.save(getComment(document.getDocumentId()));
		int count = commentDao.count();

		// When
		commentDao.update(getComment(document.getDocumentId()));

		// Then
		assertThat(commentDao.count(), is(count));
	}

	@Test
	public void testDeleteComment() {
		// Given
		commentDao.save(getComment(document.getDocumentId()));
		int count = commentDao.count();
		
		// When
		commentDao.delete(commentDao.getLastCommentId());

		// Then
		assertThat(commentDao.count(), is(count - 1));
	}

	@Test
	public void testDeleteCommentWithDocumentId() {
		// Given
		commentDao.save(getComment(document.getDocumentId()));
		commentDao.save(getComment(document.getDocumentId()));
		int count = commentDao.count();

		// When
		commentDao.deleteWithDocumentId(document.getDocumentId());

		// Then
		assertThat(commentDao.count(), is(count - 2));
	}

	private Comment getComment(int documentId) {
		Comment comment = new Comment();
		comment.setDocumentId(documentId);
		comment.setContent("content");
		comment.setProviderUserId("userId");
		comment.setRegDate(new Date());
		return comment;
	}

	private Document getDocument() {
		Document document = new Document();
		document.setContent("content");
		document.setProviderUserId("userId");
		document.setIpAddress("127.0.0.1");
		document.setRegDate(new Date());
		return document;
	}
}