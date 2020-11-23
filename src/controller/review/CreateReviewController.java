package controller.review;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import service.dto.Review;

public class CreateReviewController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		private String reviewId;
//		private String userId;
//		private String recipeId;
//		private String recipeName;
//		private String content;
//		private int rating;
//		private Date published;
		
		Date date_now = new Date(System.currentTimeMillis()); // ����ð��� ������ Date������ �����Ѵ�
		// ����Ͻú��� 14�ڸ� ����
		SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy/MM/dd"); 
		String published = fourteen_format.format(date_now);
		
		Review review = new Review(
				null, null, null, null,
				request.getParameter("content"),
				request.getParameter("rating"),
				request.getParameter("content"),
				published
				);
		
		return null;
	}

}
