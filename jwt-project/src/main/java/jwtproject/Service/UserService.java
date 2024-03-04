package jwtproject.Service;

import jwtproject.Dao.RoleDao;
import jwtproject.Dao.UserDao;
import jwtproject.Entity.Role;
import jwtproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin123"));
        adminUser.setFirstName("admin");
        adminUser.setLastName("admin");
        adminUser.setEmail("admin@gmail.com");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

    }

    public ResponseEntity<String> registerNewUser(User user) {
        if (userDao.existsByUserName(user.getUserName())) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Name Invalid .Could you provide valid User Name");
        }
        if(userDao.existsByEmail(user.getEmail())){
            System.out.println(user.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid Email. Could you provide valid Email");

        }
        System.out.println(user.getEmail());
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        userDao.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public List<User> getAllUser(){
        return  userDao.findAll();
    }

    public void deleteUser(String id){
        Optional<User> optionalUser = userDao.findById(id);

        optionalUser.ifPresent(user -> {
            user.getRoles().clear();
            userDao.deleteById(id);
        });

    }


    public Optional<User> getUser(String id){
        return userDao.findById(id);
    }

    public void  saveUser(User user){
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);

        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        userDao.save(user);
    }

    public Optional<User> getUserByUserName(String name) {
    return userDao.findUserByUserName(name);
    }

    public void saveUserImage(String url) {

    }

    public void saveUserImages(User user) {
        userDao.save(user);
    }

    public User updateUser(User currentUser) {
      return  userDao.save(currentUser);
    }

}
