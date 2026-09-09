package com.ashna.focus_tracker.service;

import com.ashna.focus_tracker.entity.UserStats;
import com.ashna.focus_tracker.repository.UserStatsRepository;
import org.springframework.stereotype.Service;

@Service
public class UserStatsService {

    private final UserStatsRepository userStatsRepository;

    public UserStatsService(UserStatsRepository userStatsRepository) {
        this.userStatsRepository = userStatsRepository;
    }

    public UserStats getOrCreate() {
        return userStatsRepository.findById(1L).orElseGet(() -> {
            UserStats stats = new UserStats();
            stats.setId(1L);
            return userStatsRepository.save(stats);
        });
    }

    public void addCoins(int amount) {
        UserStats stats = getOrCreate();
        stats.setCoinBalance(stats.getCoinBalance() + amount);
        userStatsRepository.save(stats);
    }

    public void recordCompletion(java.time.LocalDate today, boolean completedYesterday) {
        UserStats stats = getOrCreate();
        if (completedYesterday) {
            stats.setCurrentStreak(stats.getCurrentStreak() + 1);
        } else {
            stats.setCurrentStreak(1);
        }
        if (stats.getCurrentStreak() > stats.getLongestStreak()) {
            stats.setLongestStreak(stats.getCurrentStreak());
        }
        userStatsRepository.save(stats);
    }
}