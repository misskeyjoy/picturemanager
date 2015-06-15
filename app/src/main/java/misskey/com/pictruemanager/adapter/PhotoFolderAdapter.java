package misskey.com.pictruemanager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import misskey.com.pictruemanager.R;
import misskey.com.pictruemanager.bean.AlbumInfo;
import misskey.com.pictruemanager.bean.PhotoInfo;
import misskey.com.pictruemanager.imageware.RotateImageViewAware;
import misskey.com.pictruemanager.utils.ThumbnailsUtil;
import misskey.com.pictruemanager.utils.UniversalImageLoadTool;

/**
 * Created by misskey on 15-6-5.
 */
public class PhotoFolderAdapter extends BaseAdapter {
    private LayoutInflater minflater;
    private List<AlbumInfo> list;
    private ViewHolder viewHolder;
    private Context mcontext;
    public PhotoFolderAdapter(Context context, List<AlbumInfo> list) {
        minflater = LayoutInflater.from(context);
        this.mcontext=context;
        this.list=list;
    }

    public class ViewHolder {
        public ImageView imageView;
        public TextView descpriction;
        public TextView num;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumInfo albumInfo=list.get(position);
        if(convertView==null){
            viewHolder=new ViewHolder();
           convertView=minflater.inflate(R.layout.photofolderitem,null);
            ImageView imageView= (ImageView) convertView.findViewById(R.id.iv_folder);
            TextView tv_info= (TextView) convertView.findViewById(R.id.tv_info);
            TextView tv_num= (TextView) convertView.findViewById(R.id.tv_num);
            viewHolder.descpriction=tv_info;
            viewHolder.imageView=imageView;
            viewHolder.num=tv_num;
            convertView.setTag(viewHolder);
        } else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        UniversalImageLoadTool.disPlay(ThumbnailsUtil.MapgetHashValue(albumInfo.getImage_id(),albumInfo.getPath_file()),
                new RotateImageViewAware(viewHolder.imageView,albumInfo.getPath_absolute()), R.drawable.ic_launcher);
        viewHolder.num.setText(albumInfo.getName_album());
        viewHolder.descpriction.setText(list.get(position).getList().size()+ "张");
        return convertView;
    }
}
