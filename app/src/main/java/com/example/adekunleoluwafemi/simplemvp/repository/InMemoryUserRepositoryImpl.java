package com.example.adekunleoluwafemi.simplemvp.repository;

import com.example.adekunleoluwafemi.simplemvp.model.User;

public class InMemoryUserRepositoryImpl implements UserRepository{
    private User user;

    /**
     * Gets the user from memory.
     *
     * @param id The id of the user.
     * @return a {@link User}
     */
    @Override
    public User getUser(int id) {
        // Normally this would go to a DB/etc and fetch the user with an ID.
        // Here though, we're just doing something in memory, so we're kind of just
        // faking it out.
        if (user == null) {
            user = new User();
            user.setId(id);
            user.setFirstName("Captain");
            user.setLastName("Crunch");
        }
        return user;
    }

    /**
     * Save's the in-memory user.
     *
     * @param user The user.
     */
    @Override
    public void save(User user) {
        if(this.user == null) {
            this.user = getUser(0); // create the in memory user.
        }
        this.user.setId(user.getId());
        this.user.setFirstName(user.getFirstName());
        this.user.setLastName(user.getLastName());
    }
}
