package jwtproject.Controllers;


import jwtproject.Dao.UserDao;
import jwtproject.Entity.User;
import jwtproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;


    @PostConstruct
    public void  initRolesAndUsers(){
        userService.initRoleAndUser();
    }
    @PostMapping({"/registerNewUser"})
    public ResponseEntity<String> registerNewUser(@RequestBody User user){
        return userService.registerNewUser(user);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to admin";
    }
    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('user')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }

    @GetMapping({"/getUsers"})
    public List<User> getAllUser(){
        return  userService.getAllUser();
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable String id){
        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
    }

    @GetMapping({"/get-user /{id}"})
    public Optional<User> getUser(@RequestParam String id){
        return userService.getUser(id);
    }

    @PutMapping({"/save-user"})
    public void  saveUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @GetMapping({"/loginUser"})
    public User getLoginUser(Principal principal){
        User user = userService.getUserByUserName(principal.getName()).get();
        return user;
    }

}
