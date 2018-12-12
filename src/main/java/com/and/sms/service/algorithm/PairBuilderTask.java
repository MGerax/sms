package com.and.sms.service.algorithm;

import com.and.sms.model.User;
import com.and.sms.model.UserPair;
import com.and.sms.utils.UserPairUtils;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public class PairBuilderTask extends RecursiveTask<List<List<UserPair>>> {
    private List<UserPair> userPairs;

    public PairBuilderTask(List<UserPair> userPairs) {
        this.userPairs = userPairs;
    }

    @Override
    protected List<List<UserPair>> compute() {
        List<List<UserPair>> finalUserPairListCollection = new ArrayList<>();
        Map<UserPair, PairBuilderTask> pairToTaskMap = new HashMap<>();
        System.out.println(Thread.currentThread().getName());
        for (int i = 0; i < userPairs.size(); i++) {
            UserPair baseUserPair = userPairs.get(i);
            List<UserPair> newUserPairList = UserPairUtils.removeUserPairsWithUserDuplication(userPairs,
                    buildUserSet(baseUserPair.getUser1(), baseUserPair.getUser2()));
            if (newUserPairList.isEmpty()) {
                finalUserPairListCollection.add(Lists.newArrayList(baseUserPair));
            } else {
                PairBuilderTask pairBuilderTask = new PairBuilderTask(newUserPairList);
                pairBuilderTask.fork();
                pairToTaskMap.put(baseUserPair, pairBuilderTask);
            }
        }
        pairToTaskMap.entrySet().forEach(pairTaskEntry -> {
            PairBuilderTask task = pairTaskEntry.getValue();
            List<List<UserPair>> pairsCollection = task.join();
            pairsCollection.forEach(list -> {
                list.add(pairTaskEntry.getKey());
                finalUserPairListCollection.add(list);
            });
        });
        return finalUserPairListCollection;
    }

    private Set<User> buildUserSet(User... users) {
        Set<User> userSet = new HashSet<>();
        Stream.of(users).forEach(user -> userSet.add(user));
        return userSet;
    }
}
