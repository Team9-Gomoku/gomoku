package cs554_2017winter_gomoku.cs.pdx.edu.gomoku;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

/**
 * Created by harry on 1/26/17.
 */
public class GridAdapter extends BaseAdapter {

    private final Context mContext;

    public GridAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return 14 * 14;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return new Square(mContext);
    }
}

class Square extends View {
    public Square(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable cd = (ColorDrawable) getBackground();
                if (cd.getColor() == Color.WHITE) {
                    setBackgroundColor(Color.BLACK);
                } else {
                    setBackgroundColor(Color.WHITE);
                }
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO event.getX() event.getY()
                return false;
            }
        });
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}