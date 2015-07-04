package misskey.com.pictruemanager.activity;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import misskey.com.pictruemanager.R;
import misskey.com.pictruemanager.bean.PhotoInfo;
import misskey.com.pictruemanager.imageware.RotateImageViewAware;
import misskey.com.pictruemanager.utils.ThumbnailsUtil;
import misskey.com.pictruemanager.utils.UniversalImageLoadTool;
import misskey.com.pictruemanager.view.ZoomImageView;

/**
 * Created by misskey on 15-6-4.
 */
public class PictureFragment extends Fragment {
    private ZoomImageView zoomImageView;
    private Button btn_delete;
    private String path;
    private ProgressBar progressBar;
    private Bitmap bitmap;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        zoomImageView= (ZoomImageView)getView().findViewById(R.id.zoomImageView);
        progressBar= (ProgressBar) getView().findViewById(R.id.progress);
        new AsynTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.showpicture_layout,null);
    }
     class AsynTask extends AsyncTask<Void,Void,Bitmap>{

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
              Bundle args=getArguments();
             String photopath= args.getString("camephoto");
            //下面代码存在很大的bug 内存异常， 线程为退出，，
             BitmapFactory.Options opts = new BitmapFactory.Options();
             // 不读取像素数组到内存中，仅读取图片的信息
             opts.inJustDecodeBounds = true;
             BitmapFactory.decodeFile(photopath, opts);
             // 从Options中获取图片的分辨率
             int imageHeight = opts.outHeight;
             int imageWidth = opts.outWidth;
             DisplayMetrics dm = new DisplayMetrics();
             WindowManager manager = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
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
        zoomImageView.setImageBitmap(null);
        super.onDestroy();


    }
}
