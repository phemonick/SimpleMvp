package com.example.adekunleoluwafemi.simplemvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adekunleoluwafemi.simplemvp.MvpApplication;
import com.example.adekunleoluwafemi.simplemvp.R;
import com.example.adekunleoluwafemi.simplemvp.presentation.UserPresenter;
import com.example.adekunleoluwafemi.simplemvp.view.UserView;

import javax.inject.Inject;

public class UserFragment extends Fragment implements UserView {

    @Inject
    UserPresenter userPresenter;

    protected EditText userFirstName;
    protected EditText userLastName;

    private static final String USER_ID = "user_id";

    public UserFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MvpApplication)getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        userFirstName = (EditText) v.findViewById(R.id.user_first_name);
        userLastName = (EditText) v.findViewById(R.id.user_last_name);
        v.findViewById(R.id.user_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPresenter.saveUser();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        userPresenter.setView(this);
    }

    @Override
    public int getUserId() {
        return getArguments() == null ? 0 : getArguments().getInt(USER_ID, 0);
    }

    @Override
    public void displayFirstName(String name) {
        userFirstName.setText(name);
    }

    @Override
    public void displayLastName(String name) {
        userLastName.setText(name);
    }

    @Override
    public void showUserNotFoundMessage() {
        Toast.makeText(getActivity(), R.string.user_not_found, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserSavedMessage() {
        Toast.makeText(getActivity(), R.string.user_saved, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getFirstName() {
        return userFirstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return userLastName.getText().toString();
    }

    @Override
    public void showUserNameIsRequired() {
        Toast.makeText(getActivity(), R.string.user_name_required_message, Toast.LENGTH_SHORT).show();
    }
}
