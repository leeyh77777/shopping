# Diet Shopping Mall
* JSP로 구현한 다이어트 음식 쇼핑몰 (<http://.cafe24.com/>)
## 개요
### 1. 서비스 내용 
1. 다이어트 음식 카테고리별 링크 구현
2. 장바구니에 원하는 상품을 담을수 있게 구현
3. 마이페이지에서 회원정보 변경

### 2. 적용기술
<img src="https://img.shields.io/badge/JSP-4FC08D?style=for-the-badge&logo=JSP&logoColor=white"> <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/html-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

### 3. 각 페이지별 소개

## 핵심기술

### 1. 서블릿
* web.xml -> 메인페이지 WebServlet 설정 
```
	<servlet>
		<servlet-name>main</servlet-name>
		<servlet-class>com.controller.MainController</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>main</servlet-name>
		<url-pattern>/index.jsp</url-pattern>
	</servlet-mapping>
```
* 메인페이지 제외 annotation WebServlet 설정 -> main/java/com/controller
```
import javax.servlet.annotation.*;

@WebServlet("/file/*")
public class FileController extends HttpServlet {
....
```
* switch case를 사용 URI 추가 WebServlet 설정 
```
// ex
public class FileController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String URI = request.getRequestURI(); 
		String mode = URI.substring(URI.lastIndexOf("/") + 1);
		try {
			FileDao dao = FileDao.getInstance();
			switch (mode) {
				/** 파일 업로드 */
				case "upload" : 
					String json = dao.uploadJson(request);
					response.setHeader("Content-Type", "application/json");
					response.getWriter().print(json);
					break;
				/** 파일 다운로드 */
				case "download" :
					dao.download(request);
					break;
				/** 파일 삭제 */
				case "delete" : 
					String djson = dao.deleteJson(request);
					response.setHeader("Content-Type", "application/json");
					response.getWriter().print(djson);
					break;				
			}
		} catch (Exception e) {
			CommonLib.printJson(e);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response); 
	}
}
...
```

### 2. 페이지 초기화

### 3. 회원가입

### 4. 로그인

### 5. 상품등록

### 6. 주문관리

### 7. 장바구니
