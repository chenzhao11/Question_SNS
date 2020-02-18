package com.example.practice.Service;

import com.example.practice.Dao.CommentDao;
import com.example.practice.Model.Comment;
import com.example.practice.Model.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

import static com.example.practice.Model.EntityType.ENTITY_QUESTION;

@Service
@Component
public class CommentService {

    @Autowired
    CommentDao commentDao;
    @Autowired
    SensitiveService sensitiveService;
    public int insertComment(Comment comment){
       comment.setContent( HtmlUtils.htmlEscape(comment.getContent()));
        String content=sensitiveService.filter(comment.getContent());
        comment.setContent(content);
        return commentDao.insertComment(comment);
    }
    public List<Comment> getCommentList(int entityType,int questionId){
        return commentDao.selectComment(entityType,questionId);
    }


}
