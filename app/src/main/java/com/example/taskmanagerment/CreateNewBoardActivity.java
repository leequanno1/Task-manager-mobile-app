package com.example.taskmanagerment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNewBoardActivity extends AppCompatActivity {

    // Declare components

    private ImageView closeCreateNewBoard;

    private EditText boardNameEdt;

    private Button createNewBoardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cretae_new_board_layout);

        // Cal method to initial components
        initialComponents();

        // Redirect to home view when user click to the close create new board button.
        closeCreateNewBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateNewBoardActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Method to create new board and redirect to the home view when user click to the create new board button.
        createNewBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boardName = boardNameEdt.getText().toString();

                // Validation view before create new board and redirect.
                if (boardName.isEmpty()) {
                    DialogUtils.showAlertDialog(CreateNewBoardActivity.this, "Please enter complete information!");
                    return;
                }

                // Redirect and send data to home view.
                Intent intent = new Intent(CreateNewBoardActivity.this, HomeActivity.class);
                intent.putExtra("boardName", boardName);
                startActivity(intent);
            }
        });

    }

    // Method to initial components.
    private void initialComponents() {
        closeCreateNewBoard = (ImageView) findViewById(R.id.close_create_new_board);
        boardNameEdt = (EditText) findViewById(R.id.board_name_edt);
        createNewBoardButton = (Button) findViewById(R.id.create_new_board_btn);
    }


}
