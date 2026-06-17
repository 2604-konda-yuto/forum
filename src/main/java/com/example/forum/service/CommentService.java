package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    /*
     * 返信内容表示処理
     */
    public CommentForm commentReport(Integer messageId) {
        List<Comment> comments = commentRepository.findAllByOrderByIdAsc();
        return commentReport(messageId);
    }

    /*
     * 返信処理
     */
    public CommentForm replyReport(Integer messageId) {
        List<Comment> results = new ArrayList<>();
        results.add(commentRepository.findById(messageId));
        List<CommentForm> reports = setCommentEntity();
        return commentReport(messageId);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqReport) {
        Comment comment = new Comment();
        comment.setId(reqReport.getId());
        comment.setContent(reqReport.getContent());
        comment.setMessage_id(reqReport.getMessage_id());
        return comment;
    }

}