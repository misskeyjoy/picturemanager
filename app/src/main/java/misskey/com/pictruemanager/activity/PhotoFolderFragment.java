package misskey.com.pictruemanager.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import misskey.com.pictruemanager.R;
import misskey.com.pictruemanager.adapter.PhotoFolderAdapter;
import misskey.com.pictruemanager.bean.AlbumInfo;
import misskey.com.pictruemanager.bean.PhotoFolder;
import misskey.com.pictruemanager.bean.PhotoInfo;
import misskey.com.pictruemanager.bean.PhotoList;
import misskey.com.pictruemanager.utils.UniversalImageLoadTool;

/**
 * Created by yy156 on 2015/6/6.
 */
public class PhotoFolderFragment  extends Fragment{

    private GridView gridView;
    private List<AlbumInfo> listImageInfo ;
    private PhotoFolderAdapter listAdapter;
    public interface OnPageLodingClickListener {
        public void onPageLodingClickListener(List<PhotoInfo> list);
    }
    private OnPageLodingClickListener onPageLodingClickListener;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView= (GridView) getView().findViewById(R.id.photo_folder);
        Bundle args=getArguments();
        PhotoFolder photoFolder= (PhotoFolder) args.getSerializable("folder");
        if(photoFolder==null) Log.e("Tag","photoFolder is null");
        listImageInfo=photoFolder.getList();
        listAdapter=new PhotoFolderAdapter(getActivity(),listImageInfo);
        gridView.setAdapter(listAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onPageLodingClickListener.onPageLodingClickListener(listImageInfo.get(position).getList());
            }
        });

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photofolder, container, false);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(onPageLodingClickListener==null){
            onPageLodingClickListener = (OnPageLodingClickListener)activity;
        }
    }
}


