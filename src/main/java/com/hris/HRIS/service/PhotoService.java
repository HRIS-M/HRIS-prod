package com.hris.HRIS.service;

import com.hris.HRIS.model.MultimediaModel;
import com.hris.HRIS.repository.MultimediaRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoService {
    @Autowired
    private MultimediaRepository photoRepo;

    public String addMultimedia(String title, MultipartFile file, String contentType) throws IOException {
        MultimediaModel multimedia = new MultimediaModel();
        multimedia.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        multimedia.setTitle(title);
        multimedia.setContentType(contentType);
        multimedia = photoRepo.insert(multimedia);
        return multimedia.getId();
    }

    public MultimediaModel getMultimedia(String id) {
        return photoRepo.findById(id).orElse(null);
    }

    public byte[] getMultimediaContent(String id) {
        MultimediaModel multimedia = photoRepo.findById(id).orElse(null);
        return (multimedia != null) ? multimedia.getFile().getData() : null;
    }
}
