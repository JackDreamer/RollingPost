package com.coship.subwaymarquee.utils;

import com.coship.subwaymarquee.MyApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 项目：MyApplication
 * 作者：909844
 * 时间：2017/11/3 18:20
 * 版本：1.0
 * 描述：描述内容
 */
public class Utils {

    private static final String TAG ="Utils" ;

    /**
     * 将文件转换为Drawable
     * @param path
     * @return
     */
    public static  Drawable fileToDrawable(String path){
        Drawable drawable=null;
        try{
            File file=new File(path);

            drawable=new BitmapDrawable(new FileInputStream(file));

        }catch (Exception e){
            e.printStackTrace();
        }

        return  drawable;
    }


    /**
     * 将Drawable对象转换为ImageView
     * @param drawable
     * @return
     */
    public static ImageView drawToImage(Drawable drawable){
        ImageView imageView=new ImageView(MyApplication.getApplication());

        imageView.setImageDrawable(drawable);

        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return  imageView;
    }


    /**
     * 获取U盘下面的目录
     * @return
     */
    private static String getExternalStorageDirectory(){
        String dir = new String();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                // System.out.println(line);
                if (line.contains("secure")) continue;
                if (line.contains("asec")) continue;

                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        dir = dir.concat(columns[1] );
                        break;
                    }
                } else if (line.contains("fuse")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        dir = dir.concat(columns[1]);
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dir;
    }

    /**
     * 获取A文件夹下的PNG文件
     * @return
     */
    public static ArrayList<String> getImages(String url){


        ArrayList<String> filelist = new ArrayList<String>();

        String path = getExternalStorageDirectory();
        if (path!=null){
            File root = new File(path+url);
//            File[] files = root.listFiles();
            List<String> list1 = Utils.orderListByName(root.getAbsolutePath());

            File[] files= new File[list1.size()];
            for(int i = 0; i<list1.size();i++)
            {
                files[i]=new File(list1.get(i));
            }

            if (files!=null && files.length != 0 ) {

                for(File file:files){
                    if(file.isDirectory()){
                        Log.d("Utils","I am a directory, ha ha");
                        filelist.remove(file.getAbsolutePath());
                    }else{
                        filelist.add(file.getAbsolutePath());
                    }
                }

            }

        }
        return filelist;
    }


    public static void getWH(Context context){

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        Log.d(TAG,"屏幕宽高分别为："+width+":"+height);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable to Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap=null;
        if (w>0 && h>0){
            bitmap = Bitmap.createBitmap(w, h, config);
            //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图



            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, w, h);
            drawable.draw(canvas);

        }

        return bitmap;
    }


    public static List<String> orderListByName(String filePath) {

        List<String> lists=new ArrayList<>();

        File file = new File(filePath);
        File[] files = file.listFiles();
        List fileList =null;
        if (files!=null&& files.length!=0){
            fileList = Arrays.asList(files);
        }
        if (fileList!=null && fileList.size()!=0) {
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    if (o1.isDirectory() && o2.isFile())
                        return -1;
                    if (o1.isFile() && o2.isDirectory())
                        return 1;
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }

        if (files!=null && files.length!=0){
            for (File file1 : files) {
                Log.d(TAG,"---after sort --->"+file1.getName());
                lists.add(file1.getAbsolutePath());

            }

        }

        return lists;
    }

}
