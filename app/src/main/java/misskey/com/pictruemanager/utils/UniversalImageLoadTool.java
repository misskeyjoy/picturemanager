package misskey.com.pictruemanager.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

/**
 * Created by misskey on 15-6-4.
 */
public class UniversalImageLoadTool {
    private static ImageLoader imageLoader = ImageLoader.getInstance();
    public static ImageLoader getImageLoader() {
        return imageLoader;
    }
    public static boolean checkImageLoader() {
        return imageLoader.isInited();
    }

    public static void disPlay(String uri, ImageAware imageAware, int default_pic) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(default_pic)  //正在加载显示的图片
                .showImageForEmptyUri(default_pic)
                .showImageOnFail(default_pic)
                .cacheInMemory(true)   //缓冲到内存
                .cacheOnDisk(true)     //缓冲到磁盘
                .bitmapConfig(Bitmap.Config.RGB_565)  //图片的质量
                .displayer(new SimpleBitmapDisplayer())
                .build();
        imageLoader.displayImage(uri, imageAware, options);
    }

    public static void clear() {
        imageLoader.clearMemoryCache();
        imageLoader.clearDiskCache();
    }

    public static void resume() {
        imageLoader.resume();
    }

    public static void stop() {
        imageLoader.stop();
    }

    public static void destory() {
        imageLoader.destroy();
    }
    public static void pause(){imageLoader.pause();}

}
