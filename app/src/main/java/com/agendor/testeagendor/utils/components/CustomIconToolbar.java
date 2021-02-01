package com.agendor.testeagendor.utils.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.agendor.testeagendor.R;
import com.agendor.testeagendor.model.IconToolbar;
import com.agendor.testeagendor.model.enums.AssignmentType;

public class CustomIconToolbar  extends LinearLayout {

    private View view;
    private TextView qnt, type;
    private ImageView image;
    private IconToolbar iconToolbar;

    public CustomIconToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomIconToolbar(Context context, IconToolbar iconToolbar) {
        super(context);
        this.iconToolbar = iconToolbar;

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.toolbar_icon, this);
        ViewGroup parent = (ViewGroup) view.getParent();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                (LayoutParams.MATCH_PARENT), (LayoutParams.MATCH_PARENT));
        view.setLayoutParams(lp);

        if(parent != null){
            parent.removeView(view);
        }

        this.image = this.findViewById(R.id.type_toolbar_icon);
        this.qnt = this.findViewById(R.id.qnt_toolbar_text);
        this.type = this.findViewById(R.id.type_toobar_text);

        this.image.setImageResource(getImageDrawable(iconToolbar.getType()));
        this.qnt.setText(iconToolbar.getQnt().toString());
        this.type.setText(iconToolbar.getType().getName());
    }



    public void setAtt(){
        this.image.setImageResource(getImageDrawable(iconToolbar.getType()));
        this.qnt.setText(iconToolbar.getQnt().toString());
        this.type.setText(iconToolbar.getType().getName());
    }

    private int getImageDrawable(AssignmentType type){
        switch (type){
            case EMAIL:
                return R.drawable.ic_baseline_mail_outline_24;
            case LIGACAO:
                return R.drawable.ic_baseline_call_24;
            case PROPOSTA:
                return R.drawable.ic_baseline_format_list_bulleted_24;
            case REUNIAO:
                return R.drawable.ic_baseline_business_center_24;
            case VISITA:
                return R.drawable.ic_baseline_place_24;
            default:
                return R.drawable.ic_baseline_more_horiz_24;
        }
    }

}
