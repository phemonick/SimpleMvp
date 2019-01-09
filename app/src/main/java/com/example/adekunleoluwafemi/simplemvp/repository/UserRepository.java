package com.example.adekunleoluwafemi.simplemvp.repository;

import com.example.adekunleoluwafemi.simplemvp.model.User;

public interface UserRepository {
    User getUser(int id);
    void save(User u);
}
