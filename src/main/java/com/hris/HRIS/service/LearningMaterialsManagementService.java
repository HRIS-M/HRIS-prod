package com.hris.HRIS.service;

import com.hris.HRIS.model.CourseLearningMaterialModal;
import com.hris.HRIS.repository.CourseLearningMaterialRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.mongodb.MongoDatabaseFactory;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;

import static com.mongodb.client.model.Filters.eq;

@Service
public class LearningMaterialsManagementService {

    @Autowired
    private MongoDatabaseFactory mongoDbFactory;

    @Autowired
    CourseLearningMaterialRepository courseLearningMaterialRepository;
    public void saveLearningMaterial(String moduleId, String assignmentId, String status, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        InputStream inputStream = file.getInputStream();

        GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDbFactory.getMongoDatabase());
        ObjectId contentId = gridFSBucket.uploadFromStream(fileName, inputStream);

        CourseLearningMaterialModal newCourseLearningMaterialModal = new CourseLearningMaterialModal();
        newCourseLearningMaterialModal.setLearningMaterialTitle(fileName);
        newCourseLearningMaterialModal.setContentId(contentId.toString());
        newCourseLearningMaterialModal.setContentType(contentType);
        newCourseLearningMaterialModal.setModuleId(moduleId);
        newCourseLearningMaterialModal.setAssignmentId(assignmentId);
        newCourseLearningMaterialModal.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        newCourseLearningMaterialModal.setStatus(status);

        courseLearningMaterialRepository.save(newCourseLearningMaterialModal);

    }

    public ResponseEntity<byte[]> getLearningMaterial(CourseLearningMaterialModal courseLearningMaterialModal) throws IOException {

        GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDbFactory.getMongoDatabase());
        GridFSFile gridFSFile = gridFSBucket.find(eq("_id", new ObjectId(courseLearningMaterialModal.getContentId()))).first();

        if(gridFSFile != null){
            try (InputStream inputStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId())){
                byte[] fileBytes = inputStream.readAllBytes();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("filename", courseLearningMaterialModal.getLearningMaterialTitle());
                headers.setContentLength(fileBytes.length);

                return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteLearningMaterial(String contentId){
        GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDbFactory.getMongoDatabase());
        gridFSBucket.delete(new ObjectId(contentId));
    }
}
