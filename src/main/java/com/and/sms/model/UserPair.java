package com.and.sms.model;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;

public class UserPair {
    private User user1;
    private User user2;

    public UserPair(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Set<String> getCommonIterest() {
        return Sets.intersection(user1.getInterests(), user2.getInterests());
    }

    public int getCommonIterestCount() {
        return getCommonIterest().size();
    }

    @Override
    public String toString() {
        String userPairTemplate = "UserPair{%s-%s,%s}";
        return String.format(userPairTemplate, user1.getName(), user2.getName(), getCommonIterest());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPair userPair = (UserPair) o;
        return Objects.equals(user1, userPair.user1) &&
                Objects.equals(user2, userPair.user2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2);
    }
}
