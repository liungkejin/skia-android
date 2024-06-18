//
// Created by LiangKeJin on 2024/6/16.
//

#include "skia.h"
#include <jni.h>
#include <android/bitmap.h>

#pragma clang diagnostic push
#pragma ide diagnostic ignored "MemoryLeak"

class BmpCanvas {
public:
    BmpCanvas(int width, int height, void *pixels = nullptr) {
        bitmap.allocN32Pixels(width, height);
        if (pixels) {
            SkPixmap pixmap(SkImageInfo::MakeN32Premul(width, height), pixels, width * 4);
            bitmap.writePixels(pixmap);
        }
        canvas = new SkCanvas(bitmap);
    }

    ~BmpCanvas() {
        delete canvas;
    }

public:
    SkCanvas *canvas;

private:
    SkBitmap bitmap;
};

#define SK_CANVAS ((BmpCanvas *) native_ptr)->canvas

extern "C"
JNIEXPORT jlong JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nNew(JNIEnv *env, jobject thiz,
                                           jint width, jint height) {
    return reinterpret_cast<jlong>(new BmpCanvas(width, height));
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nNew2(JNIEnv *env, jobject thiz,
                                            jobject bmp) {
    AndroidBitmapInfo info;
    if (AndroidBitmap_getInfo(env, bmp, &info) != 0) {
        return 0;
    }
    void *pixels;
    if (AndroidBitmap_lockPixels(env, bmp, &pixels) != 0) {
        return 0;
    }
    auto *canvas = new BmpCanvas(info.width, info.height, pixels);
    AndroidBitmap_unlockPixels(env, bmp);
    return reinterpret_cast<jlong>(canvas);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nFree(JNIEnv *env, jobject thiz, jlong native_ptr) {
    auto *canvas = reinterpret_cast<BmpCanvas *>(native_ptr);
    delete canvas;
}
#pragma clang diagnostic pop

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nReadPixels(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                  jobject bmp) {
    AndroidBitmapInfo info;
    if (AndroidBitmap_getInfo(env, bmp, &info) != 0) {
        return false;
    }
    void *pixels;
    if (AndroidBitmap_lockPixels(env, bmp, &pixels) != 0) {
        return false;
    }
    SkPixmap pixmap(SkImageInfo::MakeN32Premul(info.width, info.height), pixels, info.stride);
    SK_CANVAS->readPixels(pixmap, 0, 0);
    AndroidBitmap_unlockPixels(env, bmp);

    return true;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nSave(JNIEnv *env, jobject thiz, jlong native_ptr) {
    SK_CANVAS->save();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nRestore(JNIEnv *env, jobject thiz, jlong native_ptr) {
    SK_CANVAS->restore();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nSaveCount(JNIEnv *env, jobject thiz, jlong native_ptr) {
    return SK_CANVAS->getSaveCount();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nRestoreToCount(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                      jint save_count) {
    SK_CANVAS->restoreToCount(save_count);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nTranslate(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                 jfloat dx, jfloat dy) {
    SK_CANVAS->translate(dx, dy);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nScale(JNIEnv *env, jobject thiz, jlong native_ptr, jfloat sx,
                                             jfloat sy) {
    SK_CANVAS->scale(sx, sy);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nRotate__JF(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                  jfloat degrees) {
    SK_CANVAS->rotate(degrees);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nRotate__JFFF(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                    jfloat degrees, jfloat px, jfloat py) {
    SK_CANVAS->rotate(degrees, px, py);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nSkew(JNIEnv *env, jobject thiz, jlong native_ptr, jfloat sx,
                                            jfloat sy) {
    SK_CANVAS->skew(sx, sy);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nConcat(JNIEnv *env, jobject thiz, jlong native_ptr,
                                              jfloatArray mat3x3) {
    jfloat *mat = env->GetFloatArrayElements(mat3x3, nullptr);
    SkMatrix matrix;
    matrix.set9(mat);
    SK_CANVAS->concat(matrix);
    env->ReleaseFloatArrayElements(mat3x3, mat, 0);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nSetMatrix(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                 jfloatArray mat3x3) {
    jfloat *mat = env->GetFloatArrayElements(mat3x3, nullptr);
    SkMatrix matrix;
    matrix.setAll(mat[0], mat[1], mat[2], mat[3], mat[4], mat[5], mat[6], mat[7], mat[8]);
    SK_CANVAS->setMatrix(matrix);
    env->ReleaseFloatArrayElements(mat3x3, mat, 0);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nResetMatrix(JNIEnv *env, jobject thiz, jlong native_ptr) {
    SK_CANVAS->resetMatrix();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nClear(JNIEnv *env, jobject thiz, jlong native_ptr,
                                             jint color) {
    SK_CANVAS->clear(color);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawColor(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                 jint color, jint blend_mode) {
    SkBlendMode mode = static_cast<SkBlendMode>(blend_mode);
    SK_CANVAS->drawColor(color, mode);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDiscard(JNIEnv *env, jobject thiz, jlong native_ptr) {
    SK_CANVAS->discard();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawPaint(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                 jlong paint_native_ptr) {
    SkPaint &paint = *(reinterpret_cast<SkPaint *>(paint_native_ptr));
    SK_CANVAS->drawPaint(paint);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawPoints(JNIEnv *env, jobject thiz,
                                                  jlong native_ptr, jint mode,
                                                  jfloatArray pts, jint pointCount,
                                                  jlong paint_native_ptr) {
    SkCanvas::PointMode pointMode = (SkCanvas::PointMode) mode;
    SkPaint &paint = *(reinterpret_cast<SkPaint *>(paint_native_ptr));

    jfloat *points = env->GetFloatArrayElements(pts, nullptr);

    SkPoint skPoints[pointCount];
    for (int i = 0; i < pointCount; ++i) {
        skPoints[i].set(points[i*2], points[i*2+1]);
    }
    SK_CANVAS->drawPoints(pointMode, pointCount, skPoints, paint);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawPoint(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                 jfloat x, jfloat y, jlong paint_native_ptr) {
    SkPaint &paint = *(reinterpret_cast<SkPaint *>(paint_native_ptr));
    SK_CANVAS->drawPoint(x, y, paint);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawLine(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                jfloat x0, jfloat y0, jfloat x1, jfloat y1,
                                                jlong paint_native_ptr) {
    SkPaint &paint = *(reinterpret_cast<SkPaint *>(paint_native_ptr));
    SK_CANVAS->drawLine(x0, y0, x1, y1, paint);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawRect(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                jfloat left, jfloat top, jfloat right,
                                                jfloat bottom, jlong paint_native_ptr) {
    SkRect rect;
    rect.setLTRB(left, top, right, bottom);

    SkPaint &paint = *(reinterpret_cast<SkPaint *>(paint_native_ptr));
    SK_CANVAS->drawRect(rect, paint);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawIRect(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                 jint left, jint top, jint right, jint bottom,
                                                 jlong paint_native_ptr) {
    SkIRect rect;
    rect.setLTRB(left, top, right, bottom);

    SkPaint &paint = *(reinterpret_cast<SkPaint *>(paint_native_ptr));
    SK_CANVAS->drawIRect(rect, paint);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawOval(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                jfloat left, jfloat top, jfloat right,
                                                jfloat bottom, jlong paint_native_ptr) {
    SkRect rect;
    rect.setLTRB(left, top, right, bottom);

    SkPaint &paint = *(reinterpret_cast<SkPaint *>(paint_native_ptr));
    SK_CANVAS->drawOval(rect, paint);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawCircle(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                  jfloat cx, jfloat cy, jfloat radius,
                                                  jlong paint_native_ptr) {
    SkPaint &paint = *(reinterpret_cast<SkPaint *>(paint_native_ptr));
    SK_CANVAS->drawCircle(cx, cy, radius, paint);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawArc(JNIEnv *env, jobject thiz, jlong native_ptr,
                                               jfloat left, jfloat top, jfloat right, jfloat bottom,
                                               jfloat start_angle, jfloat sweep_angle,
                                               jboolean use_center, jlong paint_native_ptr) {
    SkRect rect;
    rect.setLTRB(left, top, right, bottom);
    SkPaint &paint = *(reinterpret_cast<SkPaint *>(paint_native_ptr));
    SK_CANVAS->drawArc(rect, start_angle, sweep_angle, use_center, paint);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawRoundRect(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                     jfloat left, jfloat top, jfloat right,
                                                     jfloat bottom, jfloat rx, jfloat ry,
                                                     jlong paint_native_ptr) {
    SkRect rect;
    rect.setLTRB(left, top, right, bottom);

    SkPaint &paint = *(reinterpret_cast<SkPaint *>(paint_native_ptr));
    SK_CANVAS->drawRoundRect(rect, rx, ry, paint);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawImage(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                 jobject img, jfloat x, jfloat y,
                                                 jint filter_mode) {
    AndroidBitmapInfo info;
    if (AndroidBitmap_getInfo(env, img, &info) != 0) {
        return;
    }
    void *pixels;
    if (AndroidBitmap_lockPixels(env, img, &pixels) != 0) {
        return;
    }

    SkBitmap bitmap;
    bitmap.setInfo(SkImageInfo::MakeN32Premul(info.width, info.height), info.stride);
    bitmap.setPixels(pixels);
    sk_sp<SkImage> image = SkImage::MakeFromBitmap(bitmap);

    SkFilterMode filterMode = (SkFilterMode) filter_mode;
    SK_CANVAS->drawImage(image, x, y, SkSamplingOptions(filterMode));

    AndroidBitmap_unlockPixels(env, img);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawImageRect(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                     jobject img, jfloat src_left, jfloat src_top,
                                                     jfloat src_right, jfloat src_bottom,
                                                     jfloat dst_left, jfloat dst_top,
                                                     jfloat dst_right, jfloat dst_bottom,
                                                     jint filter_mode) {
    AndroidBitmapInfo info;
    if (AndroidBitmap_getInfo(env, img, &info) != 0) {
        return;
    }
    void *pixels;
    if (AndroidBitmap_lockPixels(env, img, &pixels) != 0) {
        return;
    }

    SkBitmap bitmap;
    bitmap.setInfo(SkImageInfo::MakeN32Premul(info.width, info.height), info.stride);
    bitmap.setPixels(pixels);
    sk_sp<SkImage> image = SkImage::MakeFromBitmap(bitmap);

    SkFilterMode filterMode = (SkFilterMode) filter_mode;

    SkRect src, dst;
    src.setLTRB(src_left, src_top, src_right, src_bottom);
    dst.setLTRB(dst_left, dst_top, dst_right, dst_bottom);
    SK_CANVAS->drawImageRect(image, src, dst, SkSamplingOptions(filterMode),
                             nullptr, SkCanvas::SrcRectConstraint::kFast_SrcRectConstraint);

    AndroidBitmap_unlockPixels(env, img);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawImageMatrix(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                       jobject img, jfloatArray mat3x3,
                                                       jlong paint_native_ptr) {
    AndroidBitmapInfo info;
    if (AndroidBitmap_getInfo(env, img, &info) != 0) {
        return;
    }
    void *pixels;
    if (AndroidBitmap_lockPixels(env, img, &pixels) != 0) {
        return;
    }

    jfloat *mat = env->GetFloatArrayElements(mat3x3, nullptr);
    SkMatrix matrix;
    matrix.set9(mat);

    SkPaint * paint = (SkPaint *) paint_native_ptr;

    sk_sp<SkPicture> pic = SkPicture::MakeFromData(pixels, info.width*info.height*4);
    SK_CANVAS->drawPicture(pic, &matrix, paint);

    env->ReleaseFloatArrayElements(mat3x3, mat, 0);
    AndroidBitmap_unlockPixels(env, img);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkBmpCanvas_nDrawText(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                jstring text, jfloat x, jfloat y,
                                                jlong font_native_ptr, jlong paint_native_ptr) {
    SkFont *font = (SkFont *) font_native_ptr;
    SkPaint *paint = (SkPaint *) paint_native_ptr;

    const char *str = env->GetStringUTFChars(text, nullptr);
    SK_CANVAS->drawString(str, x, y, *font, *paint);
}