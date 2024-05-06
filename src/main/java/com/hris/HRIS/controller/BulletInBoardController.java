package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.BulletInBoardModel;
import com.hris.HRIS.repository.BulletInBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bullet-in-board")
public class BulletInBoardController {
    @Autowired
    BulletInBoardRepository bulletInBoardRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveBulletInBoard(@RequestParam("organizationId") String organizationId,
                                                         @RequestParam("departmentId") String departmentId,
                                                         @RequestPart(value = "titleImage", required = false) MultipartFile titleImage,
                                                         @RequestParam("title") String title,
                                                         @RequestParam("message") String message,
                                                         @RequestParam("redirectUrl") String redirectUrl,
                                                         @RequestParam("action") String action,
                                                         @RequestPart(value = "backgroundImage", required = false) MultipartFile backgroundImage,
                                                         @RequestParam(value = "stringBg", required = false) String stringBg,
                                                         @RequestParam(value = "fontColor", required = false) String fontColor,
                                                         @RequestParam("timestamp") String timestamp
    ) throws IOException {

        BulletInBoardModel newBulletinBoardModel = new BulletInBoardModel();
        newBulletinBoardModel.setOrganizationId(organizationId);
        newBulletinBoardModel.setDepartmentId(departmentId);
        if (titleImage != null) {
            newBulletinBoardModel.setTitleImage(titleImage.getBytes());
        }
        newBulletinBoardModel.setTitle(title);
        newBulletinBoardModel.setMessages(message);
        newBulletinBoardModel.setRedirectUrl(redirectUrl);
        newBulletinBoardModel.setAction(action);
        if (backgroundImage != null) {
            newBulletinBoardModel.setBackgroundImage(backgroundImage.getBytes());
        }

        if (stringBg != null) {
            newBulletinBoardModel.setStringBg(stringBg);
        }
        newBulletinBoardModel.setFontColor(fontColor);
        newBulletinBoardModel.setTimestamp(timestamp);

        bulletInBoardRepository.save(newBulletinBoardModel);

        ApiResponse apiResponse = new ApiResponse("Bullet in board saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<BulletInBoardModel> getAllBulletInBoard(){
        return bulletInBoardRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BulletInBoardModel> getBulletInBoardById(@PathVariable String id){
        Optional<BulletInBoardModel> bulletInBoardModelOptional = bulletInBoardRepository.findById(id);

        return bulletInBoardModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBulletInBoard(@PathVariable String id){
        bulletInBoardRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Bullet in board removed successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
