package br.com.pratica.spring.praticaspring.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
public class Student extends AbstractEntity {

    @NotEmpty(message = "Nome não deve estar vazio")
    private String name;

    @NotEmpty(message = "E-mail não deve estar vazio")
    @Email(message = "E-mail tem que estar no formato")
    private String email;

    public Student() {
    }

    public Student (@NotEmpty String name, @NotEmpty @Email String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString () {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
