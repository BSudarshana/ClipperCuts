package lk.earth.earthuniversity.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Question {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "qutext")
    private String qutext;
    @Basic
    @Column(name = "isactive")
    private Byte isactive;
    @OneToMany(mappedBy = "question")
    private Collection<Response> responses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQutext() {
        return qutext;
    }

    public void setQutext(String qutext) {
        this.qutext = qutext;
    }

    public Byte getIsactive() {
        return isactive;
    }

    public void setIsactive(Byte isactive) {
        this.isactive = isactive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) && Objects.equals(qutext, question.qutext) && Objects.equals(isactive, question.isactive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, qutext, isactive);
    }

    public Collection<Response> getResponses() {
        return responses;
    }

    public void setResponses(Collection<Response> responses) {
        this.responses = responses;
    }
}
