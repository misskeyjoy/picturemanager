package misskey.com.pictruemanager.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import misskey.com.pictruemanager.R;
import misskey.com.pictruemanager.bean.PhotoInfo;

/**
 * Created by misskey on 15-6-15.
 */
public class StaggeredAdapter  extends  RecyclerView.Adapter<StaggeredAdapter.MyViewHodler>{
    private Context mContext;
    private List<PhotoInfo> photoInfos;
    public  StaggeredAdapter(Context mContext,List<PhotoInfo> photoInfos){
         this.mContext=mContext;
         this.photoInfos=photoInfos;
    }
    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHodler myViewHodler, int i) {

    }

    @Override
    public int getItemCount() {
        return photoInfos.size();
    }

    /**
     * 内部的ViewHolder 必须继承RecyclerView.ViewHolder
     */
    class MyViewHodler extends  RecyclerView.ViewHolder{
        ImageView iv;
        public MyViewHodler(View itemView) {
            super(itemView);
            //iv=itemView.findViewById(R.id.ph)
        }
    }
}
