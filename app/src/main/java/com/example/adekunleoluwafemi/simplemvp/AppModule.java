package com.example.adekunleoluwafemi.simplemvp;

import com.example.adekunleoluwafemi.simplemvp.presentation.UserPresenter;
import com.example.adekunleoluwafemi.simplemvp.presentation.UserPresenterImpl;
import com.example.adekunleoluwafemi.simplemvp.repository.InMemoryUserRepositoryImpl;
import com.example.adekunleoluwafemi.simplemvp.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides @Singleton
    public UserRepository provideUserRepository() {
        return new InMemoryUserRepositoryImpl();
    }

    @Provides
    public UserPresenter provideUserPresenter(UserRepository userRepository) {
        return new UserPresenterImpl(userRepository);
    }

}
