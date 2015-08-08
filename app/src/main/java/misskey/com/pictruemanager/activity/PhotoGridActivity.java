package misskey.com.pictruemanager.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import misskey.com.pictruemanager.R;
import misskey.com.pictruemanager.adapter.PhotoGridAdapter;
import misskey.com.pictruemanager.bean.AlbumInfo;
import misskey.com.pictruemanager.bean.PhotoInfo;
import misskey.com.pictruemanager.bean.PhotoList;
import misskey.com.pictruemanager.utils.UniversalImageLoadTool;

/**
 * Created by yy156 on 2015/6/6.
 */
public class PhotoGridActivity extends Activity {

    private GridView gridView;
    private PhotoGridAdapter photoGridAdapter;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allphotogride);
        gridView = (GridView)findViewById(R.id.allphotos);
        textView= (TextView) findViewById(R.id.tv_empty);
        gridView.setEmptyView(textView);
        Bundle bundle=getIntent().getExtras();
        Log.e("TAG","启动");
        final PhotoList photoList= (PhotoList) bundle.getSerializable("list");
        photoGridAdapter=new PhotoGridAdapter(this,photoList.getList());
        gridView.setAdapter(photoGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //取出相应的图片信息  可以传入集合名
                PhotoInfo photoInfo=photoList.getList().get(position);
                Log.e("TAG",position+"");
                Intent intent =new Intent(PhotoGridActivity.this,PhotoPictureActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("camephoto",photoInfo.getPath_absolute());
                intent.putExtras(bundle);
                startActivity(intent);

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
}
