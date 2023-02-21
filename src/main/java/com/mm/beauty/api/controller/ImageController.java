package com.mm.beauty.api.controller;

import com.mm.beauty.api.entity.ImageModel;
import com.mm.beauty.api.payload.response.MessageResponse;
import com.mm.beauty.api.service.ImageModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {
    @Autowired
    private ImageModelService imageModelService;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file")MultipartFile file,
                                                             Principal principal) throws IOException {
        imageModelService.uploagImageToUser(file, principal);
        return ResponseEntity.ok(new MessageResponse("Image to user uploaded successfully"));
    }

    @PostMapping("/{courseId}/upload")
    public ResponseEntity<MessageResponse> uploadImageToCourse(@PathVariable("courseId") String courseId,
                                                               @RequestParam("file") MultipartFile file,
                                                               Principal principal) throws IOException {
        imageModelService.uploadImageToCourse(file, principal, Long.parseLong(courseId));
        return ResponseEntity.ok(new MessageResponse("Image to course uploaded successfully"));
    }

    @GetMapping("/profileImage")
    public ResponseEntity<ImageModel> getImageFoUser(Principal principal) {
        ImageModel userImage = imageModelService.getImageToUser(principal);
        return new ResponseEntity<>(userImage, HttpStatus.OK);
    }

    @GetMapping("/{courseId}/image")
    public ResponseEntity<ImageModel> getImageToCourse(@PathVariable("courseId") String courseId) {
        ImageModel courseImage = imageModelService.getImageToCourse(Long.parseLong(courseId));
        return new ResponseEntity<>(courseImage, HttpStatus.OK);
    }



}
