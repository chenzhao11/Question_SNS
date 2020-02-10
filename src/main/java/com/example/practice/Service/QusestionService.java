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
   public void addquestion(Question question){
        questionDao.insertQuestion(question);

    }

    public  List<Question> latestquestion(int userid,int offset,int limit){
       return questionDao.selectLatestQuestions(userid, offset, limit);
    }


}
