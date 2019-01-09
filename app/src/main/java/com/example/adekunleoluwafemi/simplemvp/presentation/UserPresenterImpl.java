package com.example.adekunleoluwafemi.simplemvp.presentation;

import com.example.adekunleoluwafemi.simplemvp.model.User;
import com.example.adekunleoluwafemi.simplemvp.repository.UserRepository;
import com.example.adekunleoluwafemi.simplemvp.view.UserView;

public class UserPresenterImpl implements UserPresenter {
    private UserView view;
    private UserRepository userRepository;
    private User u;

    public UserPresenterImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void loadUserDetails() {
        int userId = view.getUserId();
        u = userRepository.getUser(userId);
        if(u == null) {
            view.showUserNotFoundMessage();
        } else {
            view.displayFirstName(u.getFirstName());
            view.displayLastName(u.getLastName());
        }
    }

    @Override
    public void setView(UserView view) {
        this.view = view;
        loadUserDetails();
    }

    @Override
    public void saveUser() {
        if(u != null) {
            if(view.getFirstName().trim().equals("") || view.getLastName().trim().equals("")) {
                view.showUserNameIsRequired();
            } else {
                u.setFirstName(view.getFirstName());
                u.setLastName(view.getLastName());
                userRepository.save(u);
                view.showUserSavedMessage();
            }

        }
    }
}
