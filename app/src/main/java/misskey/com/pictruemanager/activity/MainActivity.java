package misskey.com.pictruemanager.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;

import misskey.com.pictruemanager.R;
import misskey.com.pictruemanager.bean.PhotoFolder;
import misskey.com.pictruemanager.bean.PhotoInfo;
import misskey.com.pictruemanager.bean.PhotoList;
import misskey.com.pictruemanager.utils.CheckImageLoaderConfiguration;
import misskey.com.pictruemanager.utils.UniversalImageLoadTool;

public class MainActivity extends FragmentActivity  implements ViewPager.OnPageChangeListener,View.OnClickListener,CamePictureFragment.OnClickPicture,PhotoFolderFragment.OnPageLodingClickListener{
    private ViewPager viewPager;
    private Button btn_camepictrue,btn_allpicture;
    private PhotoFolderFragment photoFolderFragment;
    private  CamePictureFragment camePictureFragment;
    private CheckImageLoaderConfiguration checkImageLoaderConfiguration;
    private  PictureFragment pictureFragment;
    private LinearLayout ll_btn, ll_text;
    private FrameLayout frameLayout;
    FragmentManager manager = getSupportFragmentManager();
    @Override
    protected void onStart() {
        super.onStart();
        CheckImageLoaderConfiguration.chekcImageLoaderConfiguration(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getViewId();
        UniversalImageLoadTool.checkImageLoader();
        checkImageLoaderConfiguration=new CheckImageLoaderConfiguration();
        manager = getSupportFragmentManager();

    }

    /**
     * 初始化控件
     */
     public void getViewId(){
         ll_btn= (LinearLayout) findViewById(R.id.ll_btn);
         ll_text= (LinearLayout) findViewById(R.id.ll_textview);
         btn_camepictrue= (Button) findViewById(R.id.btn_camepicture);
         btn_allpicture= (Button) findViewById(R.id.btn_allpicture);
         btn_camepictrue.setOnClickListener(this);
         btn_allpicture.setOnClickListener(this);
         viewPager= (ViewPager) findViewById(R.id.viewpager);
         viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
         viewPager.setCurrentItem(0,false);
         viewPager.setOnPageChangeListener(this);
         frameLayout= (FrameLayout) findViewById(R.id.framelayout);
     }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        
    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
      if(position==0){
          resetOtherTabs();
          btn_camepictrue.setTextColor(getResources().getColor(R.color.btn_color));
      }
        if(position==1){
            resetOtherTabs();
            btn_allpicture.setTextColor(getResources().getColor(R.color.btn_color));
        }
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        clickTab(v);
    }


    /**
     *  原来是通过启动一个新的fragment 现在变成启动一个新的activity
     * @param path 图片路径
     */
    @Override
    public void OnClickGirdePicture(String path) {

//        FragmentTransaction transaction = manager.beginTransaction();
//        Bundle bundle=new Bundle();
//        bundle.putString("camephoto",path);
//        pictureFragment = new PictureFragment();
//        pictureFragment.setArguments(bundle);
//        transaction.add(R.id.id_ll_container, pictureFragment);
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        transaction.addToBackStack(null);
//        // Commit the transaction
//        transaction.commit();

        Intent intent =new Intent(this,PhotoPictureActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("camephoto",path);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onPageLodingClickListener(List<PhotoInfo> list) {
        Intent intent =new Intent(this,PhotoGridActivity.class);
        PhotoList photoList=new PhotoList();
        photoList.setList(list);
        Bundle bundle=new Bundle();
        bundle.putSerializable("list",photoList);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public  class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        //得到每个item
        @Override
        public Fragment getItem(int position) {
            if(position==0)
            {
                if(camePictureFragment==null){
                    camePictureFragment=new CamePictureFragment();
                    Bundle bundle=new Bundle();
                    Log.e("MainActivity", getIntent().getSerializableExtra("photo").toString());
                    bundle.putSerializable("photo",getIntent().getSerializableExtra("photo"));
                    camePictureFragment.setArguments(bundle);
                }

                return  camePictureFragment;
            }
             else{
                if(photoFolderFragment==null){
                    photoFolderFragment=new PhotoFolderFragment();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("folder",getIntent().getSerializableExtra("folder"));
                    photoFolderFragment.setArguments(bundle);
                }

            return  photoFolderFragment;
            }
        }


        // 初始化每个页卡选项
        @Override
        public Object instantiateItem(ViewGroup arg0, int arg1) {
            // TODO Auto-generated method stub
            return super.instantiateItem(arg0, arg1);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println( "position Destory" + position);
            super.destroyItem(container, position, object);
        }

    }

    /**
     * 点击Tab按钮
     *
     * @param v
     */
    private void clickTab(View v)
    {
        resetOtherTabs();
        switch (v.getId())
        {
            case R.id.btn_allpicture:
                viewPager.setCurrentItem(1);
                btn_allpicture.setTextColor(getResources().getColor(R.color.btn_color));
                break;
            case R.id.btn_camepicture:
                btn_camepictrue.setTextColor(getResources().getColor(R.color.btn_color));
                viewPager.setCurrentItem(0);
                break;
       
        }
    }

    private void resetOtherTabs() {
       btn_allpicture.setTextColor(Color.BLACK);
        btn_camepictrue.setTextColor(Color.BLACK);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       //按住物理返回键处理相应的事件
        if(keyCode==KeyEvent.KEYCODE_BACK){
            frameLayout.setVisibility(View.VISIBLE);
            ll_btn.setVisibility(View.VISIBLE);
            ViewGroup viewGroup=ll_btn;
            for(int i=0;i<viewGroup.getChildCount();i++){
                viewGroup.getChildAt(i).setVisibility(View.VISIBLE);
                viewGroup.getChildAt(i).setEnabled(true);
            }
            if(pictureFragment!=null){
                pictureFragment.onDestroy();
                FragmentTransaction transaction= manager.beginTransaction();
                transaction.remove(pictureFragment);
                transaction.commit();
                viewPager.setCurrentItem(0);
            }

        }

            return super.onKeyDown(keyCode, event);


    }
}
