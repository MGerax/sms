package com.and.sms.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {
    @Id
    private String name;
    @Column
    @Convert(converter = InterestConverter.class)
    private Set<String> interests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getInterests() {
        return interests;
    }

    public void setInterests(Set<String> interests) {
        this.interests = interests;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", interests=" + interests +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(interests, user.interests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, interests);
    }
}
