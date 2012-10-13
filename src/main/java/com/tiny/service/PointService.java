package com.tiny.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiny.document.Document;
import com.tiny.repository.CommentRepository;
import com.tiny.repository.DocumentRepository;
import com.tiny.repository.MemberRepository;
import com.tiny.repository.UserConnectionRepository;
import com.tiny.social.SecurityContext;

@Service
public class PointService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PointService.class);

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserConnectionRepository userConnectionRepository;

	public void calculatePointToSaveDocument(String providerUserId) {
		memberRepository.increasePointDoc(providerUserId);
	}

	public void calculatePointToDeleteDocument(String providerUserId, int documentId) {
		int count = commentRepository.countWithDocumentId(documentId);
		// TODO:: 성능 상 하나의 쿼리로 수정 필요
		for (int i = 0; i < count; i++) {
			memberRepository.decreaseCommentOnMyDoc(providerUserId);
		}
		memberRepository.decreasePointDocument(providerUserId);
	}

	public void calculatePointToSaveComment(String providerUserId, int documentId) {
		memberRepository.increaseComment(providerUserId);
		Document document = documentRepository.get(documentId);
		memberRepository.increaseCommentOnMyDoc(document.getProviderUserId());
		documentRepository.increaseComment(documentId);
	}

	public void calculatePointToDeleteComment(int documentId, int commentId) {
		memberRepository.decreaseComment(userConnectionRepository.getProviderUserId(SecurityContext
				.getCurrentUser().getId()));
		Document document = documentRepository.get(documentId);
		memberRepository.decreaseCommentOnMyDoc(document.getProviderUserId());
		documentRepository.decreaseComment(commentRepository.getCommentId(commentId).getDocumentId());
	}

	public void calculatePointToShare(int documentId) {
		Document document = documentRepository.get(documentId);
		documentRepository.increaseSharing(documentId);
		memberRepository.increaseSharing(document.getProviderUserId());
		memberRepository.increasePointBeShared(document.getProviderUserId());
	}
	
	public void calculatePointToClickLike(int documentId) {
		Document document = documentRepository.get(documentId);
		documentRepository.increaseLike(documentId);
		memberRepository.increaseLike(SecurityContext.getCurrentUser().getId());
		memberRepository.increaseLikeOnMyDoc(document.getProviderUserId());
	}
	
	public void calculatePointToClickDislike(int documentId) {
		Document document = documentRepository.get(documentId);
		documentRepository.increaseDislike(documentId);
		memberRepository.increaseDislike(SecurityContext.getCurrentUser().getId());
		memberRepository.increaseDislikeOnMyDoc(document.getProviderUserId());
	}
}