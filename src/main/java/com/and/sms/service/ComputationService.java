package com.and.sms.service;

import com.and.sms.dao.UserRepository;
import com.and.sms.model.User;
import com.and.sms.model.UserPair;
import com.and.sms.service.algorithm.PairBuilderTask;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import static com.and.sms.utils.UserPairUtils.buildUserPairsWithCommonInterests;


@Service
public class ComputationService {
    @Autowired
    private UserRepository userRepository;

    public List<UserPair> findUserPairListWithMaxLength(Set<String> userNames) {
        Iterable<User> users = userRepository.findAllById(userNames);
        List<UserPair> allUserPairs = buildUserPairsWithCommonInterests(Lists.newArrayList(users));
        return getUserPairListWithMaxLength(allUserPairs);
    }

    private List<UserPair> getUserPairListWithMaxLength(List<UserPair> allUserPairs) {
        List<List<UserPair>> finalUserPairListCollection = getUserPairListCollection(allUserPairs);

        int maxCountOfUserPairs = finalUserPairListCollection.stream()
                .map(List::size).max(Integer::compareTo).orElse(0);
        if (maxCountOfUserPairs > 0) {
            List<List<UserPair>> userPairsWithMaxLength = finalUserPairListCollection.stream()
                    .filter(userPairList -> userPairList.size() == maxCountOfUserPairs)
                    .collect(Collectors.toList());
            return selectListByStrengthOfInterestConnection(userPairsWithMaxLength);
        } else {
            return new ArrayList<>();
        }
    }

    private List<UserPair> selectListByStrengthOfInterestConnection(List<List<UserPair>> userPairListCollection) {
        Map.Entry<Integer, List<UserPair>> bestEntry = userPairListCollection.stream()
            .map(userPairList -> {
                List<Integer> sortedCountOfCommonInterests = userPairList.stream()
                    .map(UserPair::getCommonIterestCount)
                    .sorted(Integer::compareTo)
                    .collect(Collectors.toList());
                StringBuilder number = new StringBuilder();
                Collections.reverse(sortedCountOfCommonInterests);
                sortedCountOfCommonInterests.forEach(number::append);
                return new HashMap.SimpleEntry<>(Integer.valueOf(number.toString()), userPairList);
            })
            .max(Comparator.comparingInt(AbstractMap.SimpleEntry::getKey)).get();
        return bestEntry.getValue();
    }

    private List<List<UserPair>> getUserPairListCollection(List<UserPair> userPairs) {
        ForkJoinPool pool = new ForkJoinPool();
        PairBuilderTask task = new PairBuilderTask(userPairs);
        return pool.invoke(task);
    }
}
