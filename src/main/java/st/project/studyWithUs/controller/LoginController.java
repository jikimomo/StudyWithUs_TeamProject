package st.project.studyWithUs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import st.project.studyWithUs.service.naverService.NaverService;
import st.project.studyWithUs.domain.User;
import st.project.studyWithUs.interceptor.SessionConst;
import st.project.studyWithUs.service.kakaoService.KakaoService;
import st.project.studyWithUs.service.userService.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/loginUser")
    @ResponseBody
    public String loginCheck(HttpServletRequest request) {

        String id = request.getParameter("id");
        String pw = request.getParameter("pw");

        log.info("id : {}", id);
        log.info("pw : {}", pw);

        User loginUser = userService.login(id, pw);

        if (loginUser == null) {
            return "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else {

            HttpSession session = request.getSession();
            // 세션에 LOGIN_USER라는 이름(SessionConst.class에 LOGIN_USER값을 "loginUser")을 가진 상자에 loginUser 객체를 담음.
            // 즉, 로그인 회원 정보를 세션에 담아놓는다.
            session.setAttribute(SessionConst.LOGIN_USER, loginUser);
            // users/login으로 매핑하는 컨트롤러를 찾아간다. (HomeController에 있다)

            String access_Token = (String) session.getAttribute("access_Token");
            //카카오 토큰 삭제. 왜냐? 이전 사용자가 카카오 서비스 계정 로그아웃 안하고 이후 사람이 일반 로그인 할 경우 보안에 문제가 생기기 때문에.
            if (access_Token != null && !"".equals(access_Token)) {
                kakaoService.kakaoLogout(access_Token);
                session.removeAttribute("access_Token");
                session.removeAttribute("userId");
            }

            if (loginUser.getRole().equals("admin")) {
                return "/adminPage";
            } else return "/";

        }
    }

    // client_id 숨기기 위해 ajax로 전송한다.
    @ResponseBody
    @PostMapping("/getInfo")
    public HashMap<String,String> getInfo(){
        String state = UUID.randomUUID().toString();

        HashMap<String,String> res = new HashMap<>();

        res.put("client_id","quDmxXiN1GypP5C8hzbi");
        res.put("state",state);

        return res;
    }

    @GetMapping("/naverLogin")
    public String naver(String code, HttpServletRequest request, HttpServletResponse response) throws IOException{
        log.info("코드 : {}", code);
        String access_Token = naverService.getNaverToken(code);
        log.info("토큰 : {}", access_Token);

        HashMap<String, Object> userInfo = naverService.getUserInfo(access_Token);
        log.info("name : {}", userInfo.get("name"));
        log.info("email : {}", userInfo.get("email"));


        HttpSession session = request.getSession();

        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }

        log.info("access_token : {}", access_Token);

        String name = userInfo.get("name").toString();
        String email = userInfo.get("email").toString();

        User loginUser = userService.getNameEmail(name, email);

        if (loginUser == null) {
            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();

            out.println("<script>alert('가입한 정보가 없습니다.\\n 네이버 아이디와 연동을 원하신다면 네이버와 연동된 E-mail과 이름으로 가입하세요.'); location.href='/signUp';</script>");
            out.flush();
            return "redirect:/signUp";
        } else {

            session.setAttribute(SessionConst.LOGIN_USER, loginUser);

            if (loginUser.getRole().equals("admin")) {
                return "/adminPage";
            }
            return "home";
        }
    }

    @GetMapping("/kakaoLogin")
    public String kakao(String code, HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("코드 : {}", code);
        String access_Token = kakaoService.getKaKaoAccessToken(code);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);

        HttpSession session = request.getSession();

        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }

        log.info("name : {}", userInfo.get("nickname"));
        log.info("email : {}", userInfo.get("email"));
        log.info("access_token : {}", access_Token);

        Object s1 = userInfo.get("nickname");
        Object s2 = userInfo.get("email");

        String nickname = s1.toString();
        String email = s2.toString();

        User loginUser = userService.getNameEmail(nickname, email);

        if (loginUser == null) {
            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();

            out.println("<script>alert('가입한 정보가 없습니다.\\ n카카오 아이디와 연동을 원하신다면 카카오와 연동된 E-mail로 가입하세요.'); location.href='/signUp';</script>");
            out.flush();
            return "redirect:/signUp";
        } else {

            session.setAttribute(SessionConst.LOGIN_USER, loginUser);

            if (loginUser.getRole().equals("admin")) {
                return "/adminPage";
            }
            return "home";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션 값을 담아온다.
        log.info("로그아웃");
        HttpSession session = request.getSession(false);
        String access_Token = (String)session.getAttribute("access_Token");

        //소셜 로그인 토큰 삭제
        if(access_Token != null && !"".equals(access_Token)) {
            kakaoService.kakaoLogout(access_Token); // 카카오
            naverService.naverLogout(access_Token); // 네이버
            session.removeAttribute("access_Token");
            session.removeAttribute("userId");
        }

        //현재 담겨져있는 세션값이 존재한다면 세션을 드랍한다.
        if(session !=null){
            session.invalidate();
        }
        // 로그인 페이지로 이동
        return "redirect:/login";
    }



}

