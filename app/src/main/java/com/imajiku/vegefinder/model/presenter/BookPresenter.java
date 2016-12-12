package com.imajiku.vegefinder.model.presenter;


import com.imajiku.vegefinder.model.model.BookModel;
import com.imajiku.vegefinder.model.view.BookView;

/**
 * Created by Alvin on 2016-10-08.
 */
public class BookPresenter {
    private BookView view;
    private BookModel model;

    public BookPresenter(BookView v) {
        this.view = v;
    }

    public BookView getView() {
        return view;
    }

    public void setModel(BookModel model) {
        this.model = model;
    }

    public void book(int userId, int placeId, String date, String time, String comment) {
        model.book(userId, placeId, date, time, comment);
    }

    public void successBook() {
        view.successBook();
    }

    public void failedBook(String s) {
        view.failedBook(s);
    }
}

