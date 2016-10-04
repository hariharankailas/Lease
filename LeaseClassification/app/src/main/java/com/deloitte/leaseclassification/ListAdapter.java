package com.deloitte.leaseclassification;



        import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
        import android.text.TextUtils;
        import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ArrayAdapter;
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
   int id;
    public ListAdapter(Context context, int resource, ArrayList<String> arr) {
        super(context, resource);
        a = arr;
        Log.i("999",""+id);
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
        TextView text2 = (TextView)inflater.findViewById(R.id.textView2);
        RelativeLayout rl = (RelativeLayout)inflater.findViewById(R.id.relativeLayout);


        //   Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        text.setText(a.get(position));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        if(position==0||position==1||position==2)
        {
            text.setBackgroundColor(getContext().getResources().getColor(R.color.appBubble));
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            text.setBackground(getContext().getResources().getDrawable(R.drawable.round_intro));

            text.setLayoutParams(lp);

        }
        else{
        if(position % 2 !=1)
        {
            text.setBackground(getContext().getResources().getDrawable(R.drawable.round_green_bg));
            text.setTextColor(getContext().getResources().getColor(R.color.buttonHolder));
            text.setGravity(Gravity.CENTER);
        //    text.setAnimation(slideUp);
          //  text.startAnimation(slideUp);
        }
        else
        {
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            text.setLayoutParams(lp);
            text.setBackground(getContext().getResources().getDrawable(R.drawable.shadow));
            text.setTextColor(getContext().getResources().getColor(R.color.buttonHolder));
            text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.question_icon, 0, 0, 0);
            text.setCompoundDrawablePadding(40);
           // text.setPadding(10,40,0,40);

            if(a.get(position).equals("Contract contains a lease")){

                text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.exclamation, 0, 0, 0);
                text.setCompoundDrawablePadding(60);
                text.setTextSize(20);
                rl.setBackground(getContext().getResources().getDrawable(R.drawable.round_gradient_bg));
                text.setBackground(getContext().getResources().getColor());
                final SpannableString textToShow = new SpannableString("Contract contains a Lease");


                textToShow.setSpan(new RelativeSizeSpan(2f), textToShow.length() - "Lease".length(),textToShow.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                text2.setText("Lease");

            }
            if(a.get(position).equals("Lessee classifies lease as a finance lease")){
                text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.exclamation, 0, 0, 0);
                text.setCompoundDrawablePadding(60);
                text.setTextSize(15);
                text.setBackground(getContext().getResources().getDrawable(R.drawable.round_gradient_bg));
                // text.setText(Html.fromHtml("<h5>Contract contains </h5>"+ "<h1>"+"LEASE"));
                final SpannableString textToShow = new SpannableString("Lessee classifies lease as a Finance Lease");
                textToShow.setSpan(new RelativeSizeSpan(2f), textToShow.length() - "Finance Lease".length(),textToShow.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                //ggg
             //   String CurrentString = textToShow;
              //  String[] s = CurrentString.split(":");


              //  String  s3 = String.format("%s \n %s",s[0] , s[1]);
                text.setText(textToShow);
            }

            if(a.get(position).equals("Lessee classifies lease as an operating lease")){
                text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.exclamation, 0, 0, 0);
                text.setCompoundDrawablePadding(60);
                text.setTextSize(20);
                text.setBackground(getContext().getResources().getDrawable(R.drawable.round_gradient_bg));
                // text.setText(Html.fromHtml("<h5>Contract contains </h5>"+ "<h1>"+"LEASE"));
                final SpannableString textToShow = new SpannableString("Lessee classifies lease as a Operating Lease");
                textToShow.setSpan(new RelativeSizeSpan(2f), textToShow.length() - "Operating Lease".length(),textToShow.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.setText(textToShow);
            }

            if(a.get(position).equals("Contract does not contain a lease")){
                text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.exclamation, 0, 0, 0);
                text.setCompoundDrawablePadding(60);
                text.setTextSize(20);
                text.setBackground(getContext().getResources().getDrawable(R.drawable.round_gradient_bg));
                // text.setText(Html.fromHtml("<h5>Contract contains </h5>"+ "<h1>"+"LEASE"));
                final SpannableString textToShow = new SpannableString("Contract does not contain a Lease");
                textToShow.setSpan(new RelativeSizeSpan(2f), textToShow.length() - "Lease".length(),textToShow.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.setText(textToShow);
            }



        }}
        return inflater;

    }
}

