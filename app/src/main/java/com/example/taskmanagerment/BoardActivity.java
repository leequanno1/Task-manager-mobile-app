package com.example.taskmanagerment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class BoardActivity extends AppCompatActivity {

    private Toolbar boardToolbar;
    private ImageView goBackButton, moreOptionsButtonOfBoard, notificationButtonOfBoard;
    private ImageView filterButtonOfBoard, confirmEditTitleName, closeEnterTitleName;

    private EditText boardTitleEdt, filterEdt;

    private boolean isFillerEdtVisibility = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_layout);

        initialComponents();

        setSupportActionBar(boardToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFillerEdtVisibility) {
                    filterEdt.setVisibility(View.GONE);
                    boardTitleEdt.setVisibility(View.VISIBLE);
                    filterButtonOfBoard.setVisibility(View.VISIBLE);

                    isFillerEdtVisibility = false;
                } else {
                    Intent intent = new Intent(BoardActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        boardTitleEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    boardTitleEdt.setBackgroundResource(R.drawable.under_line);

                    closeEnterTitleName.setVisibility(View.VISIBLE);
                    confirmEditTitleName.setVisibility(View.VISIBLE);

                    goBackButton.setVisibility(View.GONE);
                    filterButtonOfBoard.setVisibility(View.GONE);
                    notificationButtonOfBoard.setVisibility(View.GONE);
                    moreOptionsButtonOfBoard.setVisibility(View.GONE);

                    boardTitleEdt.post(new Runnable() {
                        @Override
                        public void run() {
                            boardTitleEdt.setSelection(boardTitleEdt.getText().length());
                        }
                    });
                }
            }
        });

        closeEnterTitleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                boardTitleEdt.setBackgroundColor(0);

                closeEnterTitleName.setVisibility(View.GONE);
                confirmEditTitleName.setVisibility(View.GONE);

                goBackButton.setVisibility(View.VISIBLE);
                filterButtonOfBoard.setVisibility(View.VISIBLE);
                notificationButtonOfBoard.setVisibility(View.VISIBLE);
                moreOptionsButtonOfBoard.setVisibility(View.VISIBLE);

                // Đảm bảo EditText có thể lấy lại tiêu điểm
                boardTitleEdt.setFocusable(false);
                boardTitleEdt.setFocusableInTouchMode(false);

                // Đặt lại tiêu điểm khi EditText được nhấn
                boardTitleEdt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boardTitleEdt.setFocusable(true);
                        boardTitleEdt.setFocusableInTouchMode(true);
                        boardTitleEdt.requestFocus();
                        boardTitleEdt.post(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(boardTitleEdt, InputMethodManager.SHOW_IMPLICIT);
                            }
                        });
                    }
                });
            }
        });


        confirmEditTitleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = boardTitleEdt.getText().toString();
                boardTitleEdt.setText(newTitle);
                hideKeyboard();
                boardTitleEdt.setBackgroundColor(0);

                closeEnterTitleName.setVisibility(View.GONE);
                confirmEditTitleName.setVisibility(View.GONE);

                goBackButton.setVisibility(View.VISIBLE);
                filterButtonOfBoard.setVisibility(View.VISIBLE);
                notificationButtonOfBoard.setVisibility(View.VISIBLE);
                moreOptionsButtonOfBoard.setVisibility(View.VISIBLE);

                boardTitleEdt.setFocusable(false);
            }
        });

        filterButtonOfBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFillerEdtVisibility = true;

                filterEdt.setBackgroundResource(R.drawable.under_line);
                filterEdt.setVisibility(View.VISIBLE);
                filterEdt.requestFocus();

                boardTitleEdt.setVisibility(View.GONE);
                filterButtonOfBoard.setVisibility(View.GONE);
            }
        });



    }

    private void initialComponents() {
        boardToolbar = (Toolbar) findViewById(R.id.board_toolbar);
        goBackButton = (ImageView) findViewById(R.id.go_back_button);
        boardTitleEdt = (EditText) findViewById(R.id.board_title_edt);
        moreOptionsButtonOfBoard = (ImageView) findViewById(R.id.more_options_button_of_board);
        notificationButtonOfBoard = (ImageView) findViewById(R.id.notification_button_of_board);
        filterButtonOfBoard = (ImageView) findViewById(R.id.filter_button_of_board);
        confirmEditTitleName = (ImageView) findViewById(R.id.confirm_edit_title_name);
        closeEnterTitleName = (ImageView) findViewById(R.id.close_enter_title_name);
        filterEdt = (EditText) findViewById(R.id.filter_edt);
    }

    // Method to hide the keyboard
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
