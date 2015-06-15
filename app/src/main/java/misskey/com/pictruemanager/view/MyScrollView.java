package misskey.com.pictruemanager.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.nostra13.universalimageloader.core.ImageLoader;



/**自定义ScrollView,在其中动态地对图片进行管理
 * Created by misskey on 15-6-5.
 */
public class MyScrollView  extends ScrollView{
    /**
     * 每页加载的图片数量
     */
    public static final int PAGE_Size=15;
    /**
     * 记录当前加载到第几页
     */
    private int page;
    /**
     * 每列的高度
     */
    private int columWidth;
    /**
     * 当前第一列的高度
     */
    private int firstColumHeight;
    /**
     * 当前第二列的高度
     */
    private int secondColumHeight;
    /**
     * 当前第三列的高度
     */
    private int threeColumHeight;
    /**
     * 是否加载过一次layout,这里onLayout中初始化只需要一次
     */
    private boolean loadOnce;
    /**
     * MyScrollView布局的高度
     */
    private static int scrollViewHeight;
    /**
     * 记录上垂直方向的滚动距离
     */
    private static int lastScrollY=-1;
    /**
     * 图片加载工具
     */
    private ImageLoader imageLoader;
    private LinearLayout firstLinerLayout;
    private LinearLayout secondLinerLayout;
    private LinearLayout threeLinerLayout;
    private  static Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            MyScrollView myScrollView= (MyScrollView) msg.obj;
//            int scrollY=myScrollView.getScrollY();
//            //如果当前的股东位置和上次相同，表示停止
//            if(scrollY==lastScrollY){
//                // 当滚动的最底部，并且当前没有正在下载的任务时，开始加载下一页的图片
//                if (scrollViewHeight + scrollY >= scrollLayout.getHeight()
//                        && taskCollection.isEmpty()) {
//                    myScrollView.loadMoreImages();
//                }
//                myScrollView.checkVisibility();
//            } else {
//                lastScrollY = scrollY;
//                Message message = new Message();
//                message.obj = myScrollView;
//                // 5毫秒后再次对滚动位置进行判断
//                handler.sendMessageDelayed(message, 5);
//            }
//            super.handleMessage(msg);
//        }
//    }
        }};
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
