package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
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
     * 返信全件取得処理
     */
    public List<CommentForm> commentAllReport() {
        List<Comment> results = commentRepository.findAllByOrderByIdAsc();
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
        List<CommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            CommentForm comment = new CommentForm();
            Comment result = results.get(i);
            comment.setId(result.getId());
            comment.setContent(result.getContent());
            comments.add(comment);
        }
        return comments;
    }

    /*
     * 返信処理
     */
    public CommentForm replyReport(Integer messageId) {
        Comment reply = setCommentEntity(replyReport(messageId));
        //findById キーに該当するレコードを取得
        commentRepository.findById(messageId);
        return replyReport(messageId);
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