package com.wt.sean.mvplib.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
**  类名：QRcodeUtil   创建目的： "生成二维码"
* @author  作者：wangtong
* @date  时间:"2019/5/22 0022 09:38"
*/
public class QRcodeUtil {

    private static QRcodeUtil instance = null;

    private QRcodeUtil() {
    }

    public static QRcodeUtil getInstance() {
        synchronized (QRcodeUtil.class) {
            if (instance == null) {
                instance = new QRcodeUtil();
            }
        }
        return instance;
    }


    public boolean  createQrcode(String content,int width,int height,String filePath){

        try {
            if (content == null || "".equals(content)) {
                return false;
            }

            //配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            hints.put(EncodeHintType.MARGIN, 1); //default is 4

            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = null;
            try {
                bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width,
                        height, hints);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }

            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);


            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，
            // 内存消耗巨大！
            return bitmap != null && bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    new FileOutputStream(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

}
