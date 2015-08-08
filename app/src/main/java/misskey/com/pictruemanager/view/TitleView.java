package misskey.com.pictruemanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import misskey.com.pictruemanager.R;

/**
 * Created by yy156 on 2015/7/8.
 */
public class TitleView  extends RelativeLayout implements View.OnClickListener
{
    private ImageButton mBack;
    private ImageButton mChoice;
    private TextView textView;
    private ActionTvEvent action;
    public TitleView(Context context) {
        this(context,null);
    }
    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        View.inflate(getContext(), R.layout.layout_title,this);
        textView= (TextView) findViewById(R.id.tv_title);
        mBack= (ImageButton) findViewById(R.id.title_iv);
        mChoice= (ImageButton) findViewById(R.id.title_allchoice);
        mBack.setOnClickListener(this);
        mChoice.setOnClickListener(this);

    }
    public void setTvTitle(String s){
        textView.setText(s);
    }
    public void setAction(ActionTvEvent a){
        this.action=action;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_allchoice:
                 action.setAllChoice();
                break;
            case R.id.title_iv:
                action.setBack();
                break;
        }
    }
    public void setInvislible(){
        this.setVisibility(GONE);
        textView.setVisibility(GONE);
        mChoice.setVisibility(GONE);
    }
    public void setislible(){
        this.setVisibility(VISIBLE);
        textView.setVisibility(VISIBLE);
        mChoice.setVisibility(VISIBLE);
    }
    public interface ActionTvEvent{
        void setAllChoice();
        void setBack();
    }

}
