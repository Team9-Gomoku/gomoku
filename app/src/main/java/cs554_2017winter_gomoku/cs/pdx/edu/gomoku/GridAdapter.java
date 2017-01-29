package cs554_2017winter_gomoku.cs.pdx.edu.gomoku;

import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class GridAdapter extends BaseAdapter {
    private final Activity activity;
    private int numColumns;
    private Square[][] squares;
    private Stone[][] stones;
    private Stone stoneToBePlaced = Stone.BLACK;

    public GridAdapter(Activity activity) {
        this.activity = activity;
        registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                // TODO this is rather wasteful
                for (int i = 0; i < numColumns; i++) {
                    for (int j = 0; j < numColumns; j++) {
                        squares[i][j].setStone(stones[i][j]);
                    }
                }
            }
        });
    }

    @Override
    public int getCount() {
        numColumns = ((GridView) activity.findViewById(R.id.gridview)).getNumColumns();
        return numColumns * numColumns;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (squares == null) {
            squares = new Square[numColumns][numColumns];
        }
        int row = getRow(position);
        int col = getColumn(position);
        if (squares[row][col] == null) {
            squares[row][col] = new Square(activity, position);
        }
        return squares[row][col];
    }

    void placeStoneAt(int position) {
        if (stones == null) {
            stones = new Stone[numColumns][numColumns];
        }
        stones[getRow(position)][getColumn(position)] = stoneToBePlaced;
        nextPlayer();
        notifyDataSetChanged();
    }

    private void nextPlayer() {
        if (stoneToBePlaced == Stone.BLACK) {
            stoneToBePlaced = Stone.WHITE;
        } else if (stoneToBePlaced == Stone.WHITE) {
            stoneToBePlaced = Stone.BLACK;
        } else {
            throw new IllegalStateException("This cannot happen.");
        }
    }

    private int getRow(int position) {
        return position / numColumns;
    }

    private int getColumn(int position) {
        return position % numColumns;
    }
}

class Square extends View {
    private Stone stone = null;

    public Square(Activity activity, final int position) {
        super(activity);
        setBackgroundColor(Color.GRAY);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stone == null) {
                    ((GridAdapter) ((GridView) getParent()).getAdapter()).placeStoneAt(position);
                }
            }
        });
    }

    void setStone(Stone s) {
        stone = s;
        invalidate();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas c) {
        c.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, new Paint());
        c.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), new Paint());
        if (stone == Stone.BLACK) {
            c.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 6, new Paint());
        } else if (stone == Stone.WHITE) {
            Paint p = new Paint();
            p.setColor(Color.WHITE);
            c.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 6, p);
        }
    }
}

enum Stone {
    BLACK, WHITE;
}