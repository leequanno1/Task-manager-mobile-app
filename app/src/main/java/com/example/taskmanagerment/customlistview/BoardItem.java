package com.example.taskmanagerment.customlistview;

public class BoardItem extends ItemModel {

    private String boardName;

    public BoardItem(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Override
    public int getType() {
        return TYPE_BOARD;
    }

    @Override
    public String getTitle() {
        return boardName;
    }

    @Override
    public String getDate() {
        return null;
    }

}
