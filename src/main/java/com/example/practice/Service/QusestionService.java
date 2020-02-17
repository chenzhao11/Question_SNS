package com.example.practice.Service;

import com.example.practice.Dao.QuestionDao;
import com.example.practice.Model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class QusestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    SensitiveService sensitiveService ;
   public int addquestion(Question question){
        /*敏感词过滤*/

       question.setContent(sensitiveService.filter(question.getContent()));
       question.setTitle(sensitiveService.filter(question.getTitle()));

        return questionDao.insertQuestion(question);

    }

    public  List<Question> latestquestion(int userid,int offset,int limit){
       return questionDao.selectLatestQuestions(userid, offset, limit);
    }


}
