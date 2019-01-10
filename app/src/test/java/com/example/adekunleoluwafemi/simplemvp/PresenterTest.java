package com.example.adekunleoluwafemi.simplemvp;

import com.example.adekunleoluwafemi.simplemvp.model.User;
import com.example.adekunleoluwafemi.simplemvp.presentation.UserPresenter;
import com.example.adekunleoluwafemi.simplemvp.presentation.UserPresenterImpl;
import com.example.adekunleoluwafemi.simplemvp.presentation.ViewNotFoundException;
import com.example.adekunleoluwafemi.simplemvp.repository.UserRepository;
import com.example.adekunleoluwafemi.simplemvp.view.UserView;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class PresenterTest {
    UserPresenter presenter;
    User user;
    UserView mockUserView;
    UserRepository mockUserRepository;

    @Before
    public void setup() {
        mockUserRepository = mock(UserRepository.class);
        user = new User();
        user.setId(1);
        user.setFirstName("femi");
        user.setLastName("oluwa");
        when(mockUserRepository.getUser(anyInt())).thenReturn(user);

        mockUserView = mock(UserView.class);
        presenter = new UserPresenterImpl(mockUserRepository);
//        presenter.setView(mockUserView);

    }

    @Test
    public void noInteractionWithViewIfUserIsNull() {
        presenter.saveUser();

        verifyZeroInteractions(mockUserView);
    }

    @Test
    public void shouldLoadUserFromRepositoryWhenValidUserIsPresent() {
        when(mockUserView.getUserId()).thenReturn(1);
        presenter.setView(mockUserView);

//        verify repository interaction
        verify(mockUserRepository, times(1)).getUser(anyInt());

//        verify view Interaction
        verify(mockUserView, times(1)).getUserId();
        verify(mockUserView, times(1)).displayFirstName("femi");
        verify(mockUserView, times(1)).displayLastName("oluwa");
        verify(mockUserView, never()).showUserNotFoundMessage();
    }

    @Test
    public void shouldShowErrorMessageOnViewWhenUserNotPresent() {
        when(mockUserView.getUserId()).thenReturn(1);

//        Return null when we ask for a user
        when(mockUserRepository.getUser(anyInt())).thenReturn(null);

        presenter.setView(mockUserView);

        //        verify repository interaction
        verify(mockUserRepository, times(1)).getUser(anyInt());

        //        verify view Interaction
        verify(mockUserView, times(1)).getUserId();
        verify(mockUserView, times(1)).showUserNotFoundMessage();
        verify(mockUserView, never()).displayFirstName(anyString());
        verify(mockUserView, never()).displayLastName(anyString());
    }

    @Test
    public void shouldShowErrorMessageDuringSaveWhenFirstAndLastNameIsMissing() {
        when(mockUserView.getUserId()).thenReturn(1);

//        setup the view mock
        when(mockUserView.getFirstName()).thenReturn("");

//        set presenter view
        presenter.setView(mockUserView);
        presenter.saveUser();

        verify(mockUserView, times(1)).getFirstName();
        verify(mockUserView, never()).getLastName();
        verify(mockUserView, times(1)).showUserNameIsRequired();

//        return value for firstName and empty lastName
        when(mockUserView.getFirstName()).thenReturn("phemy");
        when(mockUserView.getLastName()).thenReturn("");

        presenter.saveUser();
        verify(mockUserView, times(2)).getFirstName();
        verify(mockUserView, times(1)).getLastName();
        verify(mockUserView, times(2)).showUserNameIsRequired();

    }

    @Test
    public void shouldBeAbleToSaveAValidUser() {
        when(mockUserView.getUserId()).thenReturn(1);

        presenter.setView(mockUserView);

        when(mockUserView.getFirstName()).thenReturn("phemy");
        when(mockUserView.getLastName()).thenReturn("Oluwa");

        presenter.saveUser();

        verify(mockUserView, times(2)).getFirstName();
        verify(mockUserView, times(2)).getLastName();

        assertThat(user.getFirstName(), is("phemy"));
        assertThat(user.getLastName(), is("Oluwa"));

        verify(mockUserRepository, times(1)).save(user);
        verify(mockUserView, times(1)).showUserSavedMessage();



    }

    @Test(expected = ViewNotFoundException.class)
    public void shouldThrowViewNotFoundExceptionWhenViewIsNull() {
        presenter.setView(null);
    }

//    @Test
//    public void shouldBeAbleToHandleNullFirstName() {
//        when(mockUserView.getUserId()).thenReturn(1);
//
//        presenter.setView(mockUserView);
//
//        verify(mockUserView, times(1)).getUserId();
//
//        when(mockUserView.getFirstName()).thenReturn(null);
//
//        presenter.saveUser();
//
//        verify(mockUserView, times(1)).getFirstName();
//        verify(mockUserView, never()).getLastName();
//        verify(mockUserView, times(1)).showUserNameIsRequired();
//    }
}
