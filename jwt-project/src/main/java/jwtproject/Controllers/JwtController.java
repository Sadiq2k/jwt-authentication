package jwtproject.Controllers;


import jwtproject.Entity.JwtRequest;
import jwtproject.Entity.JwtResponse;
import jwtproject.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
//        System.out.println(jwtRequest.getUserName() +" "+jwtRequest.getUserPassword());
        return jwtService.createJwtToken(jwtRequest);
    }

}