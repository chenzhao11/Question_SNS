package com.example.practice.Service;

import com.example.practice.Model.Question;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Component
public class SearchService {
    @Autowired
    QusestionService qusestionService;
    private static final Logger logger= LoggerFactory.getLogger(SearchService.class);
    private static final  String baseUrl = "http://127.0.0.1:8983/solr/#/practice";
    private String HIGHLITE_FILED = "question_title,question_content,comment_content";
    private static final SolrClient solrClient = new HttpSolrClient.Builder(baseUrl).build();
    public List<Question>  search(String key,int offset,int count,String hlpre,String hlpost){
        List<Question> questionList=new ArrayList<>();
        SolrQuery query=new SolrQuery(key);
        query.addHighlightField(HIGHLITE_FILED);
        //这三个关于高亮的要一起写
        query.setHighlight(true);
        query.setHighlightSimplePre(hlpre);
        query.setHighlightSimplePost(hlpost);
        query.setRows(count);
        query.setStart(offset);
        try {
            QueryResponse response=solrClient.query(query);//返回一个map
            //把response里面的数据转化成question来看
            //取highlight过后的，是一个set    key是id（默认都会导入id？）  hi.filed是一个map，比较特别的是虽然只有一个字段，map对应的key还是存放在一个list里面
            for (Map.Entry<String,Map<String,List<String>>> entry :response.getHighlighting().entrySet()
                 ) {
                Question question=new Question();
                int id=Integer.parseInt(entry.getKey());
                question=qusestionService.getQuestionById(id);
                if(entry.getValue().containsKey("question_content")){
                    if(entry.getValue().get("question_content").size()>0){
                    question.setContent(entry.getValue().get("question_content").get(0));}
                }
                if(entry.getValue().containsKey("question_title")){
                    if(entry.getValue().get("question_title").size()>0){
                    question.setTitle(entry.getValue().get("question_title").get(0));}
                }
                //comment返回的id是对应的questionid 这里没有设置评论的详情页所以不好判断
                if(entry.getValue().containsKey("comment_content")){

                }
                questionList.add(question);
            }
            return questionList;
        }catch (Exception e){
            logger.error("请求查询solr服务器失败！\r\n"+e.getMessage());
        }
        return null;
    }


}
