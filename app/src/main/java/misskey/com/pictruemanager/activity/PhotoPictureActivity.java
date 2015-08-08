package misskey.com.pictruemanager.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import misskey.com.pictruemanager.R;
import misskey.com.pictruemanager.view.ZoomImageView;

/**
 * Created by misskey on 15-6-10.
 */
public class PhotoPictureActivity extends AppCompatActivity {
    private ZoomImageView zoomImageView;
    private Button btn_delete;
    private String path;
    private ProgressBar progressBar;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.showpicture_layout);
        setOverflowButtonAlways();
        getId();
        new AsynTask().execute();
    }
    //初始化控件
    public  void getId(){
        zoomImageView= (ZoomImageView) findViewById(R.id.zoomImageView);
        progressBar= (ProgressBar) findViewById(R.id.progress);
    }

    /**
     * 加载图片的异步类
     *
     * 加载过程可能会很慢 可以开启动画效果来替换progressbar
     */
    class AsynTask extends AsyncTask<Void,Void,Bitmap> {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Bitmap doInBackground(Void... params) {
            Intent intent= getIntent();
            Bundle args=intent.getExtras();
            String photopath= args.getString("camephoto");
            //下面代码存在很大的bug 内存异常， 线程为退出，，，
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 不读取像素数组到内存中，仅读取图片的信息
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photopath, opts);
            // 从Options中获取图片的分辨率
            int imageHeight = opts.outHeight;
            int imageWidth = opts.outWidth;
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getMetrics(dm);
            int windowHeight = dm.heightPixels;
            int windowWidth = dm.widthPixels;
            // 计算采样率
            int scaleX = imageWidth / windowWidth;
            int scaleY = imageHeight / windowHeight;
            int scale = 1;
            // 采样率依照最大的方向为准
            if (scaleX > scaleY && scaleY >= 1) {
                scale = scaleX;
            }
            if (scaleX < scaleY && scaleX >= 1) {
                scale = scaleY;
            }

            // false表示读取图片像素数组到内存中，依照设定的采样率
            opts.inJustDecodeBounds = false;
            // 采样率
            opts.inSampleSize = scale;
            bitmap = BitmapFactory.decodeFile(photopath, opts);
            return  bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressBar.setVisibility(View.GONE);
            zoomImageView.setImageBitmap(bitmap);

        }
    }

    @Override
    public void onDestroy() {
        bitmap.recycle();
        zoomImageView.setImageBitmap(null);
        super.onDestroy();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_showpictur_main, menu);
        return true;
    }

    private  void  setOverflowButtonAlways(){
        ViewConfiguration configuration=ViewConfiguration.get(this);
        try {
            Field menuKey=ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(configuration,false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
