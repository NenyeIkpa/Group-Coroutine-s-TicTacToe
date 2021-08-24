package com.example.nenyesttt.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.nenyesttt.GameLogic;
import com.example.nenyesttt.R;

public class TicTacToeBoard extends View {
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private Boolean winning = false;
    private final int winningLineColor;
    private final GameLogic game;
    private final Paint paint = new Paint();
    private int cellSize = getWidth()/3;
    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
         TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard,0,0);
         try{
             boardColor = a.getColor(R.styleable.TicTacToeBoard_boardColor, 0);
             XColor = a.getColor(R.styleable.TicTacToeBoard_XColor,0);
             OColor = a.getColor(R.styleable.TicTacToeBoard_OColor, 0);
             winningLineColor = a.getColor(R.styleable.TicTacToeBoard_boardColor, 0);

         }finally {
             a.recycle();
         }
        game = new GameLogic();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            int row = (int) (Math.ceil(y/cellSize));
            int col = (int) (Math.ceil(x/cellSize));
            if(!winning){
                if(game.updateGameBoard(row,col)){
                    invalidate();
                    if(game.winnerCheck()){
                        winning = true;
                    }

                    //updating players turn
                    if(game.getPlayer()%2 ==0){
                        game.setPlayer(game.getPlayer()-1);
                    }else{
                        game.setPlayer(game.getPlayer()+1);
                    }
                }
            }



            invalidate();
            return true;

        }
        return false;
    }

    @Override
    protected void onMeasure(int width,int height){
        super.onMeasure(width,height);
        int dimension = Math.min(getMeasuredWidth(),getMeasuredHeight());
        setMeasuredDimension(dimension, dimension);
        cellSize = dimension/3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        drawGameBoard(canvas);
        drawMarkers(canvas);
        if(winning){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
//        drawX(canvas,1,1);
//        drawO(canvas,0,1);


    }

    private void drawMarkers(Canvas canvas) {
            for(int r = 0; r<3; r++){
                for(int c = 0; c<3; c++){
                    if(game.getRowColArray()[r][c] != 0){
                        if(game.getRowColArray()[r][c] == 1){
                                drawX(canvas,r,c);
                        }else {
                            drawO(canvas,r,c);

                        }
                    }
                }
            }
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
        for (int col = 0; col<3; col++){
            canvas.drawLine(cellSize*col,0,cellSize*col,canvas.getWidth(),paint);
        }
        for (int  row = 0; row<3; row++){
            canvas.drawLine(0,cellSize*row,canvas.getWidth(),cellSize*row,paint);
        }

    }
    private void drawX(Canvas canvas, int row, int col){
        paint.setColor(XColor);
        canvas.drawLine(
                (col+1)*cellSize-cellSize*0.2f,
             row*cellSize+cellSize*0.2f,
                col*cellSize+cellSize*0.2f,
                (row+1)*cellSize-cellSize*0.2f,
                paint
        );
        canvas.drawLine(
               col*cellSize+cellSize*0.2f,
                row*cellSize+cellSize*0.2f,
               (col+1)*cellSize-cellSize*0.2f,
               (row+1)*cellSize-cellSize*0.2f,
                paint
        );

    }
    private void drawO(Canvas canvas, int row, int col){
        paint.setColor(OColor);
        canvas.drawOval(
                col*cellSize + cellSize*0.2f,
                row*cellSize+ cellSize*0.2f,
                (col*cellSize + cellSize) - cellSize*0.2f,
                (row*cellSize + cellSize)- cellSize*0.2f,
                paint
        );

    }
    private void drawHorizontalLine(Canvas canvas, int row, int col){
        paint.setColor(OColor);
        canvas.drawLine(col,
                row*cellSize+ cellSize/2f,
                cellSize * 3,
                row*cellSize+ cellSize/2f,
                paint
        );
    }
    private void drawVerticalLine(Canvas canvas, int row, int col){
        paint.setColor(OColor);
        canvas.drawLine(col * cellSize + cellSize/2f,
                row,
                col *cellSize + cellSize/2f,
                cellSize*3,
                paint
        );
    }
    private void drawDiagonalLinePos(Canvas canvas){
        paint.setColor(OColor);
        canvas.drawLine(0,
                cellSize * 3,
                cellSize * 3,
                0,
                paint
        );
    }
    private void drawDiagonalLineNeg(Canvas canvas){
        paint.setColor(OColor);
        canvas.drawLine(0,
                0,
                cellSize * 3,
                cellSize * 3,
                paint
        );
    }
    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];
        int winType = game.getWinType()[2];
        switch (winType){
            case 1: drawHorizontalLine(canvas,row,col);
            break;
            case 2: drawVerticalLine(canvas, row,col);
            break;
            case 3:drawDiagonalLineNeg(canvas);
            break;
            case 4:drawDiagonalLinePos(canvas);
            break;

        }
    }


    public void setUpGame(Button playAgain,Button home,TextView playerDisplay, String[] names){
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerName(names);
    }

    public void resetGame(){
        game.resetGame();
        winning = false;
    }
}
