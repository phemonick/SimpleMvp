package com.example.adekunleoluwafemi.simplemvp;

import com.example.adekunleoluwafemi.simplemvp.view.fragment.UserFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(UserFragment target);
}
