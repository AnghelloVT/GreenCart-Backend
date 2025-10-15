
package com.GreenCart.GreenCart.web.controller;

import com.GreenCart.GreenCart.domain.User;
import com.GreenCart.GreenCart.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAll() {
        return userService.getAll();
    }
}
