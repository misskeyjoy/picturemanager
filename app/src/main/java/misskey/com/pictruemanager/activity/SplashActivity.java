package misskey.com.pictruemanager.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import misskey.com.pictruemanager.R;
import misskey.com.pictruemanager.bean.AlbumInfo;
import misskey.com.pictruemanager.bean.PhotoFolder;
import misskey.com.pictruemanager.bean.PhotoInfo;
import misskey.com.pictruemanager.bean.PhotoList;
import misskey.com.pictruemanager.utils.ThumbnailsUtil;

/**
 * Created by yy156 on 2015/6/7.
 */
public class SplashActivity extends Activity {
    private List<AlbumInfo> listImageInfo = new ArrayList<AlbumInfo>();
    private ContentResolver cr;
    private ProgressBar progressBar;
    /**
     * 可序列化对象 ，给PhotoFolder传值
     */
    private PhotoFolder photoFolder;
    /**
     * 可序列化对象，给CamePhoto传值
     */
    public PhotoList photoList;
    private TextView textView;

    public void getId(){
        progressBar= (ProgressBar) findViewById(R.id.splash_progress);
        textView= (TextView) findViewById(R.id.textview);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity);
        getId();
        cr= getContentResolver();
        photoList=new PhotoList();
        photoFolder=new PhotoFolder();
        new ImageAsyncTask().execute();
    }

    public class ImageAsyncTask extends AsyncTask<Void,Void,Object> {
        /**
         * 后台查询系统的缩略图和图片
         * @param params
         * @return
         */
        @Override
        protected Object doInBackground(Void... params) {
            ThumbnailsUtil.clear();
            String[] projection={MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.Thumbnails.IMAGE_ID, MediaStore.Images.Thumbnails.DATA};
            /**
             *查询系统分配好的缩略图
             */
            Cursor cursor= cr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
            if(cursor!=null&&cursor.moveToFirst()){
                int image_id;
                String image_path;
                int image_idColumn=cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
                int dataColumn=cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
                do{
                    image_id=cursor.getInt(image_idColumn);
                    image_path=cursor.getString(dataColumn);
                    ThumbnailsUtil.putValue(image_id,"file://"+image_path);
                }while (cursor.moveToNext());

            }
            //获取原图
            Cursor cur= cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, "date_modified DESC");
            String _path="_data";
            String _album="bucket_display_name";
            HashMap<String,AlbumInfo> myhash=new HashMap<>();
            List<PhotoInfo> CamePictureList=new ArrayList<>();
            AlbumInfo albumInfo=null;
            PhotoInfo photoInfo=null;
            if(cur!=null&&cur.moveToFirst()){
                do{
                    int index=0;
                    int _id=cur.getInt(cur.getColumnIndex("_id"));
                    String path=cur.getString(cur.getColumnIndex(_path));
                    String album=cur.getString(cur.getColumnIndex(_album));
                    List<PhotoInfo> stringList=new ArrayList<>();

                    //照相机拍出的图片单独处理
                    if(album.equals("Camera")){
                        PhotoInfo photoInfo1=new PhotoInfo();
                        photoInfo1.setImage_id(_id);
                        Log.e("path",path);
                        photoInfo1.setFile_path("file://"+path);
                        photoInfo1.setPath_absolute(path);
                        CamePictureList.add(photoInfo1);
                    }
                    photoInfo=new PhotoInfo();
                    if(myhash.containsKey(album)){
                        albumInfo=myhash.remove(album);
                        if(listImageInfo.contains(albumInfo)){
                            index=listImageInfo.indexOf(albumInfo);
                        }
                        photoInfo.setImage_id(_id);
                        photoInfo.setFile_path("file://"+path);
                        photoInfo.setPath_absolute(path);
                        albumInfo.getList().add(photoInfo);
                        listImageInfo.set(index,albumInfo);
                        myhash.put(album,albumInfo);
                    }else {
                        albumInfo =new AlbumInfo();
                        stringList.clear();
                        photoInfo.setImage_id(_id);
                        photoInfo.setFile_path("file://"+path);
                        photoInfo.setPath_absolute(path);
                        stringList.add(photoInfo);
                        albumInfo.setName_album(album);
                        albumInfo.setImage_id(_id);
                        albumInfo.setPath_file("file://"+path);
                        albumInfo.setPath_absolute(path);
                        albumInfo.setList(stringList);
                        listImageInfo.add(albumInfo);
                        myhash.put(album, albumInfo);


                    }
                }while(cur.moveToNext());
            }
            //记得游标关闭
            cur.close();
            cursor.close();
            photoList.setList(CamePictureList);
            photoFolder.setList(listImageInfo);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.e("PictureManager","异步加载完成");
            progressBar.setVisibility(View.GONE);
            textView.setText("加载图片信息完成");
            Bundle bundle=new Bundle();
            bundle.putSerializable("folder",photoFolder);
            bundle.putSerializable("photo",photoList);
            Intent intent=new Intent(SplashActivity.this,MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();

        }
    }

}
