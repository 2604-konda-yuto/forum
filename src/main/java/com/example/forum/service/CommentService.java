package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            comment.setMessageId(result.getMessage_id());
            comments.add(comment);
        }
        return comments;
    }

    /*
     * 返信処理
     */
    public CommentForm replyReport(Integer messageId, Integer id, String content) {

        //Repositoryに探しに行った結果を詰める
        Comment results = commentRepository.findById();

        //上記をComment replyに詰め替え
        Comment reply = results;

        //l.27から取り出す
        CommentForm form = setCommentForm(results);

        //他必要な情報をセットする
        form.setId(id);
        form.setContent(content);
        form.setMessageId(messageId);

        return form;

    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
//    private Comment setCommentEntity(CommentForm reqReport) {
//        Comment comment = new Comment();
//        comment.setId(reqReport.getId());
//        comment.setContent(reqReport.getContent());
//        comment.setMessage_id(reqReport.getMessageId());
//        return comment;
//    }

}