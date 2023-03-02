package com.mm.beauty.api.service.impl;

import com.mm.beauty.api.entity.Courses;
import com.mm.beauty.api.entity.ImageModel;
import com.mm.beauty.api.entity.User;
import com.mm.beauty.api.exceptions.ImageNotFoundException;
import com.mm.beauty.api.repository.ImageModelRepository;
import com.mm.beauty.api.repository.CoursesRepository;
import com.mm.beauty.api.repository.UserRepository;
import com.mm.beauty.api.service.ImageModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageModelServiceImpl implements ImageModelService {
    public static final Logger LOG = LoggerFactory.getLogger(ImageModelServiceImpl.class);


    private final CoursesRepository coursesRepository;
    private final ImageModelRepository imageModelRepository;
    private final UserRepository userRepository;

    @Autowired
    public ImageModelServiceImpl(CoursesRepository coursesRepository, ImageModelRepository imageModelRepository, UserRepository userRepository) {
        this.coursesRepository = coursesRepository;
        this.imageModelRepository = imageModelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ImageModel uploagImageToUser(MultipartFile file, Principal principal) throws IOException {
        User user = getUserByPrincipal(principal);
        LOG.info("Upload image profile to user {}", user.getUsername());
        ImageModel userProfileImage = imageModelRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(userProfileImage)) {
            imageModelRepository.delete(userProfileImage);
        }
        ImageModel imageModel = new ImageModel();
        imageModel.setUserId(user.getId());
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        imageModel.setName(file.getOriginalFilename());
        return imageModelRepository.save(imageModel);
    }

    @Override
    public ImageModel uploadImageToCourse(MultipartFile file, Principal principal, Long id) throws IOException {
        Courses courses = coursesRepository.findCoursesById(id);
        ImageModel imageModel = new ImageModel();
        imageModel.setCourseId(courses.getId());
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        imageModel.setName(file.getOriginalFilename());
        return imageModelRepository.save(imageModel);
    }

    @Override
    public ImageModel getImageToUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        ImageModel imageModel = imageModelRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressByte(imageModel.getImageBytes()));
        }
        return imageModel;
    }

    @Override
    public ImageModel getImageToCourse(Long id) {
        ImageModel imageModel = imageModelRepository.findByCourseId(id)
                .orElseThrow(() -> new ImageNotFoundException("Cannot find image to course: " + id));

        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressByte(imageModel.getImageBytes()));
        }
        return imageModel;

    }

    private <T> Collector<T, ?, T> toSingleCourseCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }


    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            LOG.error("Cannot compress bytes");
        }
        System.out.println("Compressed image byte size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private static byte[] decompressByte(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            LOG.error("Cannot decompress bytes");
        }
        return outputStream.toByteArray();
    }

    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User" + username + " not found"));
    }


}
