package misskey.com.pictruemanager.activity;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import misskey.com.pictruemanager.R;
import misskey.com.pictruemanager.adapter.PhotoGridAdapter;
import misskey.com.pictruemanager.bean.PhotoInfo;
import misskey.com.pictruemanager.bean.PhotoList;
import misskey.com.pictruemanager.utils.UniversalImageLoadTool;

/**
 * Created by yy156 on 2015/6/6.
 */
public class CamePictureFragment extends Fragment {
    private GridView gridView;
    private PhotoGridAdapter photoGridAdapter;
    private List<PhotoInfo> list;

    /**
     * 当点击gride的图片，传递给activity ，再与pictureFramgment交互
     */
    public interface  OnClickPicture{
        public void OnClickGirdePicture(String path);
    }
    private  OnClickPicture onClickPicture;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView = (GridView)getView().findViewById(R.id.gv_photos);
        Bundle args=getArguments();
        PhotoList photoGrid=(PhotoList) args.getSerializable("photo");
        list=new ArrayList<>();
        list.addAll(photoGrid.getList());
        photoGridAdapter=new PhotoGridAdapter(getActivity(),list);
        gridView.setAdapter(photoGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickPicture.OnClickGirdePicture(list.get(position).getPath_absolute());
            }
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState==0){
                    UniversalImageLoadTool.resume();
                }else{
                    UniversalImageLoadTool.pause();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(onClickPicture==null){
            onClickPicture=(OnClickPicture)activity;
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photogride, container, false);
    }
}
