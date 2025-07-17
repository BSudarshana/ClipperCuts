package com.clippercuts.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Response {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "rating")
    private Integer rating;
    @ManyToOne
    @JoinColumn(name = "customerfeedback_id", referencedColumnName = "id", nullable = false)
    private Customerfeedback customerfeedback;
    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
    private Question question;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(id, response.id) && Objects.equals(rating, response.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating);
    }

    public Customerfeedback getCustomerfeedback() {
        return customerfeedback;
    }

    public void setCustomerfeedback(Customerfeedback customerfeedback) {
        this.customerfeedback = customerfeedback;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
