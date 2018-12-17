package me.nereo.image_selector;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import me.nereo.image_selector.utils.FileUtils;

/**
 * @Author: chenjianrun
 * @Time: 2018/12/12
 * @Description: 获取打开摄像机、图片裁剪、打开图库的 Intent
 */
public class PictureIntentHelper {

    /**
     * 获取打开照相机的 intent，适配 Android 7.0
     * @param activity
     * @param mTmpFile
     * @return
     */
    public static Intent getOpenCameraIntent(Activity activity,File mTmpFile){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            if (mTmpFile != null && mTmpFile.exists()) {
                if (Build.VERSION.SDK_INT >= 24) {
                    // 适配 Android 7.0
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(activity, activity.getPackageName()+".provider",mTmpFile));
                } else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                }
            } else {
                Toast.makeText(activity, me.nereo.image_selector.R.string.error_image_not_exist, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, me.nereo.image_selector.R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
        }
        return intent;
    }

    /**
     * 获取打开 打开图库，选择单个图片的 Intent（返回 Intent，目前是想配合 SimpleForResult 一起使用）
     * @param activity
     * @return
     */
    public static Intent getSelectSingleImageIntent(Activity activity,boolean showCamera){
        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
        // 是否开启相机
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
        // 选择的照片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);

        // 是否多选、单选
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        return intent;
    }

    /**
     * 获取打开打开图库，选择多张图片的 Intent（返回 Intent，目前是想配合 SimpleForResult 一起使用）
     * @param activity
     * @return
     */
    public static Intent getSelectMultiImageIntent(Activity activity,boolean showCamera,int imageCount){
        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
        // 是否开启相机
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
        // 选择的照片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, imageCount);
        // 是否多选、单选
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        return intent;
    }


    /**
     * 获取发起剪裁图片请求的 Intent
     *
     * @param context    上下文
     * @param srcFile     原文件的File
     * @param output      输出文件的File
     */
    public static Intent getPhotoZoomIntent(Context context, File srcFile, File output) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(context, srcFile), "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        // intent.putExtra("return-data", false);


        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection

        return intent;
    }


    /**
     * 安卓7.0裁剪根据文件路径获取uri
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }



}
