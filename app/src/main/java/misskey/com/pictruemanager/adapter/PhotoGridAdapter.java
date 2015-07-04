package misskey.com.pictruemanager.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import misskey.com.pictruemanager.R;
import misskey.com.pictruemanager.bean.PhotoInfo;
import misskey.com.pictruemanager.imageware.RotateImageViewAware;
import misskey.com.pictruemanager.utils.ThumbnailsUtil;
import misskey.com.pictruemanager.utils.UniversalImageLoadTool;

/**
 * Created by misskey on 15-6-5.
 */
public class PhotoGridAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<PhotoInfo> list;
    private ViewHolder viewHolder;
    private int width;
    public  class ViewHolder {
         public ImageView imageView;
    }

    public PhotoGridAdapter(Context mContext, List<PhotoInfo> list) {
        this.list = list;
        /**
         * 获取屏幕的宽
         */
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        mInflater = LayoutInflater.from(mContext);
        width = dm.widthPixels/3;
        Log.e("TAG","width:"+width);
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
        PhotoInfo photoInfo=list.get(position);
        if (convertView == null) {
           viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.photogride_item, null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_thumbnail);
            viewHolder.imageView = imageView;
            if(imageView==null){
                Log.e("ImageView","is null");
            }
            convertView.setTag(viewHolder);
        } else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
       ViewGroup.LayoutParams layoutParams = viewHolder.imageView.getLayoutParams();
        layoutParams.width=width;
        layoutParams.height=width;
        viewHolder.imageView.setLayoutParams(layoutParams);
        if(photoInfo!=null){
            UniversalImageLoadTool.disPlay(ThumbnailsUtil.MapgetHashValue(photoInfo.getImage_id(), photoInfo.getFile_path()),
                    new RotateImageViewAware(viewHolder.imageView,photoInfo.getPath_absolute()), R.drawable.ic_launcher);
        }
        return convertView;
    }

}
