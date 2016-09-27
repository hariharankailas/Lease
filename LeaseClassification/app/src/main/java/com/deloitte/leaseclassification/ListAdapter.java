package com.deloitte.leaseclassification;



        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.PorterDuff;
        import android.graphics.PorterDuffColorFilter;
        import android.graphics.drawable.Drawable;
        import android.graphics.drawable.GradientDrawable;
        import android.os.Build;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ArrayAdapter;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import java.util.ArrayList;

/**
 * Created by hariramesh on 9/23/16.
 */
public class ListAdapter extends ArrayAdapter<String> {
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *
     */


    ArrayList<String> a;
    public ListAdapter(Context context, int resource, ArrayList<String> arr) {
        super(context, resource);
        a = arr;
    }

    @Override
    public int getCount() {
        return a.size();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.bubblelist,parent,false);
        TextView text = (TextView)inflater.findViewById(R.id.text1);


//        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        text.setText(a.get(position));

        if(position==0||position==1)
        {
            text.setBackgroundColor(getContext().getResources().getColor(R.color.appBubble));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            text.setLayoutParams(lp);

        }
        else
        if(position % 2 ==1)
        {
            text.setBackground(getContext().getResources().getDrawable(R.drawable.round_green_bg));
            text.setTextColor(getContext().getResources().getColor(R.color.buttonHolder));
        }
        else
        {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            text.setLayoutParams(lp);
            text.setBackground(getContext().getResources().getDrawable(R.drawable.round_ques_bg));
            text.setTextColor(getContext().getResources().getColor(R.color.buttonHolder));

        }
//        text.setAnimation(slideUp);
//        text.startAnimation(slideUp);
        return inflater;



    }
}

