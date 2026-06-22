package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.entity.Report;
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
            comment.setMessageId(result.getMessageId());
            comments.add(comment);
        }
        return comments;
    }

    /*
     * 返信処理
     */
    public void saveComment(CommentForm report) {

        //Repositoryに探しに行った結果を詰める
        Comment results = setCommentEntity(report);

        Comment saveResult = commentRepository.save(results);

        //他必要な情報をセットする
        CommentForm form = new CommentForm();
        form.setId(saveResult.getId());
        form.setContent(saveResult.getContent());
        form.setMessageId(saveResult.getMessageId());

    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqReport) {
        Comment comment = new Comment();
        comment.setId(reqReport.getId());
        comment.setContent(reqReport.getContent());
        comment.setMessageId(reqReport.getMessageId());
        return comment;
    }

    /*
     * レコード1件取得
     */
    public CommentForm editCommentReport(Integer id) {
        List<Comment> results = new ArrayList<>();
        results.add(commentRepository.findById(id).orElse(null));
        List<CommentForm> comments = setCommentForm(results);
        return comments.get(0);
    }

    /*
     * レコード追加
     */
    public void saveReport(CommentForm reqReport) {
        Comment saveReport = setCommentEntity(reqReport);
        commentRepository.save(saveReport);
    }

    /*
     * レコード削除
     */
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

}