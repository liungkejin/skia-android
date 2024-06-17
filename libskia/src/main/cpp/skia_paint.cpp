//
// Created by LiangKeJin on 2024/6/16.
//

#include "skia.h"
#include <jni.h>

#define SK_PAINT ((SkPaint *) native_ptr)

extern "C"
JNIEXPORT jlong JNICALL
Java_com_bhs_android_skia_SkPaint_nNew(JNIEnv *env, jobject thiz) {
    return reinterpret_cast<jlong>(new SkPaint);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPaint_nFree(JNIEnv *env, jobject thiz, jlong native_ptr) {
    delete reinterpret_cast<SkPaint *>(native_ptr);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPaint_nReset(JNIEnv *env, jobject thiz, jlong native_ptr) {
    SK_PAINT->reset();
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_bhs_android_skia_SkPaint_nIsAntiAlias(JNIEnv *env, jobject thiz, jlong native_ptr) {
    return SK_PAINT->isAntiAlias();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPaint_nSetAntiAlias(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                jboolean antialias) {
    SK_PAINT->setAntiAlias(antialias);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPaint_nSetStyle(JNIEnv *env, jobject thiz, jlong native_ptr,
                                            jint style) {
    SK_PAINT->setStyle(static_cast<SkPaint::Style>(style));
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPaint_nSetColor(JNIEnv *env, jobject thiz, jlong native_ptr,
                                            jint color) {
    SK_PAINT->setColor(color);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPaint_nSetAlpha(JNIEnv *env, jobject thiz, jlong native_ptr,
                                            jfloat alpha) {
    SK_PAINT->setAlpha(alpha);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPaint_nSetStrokeWidth(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                  jfloat width) {
    SK_PAINT->setStrokeWidth(width);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPaint_nSetStrokeMiter(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                  jfloat miter) {
    SK_PAINT->setStrokeMiter(miter);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPaint_nSetStrokeCap(JNIEnv *env, jobject thiz, jlong native_ptr,
                                                jint cap) {
    SK_PAINT->setStrokeCap(static_cast<SkPaint::Cap>(cap));
}