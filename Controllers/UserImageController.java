package jwtproject.Controllers;

import jwtproject.Dao.UserDao;
import jwtproject.Entity.User;
import jwtproject.Entity.UserImage;
import jwtproject.Service.UserImageService;
import jwtproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@RestController
public class UserImageController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/addProfilePic")
    public ResponseEntity<User> addProfilePic(@RequestParam("imageFile") MultipartFile[] files, Principal principal) {
        try {
            Set<UserImage> images = uploadImage(files);

            User currentUser = userDao.findById(principal.getName()).orElse(null);
            if (currentUser != null) {
                currentUser.setUserImages(images);
                return ResponseEntity.ok(userService.updateUser(currentUser));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Set<UserImage> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<UserImage> imageModels = new HashSet<>();
        for (MultipartFile file : multipartFiles) {
            UserImage imageModel = new UserImage(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModels.add(imageModel);
        }
        return imageModels;
    }

//    @PostMapping(value = "/addProfilePic")
//    public User addProfilePic(@RequestParam("imageFile") MultipartFile[] files, Principal principal) {
//        try {
//            Set<UserImage> images = uploadImage(files);
//
//            User currentUser = userDao.findById(principal.getName()).orElse(null);
//            currentUser.setUserImages(images);
//            return userService.updateUser(currentUser);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public Set<UserImage> uploadImage(MultipartFile[] multipartFiles) throws IOException {
//        Set<UserImage> imageModels = new HashSet<>();
//        for (MultipartFile file : multipartFiles) {
//            UserImage imageModel = new UserImage(
//                    file.getOriginalFilename(),
//                    file.getContentType(),
//                    file.getBytes()
//            );
//            imageModels.add(imageModel);
//        }
//        return imageModels;
//    }

    @GetMapping("/getImage")
    public UserImage getImage(@PathVariable String  userName){
    User user = userService.getUser(userName).get();
        final Set<UserImage> userImages = user.getUserImages();
    return (UserImage) userImages;
    }

}
