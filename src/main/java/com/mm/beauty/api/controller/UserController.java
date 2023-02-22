package com.mm.beauty.api.controller;

import com.mm.beauty.api.dto.UserDTO;
import com.mm.beauty.api.entity.User;
import com.mm.beauty.api.facade.UserFacade;
import com.mm.beauty.api.service.UserService;
import com.mm.beauty.api.validations.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/user")
@PreAuthorize("permitAll()")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private ResponseErrorValidator responseErrorValidator;


    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.UserToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getUserList() {
        List<UserDTO> userDTOList = userService.getUserList().stream()
                .map(userFacade::UserToUserDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId) {
        User user = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = userFacade.UserToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }



    @RequestMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult result, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        User user = userService.updateUser(userDTO, principal);
        UserDTO updateUser = userFacade.UserToUserDTO(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @PostMapping("/ver")
    public ResponseEntity<Object> getVer() {
        return ResponseEntity.ok().body("hsfhs");
    }
}
