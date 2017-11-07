package com.coship.subwaymarquee;

import com.coship.subwaymarquee.utils.Utils;
import com.coship.subwaymarquee.view.MarqueeView;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MarqueeView marqueeViewUp;
    private MarqueeView marqueeViewDown;

    private List<String> listA;
    private List<String> listB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        marqueeViewUp = (MarqueeView) findViewById(R.id.marquee_view_up);
        marqueeViewDown = (MarqueeView) findViewById(R.id.marquee_view_down);

        listA = Utils.getImages("/A");
        listB = Utils.getImages("/B");
        if (listA != null && listA.size() != 0 && listB != null && listB.size() != 0) {

            Log.d(TAG, " get the Image ");
            initDataUp(listA);
            initDataDown(listB);

        } else {
            Log.d(TAG, "Do not get the Image");
            initMarqueeView();

        }


    }

    /**
     * initiate the image data of down
     */
    private void initDataDown(List<String> pathsB) {

        String path = null;
        ImageView imageViewDown = null;
        for (int i = 0; i < pathsB.size(); i++) {
            path = pathsB.get(i);
            if (path != null) {
                Log.d(TAG, "path:" + path);
                imageViewDown = Utils.drawToImage(Utils.fileToDrawable(path));
                imageViewDown.setScaleType(ImageView.ScaleType.FIT_XY);
                Bitmap bitmap1=Utils.drawableToBitmap(Utils.fileToDrawable(path));

                final Bitmap bitmap2=amplifyIma(bitmap1);

                imageViewDown.setImageBitmap(bitmap2);


                marqueeViewDown.addViewInQueue(imageViewDown);

            }
        }

        marqueeViewDown.setScrollSpeed(5);
        marqueeViewDown.setScrollDirection(MarqueeView.RIGHT_TO_LEFT);
        marqueeViewDown.setViewMargin(0);
        marqueeViewDown.startScroll();
    }

    /**
     * initiate the image data of up
     */
    private void initDataUp(List<String> pathsA) {

        String path = null;
        ImageView imageViewUP = null;


        for (int i = 0; i < pathsA.size(); i++) {
            path = pathsA.get(i);
            if (path != null) {

                imageViewUP = Utils.drawToImage(Utils.fileToDrawable(path));
                imageViewUP.setScaleType(ImageView.ScaleType.FIT_XY);
                Log.d(TAG, "path:" + path);

                Bitmap bitmap1=Utils.drawableToBitmap(Utils.fileToDrawable(path));

                if (bitmap1!=null){

                    final Bitmap bitmap2=amplifyIma(bitmap1);

                    imageViewUP.setImageBitmap(bitmap2);

                }


                marqueeViewUp.addViewInQueue(imageViewUP);
            }
        }

        marqueeViewUp.setY(200);
        marqueeViewUp.setScrollSpeed(5);
        marqueeViewUp.setScrollDirection(MarqueeView.LEFT_TO_RIGHT);
        marqueeViewUp.setViewMargin(0);
        marqueeViewUp.startScroll();

    }

    public  Bitmap amplifyIma(Bitmap bitmap){

        Bitmap resizeBitmap=null;

        if (bitmap!=null){

            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
            //需要判断屏幕宽度和图片宽度的大小，来决定是放大还是缩小，如果是放大，应该还需要加上图片本身宽度，即：（倍数+1）为缩放倍数float num = ((float)bitmapWidth/mScreenWidth)+1.0f;
            //得到图片宽度比
            float num = (float)1.5;

            Matrix matrix = new Matrix();
            matrix.postScale(num, num);
            // 产生缩放后的Bitmap对象
            resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth,
                    bitmapHeight, matrix, true);

        }

        return resizeBitmap;
    }

    private void initMarqueeView() {


        ImageView iv1 = new ImageView(this);
        Drawable draw1 = this.getResources().getDrawable(R.drawable.updefault);
        iv1.setImageDrawable(draw1);
        marqueeViewUp.addViewInQueue(iv1);

        ImageView iv6 = new ImageView(this);
        Drawable draw6 = this.getResources().getDrawable(R.drawable.downdefault);
        iv6.setImageDrawable(draw6);
        marqueeViewDown.addViewInQueue(iv6);

        marqueeViewUp.setScrollSpeed(5);
        marqueeViewUp.setScrollDirection(MarqueeView.LEFT_TO_RIGHT);
        marqueeViewUp.setViewMargin(0);
        marqueeViewUp.startScroll();

        marqueeViewDown.setScrollSpeed(5);
        marqueeViewDown.setScrollDirection(MarqueeView.RIGHT_TO_LEFT);
        marqueeViewDown.setViewMargin(0);
        marqueeViewDown.startScroll();
    }


}
