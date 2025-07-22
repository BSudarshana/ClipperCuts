package com.clippercuts.controller;

import com.clippercuts.dao.QuestionDao;
import com.clippercuts.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    @Autowired
    private QuestionDao questionDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Question> get(@RequestParam HashMap<String, String> params) {

        List<Question> questions = this.questionDao.findAll();

        if(params.isEmpty())  return questions;

        String quesionText = params.get("qutext");

        Stream<Question> questionStream = questions.stream();

        if(quesionText!=null) questionStream = questionStream.filter(q -> q.getQutext().contains(quesionText));

        return questionStream.collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Question question){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(questionDao.findQuestionByText(question.getQutext())!=null)
            errors = errors+"<br> Existing Question ";

        if(errors=="")
            questionDao.save(question);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(question.getId()));
        responce.put("url","/questions/"+question.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Question question){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Question question1 = questionDao.findQuestionByText(question.getQutext());

        if(question1!=null && question.getId()!=question1.getId())
            errors = errors+"<br> Existing Question";

        if(errors=="") questionDao.save(question);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id ",String.valueOf(question.getId()));
        responce.put("url ","/questions/"+question.getId());
        responce.put("error ",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Question question = questionDao.findQuestionById(id);

        if(question==null)
            errors = errors+"<br> The Payment Receipt  Does Not Existed";

        if(errors=="") questionDao.delete(question);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/questions/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




