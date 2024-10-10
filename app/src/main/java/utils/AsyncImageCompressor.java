package utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.abpal.tel.R;
import com.developers.imagezipper.ImageZipper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AsyncImageCompressor extends AsyncTask<String, String, String> {

    private byte[] byteArray = null;
    private Context context;
    private File photoFile;
    private Typeface gothic;
    private Activity activity;
    private AsyncImageCompressorCallbackListner asyncImageCompressorCallbackListner;
    private String strLatLng;
    private boolean isFeNameAndIdPrintOnPhoto = false;
    private String feIdAndFeName;
    private int compressPhotoOfWhichConfiguration;
    private boolean isForEducationFamliy = false;
    private boolean isForSelfieOnly = false;

    public AsyncImageCompressor(Context context, File photoFile, Typeface gothic,
                                Activity activity, AsyncImageCompressorCallbackListner asyncImageCompressorCallbackListner,
                                String strLatLng, boolean isFeNameAndIdPrintOnPhoto, String feIdAndFeName, int compressPhotoOfWhichConfiguration,
                                boolean isForEducationFamliy, boolean isForSelfieOnly) {
        this.context = context;
        this.photoFile = photoFile;
        this.gothic = gothic;
        this.activity = activity;
        this.asyncImageCompressorCallbackListner = asyncImageCompressorCallbackListner;
        this.strLatLng = strLatLng;
        this.isFeNameAndIdPrintOnPhoto = isFeNameAndIdPrintOnPhoto;
        this.feIdAndFeName = feIdAndFeName;
        this.compressPhotoOfWhichConfiguration = compressPhotoOfWhichConfiguration;
        this.isForEducationFamliy = isForEducationFamliy;
        this.isForSelfieOnly = isForSelfieOnly;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            //if the case checks is for education then we reduce compress level as compare to else part
            if (isForEducationFamliy) {
                File file = new File(photoFile.getAbsolutePath());
                File imageZipperFile = new ImageZipper(context)
                        .setQuality(60)
                        .compressToFile(file);
                Bitmap compressedPre = BitmapFactory.decodeFile(imageZipperFile.getAbsolutePath());
                Bitmap compressed = Bitmap.createScaledBitmap(compressedPre, 920, 920, false);
                Bitmap mutableBitmap = compressed.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap resized = addTimeLat(mutableBitmap);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();

//                if ((byteArray.length / 1024) > 1024) {
//                    resized = compressImage(photoFile.getAbsolutePath(), 720.0f, 720.0f);
//                    resized.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                    byteArray = byteArrayOutputStream.toByteArray();
//                }
            } else if (isForSelfieOnly) {
                File file = new File(photoFile.getAbsolutePath());
                File imageZipperFile = new ImageZipper(context)
                        .setQuality(60)
                        .setMaxWidth(720)
                        .setMaxHeight(720)
                        .compressToFile(file);
                Bitmap compressedPre = BitmapFactory.decodeFile(imageZipperFile.getAbsolutePath());
                Bitmap compressed = Bitmap.createScaledBitmap(compressedPre, 920, 920, false);


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                compressed.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();

                if ((byteArray.length / 1024) > 1024) {
                    compressed = compressImage(photoFile.getAbsolutePath(), 720.0f, 720.0f);
                    compressed.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                }

                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(photoFile.getAbsolutePath());
                    //write the compressed bitmap at the destination specified by filename.
                    compressed.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    //MyApplication.getInstance().saveImage(finalBitmap,"scaledBitmapDraw")
                } catch (Exception e) {
                    //This is Catch Block
                }

            } else {
                File file = new File(photoFile.getAbsolutePath());
                File imageZipperFile = new ImageZipper(context)
                        .setQuality(60)
                        .setMaxWidth(720)
                        .setMaxHeight(720)
                        .compressToFile(file);
                Bitmap compressedPre = BitmapFactory.decodeFile(imageZipperFile.getAbsolutePath());
                Bitmap compressed = Bitmap.createScaledBitmap(compressedPre, 920, 920, false);
                Bitmap mutableBitmap = compressed.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap resized = addTimeLat(mutableBitmap);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();

                if ((byteArray.length / 1024) > 1024) {
                    resized = compressImage(photoFile.getAbsolutePath(), 720.0f, 720.0f);
                    resized.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                }
            }


        } catch (Exception e) {
            Log.d("Exception", "expection");
        }

        return photoFile.getAbsolutePath();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        asyncImageCompressorCallbackListner.onImageCompressorDone(s, compressPhotoOfWhichConfiguration);
    }

//    private String getImageFileNameAfterCompress(Bitmap bitmap, String fileName) {
//        try {
//            File mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG_PHOTO);
//            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
//                //Log.d("Fail", "failed to create directory");
//            }
//            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
//
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
//            byte[] bitmapdata = bos.toByteArray();
//
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(bitmapdata);
//            fos.flush();
//            fos.close();
//            return file.getAbsolutePath();
//        } catch (Exception e) {
//            //e.printStackTrace();
//            return null;
//        }
//    }

    public static Bitmap rotateImage(Bitmap bitmap, String photoPath) {
        try {
            ExifInterface exif;
            try {
                exif = new ExifInterface(photoPath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                bitmap = Bitmap.createBitmap(bitmap,
                        0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            } catch (IOException e) {
                //This is Catch Block
            }
            return bitmap;
        } catch (Exception e) {

        }
        return null;
    }


    public Bitmap addTimeLat(Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.white)); // Text Color
        paint.setTextSize(30); // Text Size
        paint.setTypeface(Typeface.create(gothic, Typeface.BOLD));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)); // Text Overlapping Pattern
        canvas.drawBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), null);
        Paint whitePaint = new Paint();
        whitePaint.setColor(context.getResources().getColor(R.color.imageAlpha));

        //increase height for background shadow when feid and name print
        if (isFeNameAndIdPrintOnPhoto) {
            canvas.drawRect(0, bitmap.getHeight(), bitmap.getWidth(), bitmap.getHeight() - 100, whitePaint);

        } else {
            canvas.drawRect(0, bitmap.getHeight(), bitmap.getWidth(), bitmap.getHeight() - 60, whitePaint);

        }
        try {
            String str = strLatLng;
            if (str.length() > 8) {
                //Log.e("Lat Long Length > 8", str)
                canvas.drawText(Utility.getInstance().getDatePhoto() + " | " + str, 30, bitmap.getHeight() - 20, paint);
            } else {
                //Log.e("Lat Long short", str)
                canvas.drawText(Utility.getInstance().getDatePhoto(), 30, bitmap.getHeight() - 20, paint);
            }


            //print fename and feid
            if (isFeNameAndIdPrintOnPhoto) {
                canvas.drawText(feIdAndFeName, 30, bitmap.getHeight() - 60, paint);
            }
        } catch (Exception e) {
            //This is Catch Block
        }
        //canvas.drawText(MyApplication.getDatePhoto(), 30, bitmap.getHeight()-10, paint)
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(photoFile.getAbsolutePath());
            //write the compressed bitmap at the destination specified by filename.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            //MyApplication.getInstance().saveImage(finalBitmap,"scaledBitmapDraw")
        } catch (Exception e) {
            //This is Catch Block
        }
        return bitmap;
    }

    public Bitmap compressImage(String imagePath, float maxHeight, float maxWidth) {
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        //Log.e("Actual width height",actualWidth+" , "+actualHeight)

        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(imagePath, options);
        } catch (OutOfMemoryError exception) {
            //exception.printStackTrace()
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.RGB_565);
        } catch (OutOfMemoryError exception) {
            //exception.printStackTrace()
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);

        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.white)); // Text Color
        paint.setTextSize(30); // Text Size
        paint.setTypeface(Typeface.create(gothic, Typeface.BOLD));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)); // Text Overlapping Pattern
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, paint);
        Paint whitePaint = new Paint();
        whitePaint.setColor(context.getResources().getColor(R.color.imageAlpha));

        //increase height for background shadow when feid and name print
        if (isFeNameAndIdPrintOnPhoto) {
            canvas.drawRect(0, bmp.getHeight(), bmp.getWidth(), bmp.getHeight() - 100, whitePaint);

        } else {
            canvas.drawRect(0, bmp.getHeight(), bmp.getWidth(), bmp.getHeight() - 60, whitePaint);

        }
        //Log.e("width height",""+scaledBitmap.getWidth()+" , "+scaledBitmap.getHeight())
        try {
            String str = strLatLng;
            if (str.length() > 8) {
                //Log.e("Lat Long Length > 8", str)
                canvas.drawText(Utility.getInstance().getDatePhoto() + " | " + str, 30, bmp.getHeight() - 20, paint);
            } else {
                //Log.e("Lat Long short", str)
                canvas.drawText(Utility.getInstance().getDatePhoto(), 30, bmp.getHeight() - 20, paint);
            }

            //print fename and feid
            if (isFeNameAndIdPrintOnPhoto) {
                canvas.drawText(feIdAndFeName, 30, bmp.getHeight() - 60, paint);
            }
        } catch (Exception e) {
            //This is Catch Block
        }
        if (bmp != null) {
            bmp.recycle();
        }

        ExifInterface exif;
        try {
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap,
                    0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        } catch (IOException e) {
            //This is Catch Block
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(photoFile.getAbsolutePath());
            //write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            //This is Catch Block
        }
        return scaledBitmap;
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public interface AsyncImageCompressorCallbackListner {
        void onImageCompressorDone(String imagePath, int compressPhotoOfWhichConfiguration);
    }
}
