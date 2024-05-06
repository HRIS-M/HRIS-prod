package com.hris.HRIS.service;

import com.hris.HRIS.model.MultimediaModel;
import com.hris.HRIS.repository.MultimediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MultimediaService {
    @Autowired
    MultimediaRepository multimediaRepository;

    public void toggleLike(String multimediaId, String likeId) {
        MultimediaModel multimedia = multimediaRepository.findById(multimediaId)
                .orElseThrow(() -> new RuntimeException("Multimedia not found"));

        List<String> likes = multimedia.getLikes();

        if (likes == null) {
            likes = new ArrayList<>();
            multimedia.setLikes(likes);
        }

        if (likes.contains(likeId)) {
            // Like ID already in likes array, remove it
            likes.remove(likeId);
        } else {
            // Like ID not in likes array, add it
            likes.add(likeId);
        }

        multimediaRepository.save(multimedia);
    }

    public void saveComment(String multimediaId, String commentId) {
        MultimediaModel multimedia = multimediaRepository.findById(multimediaId)
                .orElseThrow(() -> new RuntimeException("Multimedia not found"));

        List<String> comments = multimedia.getComments();

        if (comments == null) {
            comments = new ArrayList<>();
            multimedia.setComments(comments);
        }

        comments.add(commentId);

        multimediaRepository.save(multimedia);
    }

    public void deleteComment(String multimediaId, String commentId) {
        MultimediaModel multimedia = multimediaRepository.findById(multimediaId)
                .orElseThrow(() -> new RuntimeException("Multimedia not found"));

        List<String> comments = multimedia.getComments();

        comments.remove(commentId);
    }

    public void saveShare(String multimediaId, String shareId) {
        MultimediaModel multimedia = multimediaRepository.findById(multimediaId)
                .orElseThrow(() -> new RuntimeException("Multimedia not found"));

        List<String> shares = multimedia.getShares();

        if (shares == null){
            shares = new ArrayList<>();
            multimedia.setShares(shares);
        }

        shares.add(shareId);

        multimediaRepository.save(multimedia);
    }

    public void deleteShare(String multimediaId, String shareId) {
        MultimediaModel multimedia = multimediaRepository.findById(multimediaId)
                .orElseThrow(() -> new RuntimeException("Multimedia not found"));

        List<String> shares = multimedia.getShares();

        shares.remove(shareId);
    }

}
