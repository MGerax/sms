package com.and.sms.utils;

import com.and.sms.model.User;
import com.and.sms.model.UserPair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserPairUtils {

    public static List<UserPair> removeUserPairsWithUserDuplication(List<UserPair> userPairs, Set<User> users) {
        List<UserPair> cleanUserPairs = new ArrayList<>(userPairs);
        for (int i = 0; i < userPairs.size(); i++) {
            UserPair userPair = userPairs.get(i);
            if (users.contains(userPair.getUser1()) || users.contains(userPair.getUser2())) {
                cleanUserPairs.remove(userPair);
            }
        }
        return cleanUserPairs;
    }

    public static List<UserPair> buildUserPairsWithCommonInterests(List<User> users) {
        List<UserPair> pairs = new ArrayList<>();
        for (int i = 0; i < users.size() - 1; i++) {
            pairs.addAll(buildUserPairsWithCommonInterests(users.get(i), users.subList(i + 1, users.size())));
        }
        return pairs;
    }

    private static Collection<UserPair> buildUserPairsWithCommonInterests(User user, List<User> users) {
        List<UserPair> pairs = new ArrayList<>();
        users.forEach(user2 -> {
            UserPair userPair = new UserPair(user, user2);
            if (userPair.getCommonIterestCount() > 0) {
                pairs.add(userPair);
            }
        });
        return pairs;
    }
}
