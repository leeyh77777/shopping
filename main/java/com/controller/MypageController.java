package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.annotation.*;
import com.core.Logger;
import com.models.member.Member;
import com.models.member.MemberDao;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/member/mypage/*")
public class MypageController extends HttpServlet{

	private String httpMethod; // Http 요청 메서드, GET, POST
	private PrintWriter out;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/** 요청 메서드 */
		httpMethod = request.getMethod().toUpperCase();
		if (httpMethod.equals("GET")) {
			response.setContentType("text/html; charset=utf-8");
		} else { // GET이 아닌 경우 -> 유입된 입력 양식 데이터 처리
			response.setCharacterEncoding("utf-8");
		}
		
		out = response.getWriter();

		String URI = request.getRequestURI();
		String mode = URI.substring(URI.lastIndexOf("/") + 1);
		switch(mode) {
			case "mypage" : // 마이페이지
				mypageController(request, response);
				break;
			case "account" : // 계정관리
				accountController(request, response);
				break;
			case "address" : // 장바구니
				addressController(request, response);
				break;
			default : // 없는 페이지 
				RequestDispatcher rd = request.getRequestDispatcher("/views/error/404.jsp");
				rd.forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 마이페이지
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void mypageController (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (httpMethod.equals("GET")) {
		RequestDispatcher rd = request.getRequestDispatcher("/views/member/mypage.jsp");
		rd.include(request, response);
		}
	}
	
	/**
	 * 계정 관리(회원정보수정) /member/mypage/account
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void accountController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member member = null;
		try {
			if (request.getAttribute("member") != null) {
				member = (Member)request.getAttribute("member");
			}
			
			if (member == null) {
				throw new Exception("수정할 회원 정보가 없습니다.");
			}
		} catch (Exception e) {
			Logger.log(e);
			out.printf("<script>alert('%s');history.back();</script>", e.getMessage());
			return;
		}
		
		if (httpMethod.equals("GET")) { // 수정 양식
			String socialType = member.getSocialType();
			
			request.setAttribute("socialType", socialType);
			request.setAttribute("action", "../mypage/account");
			RequestDispatcher rd = request.getRequestDispatcher("/views/mypage/account.jsp");
			rd.include(request, response);
		} else { // 수정 처리 
			MemberDao dao = MemberDao.getInstance();
			try {
				dao.updateInfo(request);
				out.println("<script>alert('수정되었습니다.');parent.location.reload();</script>");
			} catch (Exception e) {
				Logger.log(e);
				out.printf("<script>alert('%s');</script>", e.getMessage());
			}
		}
	}
	

	
	/**
	 * 배송지 관리 /member/mypage/address
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addressController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member member = null;
		try {
			if (request.getAttribute("member") != null) {
				member = (Member)request.getAttribute("member");
			}
			
			if (member == null) {
				throw new Exception("수정할 회원 주소가 없습니다.");
			}
		} catch (Exception e) {
			Logger.log(e);
			out.printf("<script>alert('%s');history.back();</script>", e.getMessage());
			return;
		}
		
		if (httpMethod.equals("GET")) { // 수정 양식
			String URI = request.getRequestURI();
			String mode = URI.substring(URI.lastIndexOf("/") + 1);
			
			request.setAttribute("mode", mode);
			request.setAttribute("action", "../mypage/address");
			RequestDispatcher rd = request.getRequestDispatcher("/views/mypage/address.jsp");
			rd.include(request, response);
		} else { // 수정 처리 
			MemberDao dao = MemberDao.getInstance();
			try {
				dao.updateInfo(request);
				out.println("<script>alert('배송지가 수정되었습니다.');parent.location.reload();</script>");
			} catch (Exception e) {
				Logger.log(e);
				out.printf("<script>alert('%s');</script>", e.getMessage());
			}
		}
	}
}