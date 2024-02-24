package com.in28minutes.springboot.fullrestapi.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;



@RestController
public class UserResource {
    private static final String USERS = "/users";
    private static final String USER_ID = USERS + "/{id}";
    //    @Autowired
    private UserDaoService service;

    public UserResource(UserDaoService service) {
        this.service = service;
    }


    @GetMapping(USERS)
    public List<User> getUsers() {
        return service.findAll();
    }
    @GetMapping(USER_ID)
    public EntityModel<User> getUser(@PathVariable("id") int userId){
        User user = service.getUser(userId);
        if(user == null){
            throw new UserNotFoundException("id: " + userId);
        }
        EntityModel entityModel = EntityModel.of(user);
        WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).getUsers());
        entityModel.add(webMvcLinkBuilder.withRel("all-users"));
         return entityModel;
    }
    @PostMapping(USERS)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
        //return the location of the created resource as a part of response header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping(USER_ID)
    public void deleteUser(@PathVariable("id") int userId){
        service.deleteUser(userId);
    }
}
