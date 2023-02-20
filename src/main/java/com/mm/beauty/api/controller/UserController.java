package com.mm.beauty.api.controller;

import com.mm.beauty.api.dto.UserDTO;
import com.mm.beauty.api.facade.UserFacade;
import com.mm.beauty.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getUserList() {
        List<UserDTO> userDTOList = userService.getUserList().stream()
                .map(userFacade::UserToUserDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @PostMapping("/ver")
    public ResponseEntity<Object> getVer() {
        return ResponseEntity.ok().body("hsfhs");
    }
}
