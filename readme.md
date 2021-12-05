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
* 메인페이지 제외 annotation으로 WebServlet 설정 -> main/java/com/controller
```
import javax.servlet.annotation.*;

@WebServlet("/file/*")
public class FileController extends HttpServlet {
....
```
* switch case를 사용 추가 URI WebServlet 설정 
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
1. 필터 (main/java/com/filter/CommonFilter.java)
* doFilter() : 헤더와 푸터 설정
```
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		
		BootStrap.init(request, response); // 사이트 초기화!
		
		if (isPrintOk(request)) {
			printHeader(request, response);
		}
		
		chain.doFilter(request, response); // 헤더(위) 와 푸터(아래) 설정
		
		if (isPrintOk(request)) {
			printFooter(request, response);
		}	
	}
```
* printHeader() : 공통헤드 출력 과 헤더(시멘틱테그-inc폴더) 추가 
```
	private void printHeader(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher rd = request.getRequestDispatcher("/views/outline/header/main.jsp");
		rd.include(request, response);
		
		/** 헤더 추가 영역 처리 */
		Config config = Config.getInstance();
		String addonURL = config.getHeaderAddon();
		if (addonURL != null) {
			RequestDispatcher inc = request.getRequestDispatcher(addonURL);
			inc.include(request, response);
		}
	}
```
* printFooter() : 공통푸터 출력과 푸터(시멘틱테그-inc폴더) 추가
```
	private void printFooter(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		
		/** 푸터 추가 영역 처리 */
		Config config = Config.getInstance();
		String addonURL = config.getFooterAddon();
		if (addonURL != null) {
			RequestDispatcher inc = request.getRequestDispatcher(addonURL);
			inc.include(request, response);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/views/outline/footer/main.jsp");
		rd.include(request, response);
	}
```
* isPrintOk() : 헤더와 푸터의 출력 여부를 결정한다.

2. 사이트 초기화 (main/java/com/core/BootStrap.java)
* init() : 사이트 초기화(doFilter에서 설정)
```
	public static void init(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		/** Req, Res 인스턴스 설정 */
		Req.set(request);
		Res.set(response);
		
		/** 사이트 설정 초기화 */
		Config.init();
		
		/** 로거 초기화 */
		Logger.init();
		
		/** 접속자 정보 로그 */
		Logger.log(request);
		
		/** 로그인 유지 */
		String URI = Req.get().getRequestURI();
		if (URI.indexOf("/resources") == -1) {
			MemberDao.init();
		}
		/** 공통 속성 설정 처리 */
		setAttributes();
	}
```
### 3. 회원가입

### 4. 로그인

### 5. 상품등록

### 6. 주문관리

### 7. 장바구니
