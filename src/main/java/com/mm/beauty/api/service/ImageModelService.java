package com.mm.beauty.api.service;

import com.mm.beauty.api.entity.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

public interface ImageModelService {

    ImageModel uploagImageToUser(MultipartFile file, Principal principal) throws IOException;

    ImageModel uploadImageToCourse(MultipartFile file, Principal principal, Long id) throws IOException;

    ImageModel getImageToUser(Principal principal);

    ImageModel getImageToCourse(Long id);


}
