package com.project.stocktrading.controller.Login;


import com.project.stocktrading.models.User.IUser;
import com.project.stocktrading.models.User.User;
import com.project.stocktrading.service.User.IUserAuthServiceActions;
import com.project.stocktrading.service.User.IUserServiceActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Raj_Valand
 */

@Controller
public class UserAuthController {
    private static final String usernameTaken = "Email is already registered ! Please use another one";
    private final IUserServiceActions iUserServiceActions;
    private final IUserAuthServiceActions iUserAuthServiceActions;

    @Autowired
    public UserAuthController(IUserServiceActions iUserServiceActions, IUserAuthServiceActions iUserAuthServiceActions) {
        this.iUserServiceActions = iUserServiceActions;
        this.iUserAuthServiceActions = iUserAuthServiceActions;
    }

    @GetMapping("/user_signup")
    public String userSignUp(Model model) {
        model.addAttribute("user", new User());
        return "user/signup";
    }

    @PostMapping("/user_signup")
    public RedirectView userSignUpAccepted(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        boolean usernameExists = false, residentialIdExists = false, passwordPattern = false;
        passwordPattern = iUserAuthServiceActions.isPasswordNotValid(user.getPassword());
        residentialIdExists = iUserAuthServiceActions.residentialIdAlreadyExists(user.getResidentialId());
        usernameExists = iUserAuthServiceActions.userIsAlreadyExists(user.getEmail());
        if (usernameExists || residentialIdExists || passwordPattern) {
            redirectAttributes.addFlashAttribute("message", usernameTaken);
            return new RedirectView("user_signup", true);
        } else {
            this.iUserServiceActions.userRegistration(user);
            return new RedirectView("sign_in", true);
        }
    }

    @GetMapping("/sign_in")
    public String userLogin(Model model) {
        model.addAttribute("user", new User());
        return "user/signin";
    }

    @GetMapping("/admin_signin")
    public String adminLogin(Model model) {
        model.addAttribute("user", new User());
        return "admin_signin";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest servletRequest) {
        servletRequest.getSession().invalidate();
        this.iUserServiceActions.userLoggedOut();
        return "redirect:/sign_in";
    }

    @GetMapping("/user_home")
    public String userHome(HttpSession httpSession, Model model) {
        @SuppressWarnings("unchecked")
        List<String> msg = (List<String>) httpSession.getAttribute("MY_SESSION_MESSAGE");
        if (msg == null) {
            msg = new ArrayList<>();
        }
        model.addAttribute("sessionMessage", msg);
        return "user/userhome";
    }

    @PostMapping(value = "/user_home")
    public String userLoginAccepted(@RequestParam("name") String name, @RequestParam String password, HttpServletRequest servletRequest, Model model) {
        boolean loginCredential = false;
        loginCredential = iUserAuthServiceActions.isUserCredentialValid(name, password);
        if (loginCredential) {
            IUser user = this.iUserServiceActions.getUserInfoFromEmail(name);
            List<String> msg = (List<String>) servletRequest.getSession().getAttribute("MY_SESSION_MESSAGE");
            if (msg == null) {
                msg = new ArrayList<>();
                servletRequest.getSession().setAttribute("MY_SESSION_MESSAGE", msg);
            }
            msg.add(name);
            servletRequest.getSession().setAttribute("MY_SESSION_MESSAGE", msg);
            this.iUserServiceActions.updateUserLogedIn(name);
            return "redirect:/watchlisthome/1";
        } else {
            return "redirect:/sign_in";
        }
    }

    @GetMapping("/admin_home")
    public String adminHome(HttpSession httpSession, Model model) {
        List<String> msg = (List<String>) httpSession.getAttribute("MY_SESSION_MESSAGE");
        if (msg == null) {
            msg = new ArrayList<>();
        }
        model.addAttribute("sessionMessage", msg);
        return "admin/adminhome";
    }

    @PostMapping(value = "/admin_home")
    public String adminLoginAccepted(@RequestParam("name") String name, @RequestParam String password) {
        boolean loginCredential = false;
        loginCredential = iUserAuthServiceActions.isAdminCredentialValid(name, password);
        if (loginCredential) {
            return "admin/adminhome";
        } else {
            return "user/loginfailed";
        }
    }
}
