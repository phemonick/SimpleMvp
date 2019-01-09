package com.example.adekunleoluwafemi.simplemvp.presentation;

import com.example.adekunleoluwafemi.simplemvp.view.UserView;

public interface UserPresenter {
    void loadUserDetails();
    void setView(UserView view);
    void saveUser();
}
