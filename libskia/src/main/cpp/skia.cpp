#include <jni.h>
#include <string>
#include <skia.h>
#include <android/bitmap.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_kejin_android_skia_NativeLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_kejin_android_skia_Skia_testDraw(JNIEnv *env, jobject thiz, jobject bmp) {
    AndroidBitmapInfo info;
    void *pixels;
    if (AndroidBitmap_getInfo(env, bmp, &info) < 0) {
        return;
    }
    if (AndroidBitmap_lockPixels(env, bmp, &pixels) < 0) {
        return;
    }
    SkImageInfo imageInfo = SkImageInfo::Make(
            info.width, info.height, kRGBA_8888_SkColorType, kPremul_SkAlphaType);
    SkBitmap bitmap;
    bitmap.setInfo(imageInfo, imageInfo.minRowBytes());
    bitmap.setPixels(pixels);
    SkCanvas canvas(bitmap);
    SkPaint paint;
    paint.setColor(SK_ColorRED);
    SkRect rect = SkRect::MakeXYWH(0, 0, info.width, info.height);
    canvas.drawRect(rect, paint);

    paint.setColor(SK_ColorWHITE);

    canvas.drawCircle(SkPoint::Make(info.width/2.0, info.height/2.0), 300, paint);


    paint.setColor(SK_ColorBLUE);
    SkFont font;
    font.setSize(30);

    sk_sp<SkTextBlob> blob1 =
            SkTextBlob::MakeFromString("Skia!", SkFont(nullptr, 30));
    canvas.drawTextBlob(blob1.get(), info.width/2.0, info.height/2.0, paint);

    AndroidBitmap_unlockPixels(env, bmp);
}