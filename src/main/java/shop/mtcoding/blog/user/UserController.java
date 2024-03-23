package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserRepository userRepository;
    private final HttpSession session;

    @PostMapping("/join")
    public String join (UserRequest.JoinDTO reqDTO){
       User user = userRepository.join(reqDTO.toEntity());
       session.setAttribute("sessionUser", user);
       return "redirect:/";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO reqDTO) {
        User user = userRepository.findByUsernameAndPassword(reqDTO);

        if (user != null){
            session.setAttribute("sessionUser", user);
        }
        return "redirect:/";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    @GetMapping("/user/update-form")
    public String updateForm() {
        return "user/update-form";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
}
