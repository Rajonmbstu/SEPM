package com.example.TestSpringJPA;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // READ: List all users
    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    // INSERT: Handle new user form submission
    @PostMapping("/users")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    // DELETE: Delete a user by ID
    @PostMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    // UPDATE (Step 2): Handle form submission
    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute User user) {
        userService.saveUser(user);  // saveUser will update if ID exists
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);  // Must match th:object="${user}"
        return "edit-user";
    }

}

