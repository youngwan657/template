<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%-- documentId를 string으로 설정함. comment에서 Map key로 integer 사용 안 됨. --%>
<c:set var="documentId">${document.documentId}</c:set>

<%-- Document --%>
<div class="accordion-group">
	<div class="accordion-heading">
		<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse${documentId}">${document.title}</a>
	</div>
	
	<div id="collapse${documentId}" class="accordion-body collapse">
		<div class="accordion-inner">
			${document.content}
			
			<form:form method="delete" action="/?documentId=${documentId}">
				<br/><a href="#deleteModal${documentId}" role="button" class="btn btn-small btn-danger" data-toggle="modal">Delete</a>
				<%-- Modal --%>
				<div class="modal hide fade" id="deleteModal${documentId}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3 id="myModalLabel">DELETE</h3>
					</div>
					<div class="modal-body">
						<p>Do you want to delete?</p>
					</div>
					<div class="modal-footer">
						<button class="btn" data-dismiss="modal" aria-hidden="true">No</button>
						<button type="submit" class="btn btn-primary">Yes</button>
					</div>
				</div>
			</form:form>
			
			<%-- Comment --%>
			<c:forEach var="comment" items="${comments[documentId]}">
				<blockquote>
					<p>${comment.content}</p>
				</blockquote>
			</c:forEach>
			<blockquote id="lastCommentPosition${documentId}"></blockquote>
			<div class="span11">
				<textarea id="newComment${documentId}" rows="3" class="span11"></textarea>
				<br />
				<button type="submit" class="btn btn-info" onclick="writeComment(${documentId})">Save</button>
				<br /><br />
			</div>
		</div>
	</div>
</div>