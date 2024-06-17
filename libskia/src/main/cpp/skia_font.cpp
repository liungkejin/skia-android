//
// Created by LiangKeJin on 2024/6/16.
//


#include "skia.h"
#include <jni.h>

#define SK_FONT ((SkFont *) native_ptr)

extern "C"
JNIEXPORT jlong JNICALL
Java_com_bhs_android_skia_SkFont_nNew(JNIEnv *env, jobject thiz) {
    return reinterpret_cast<jlong>(new SkFont);
}
extern "C"
JNIEXPORT jlong JNICALL
Java_com_bhs_android_skia_SkFont_nNewSize(JNIEnv *env, jobject thiz, jfloat size) {
    return reinterpret_cast<jlong>(new SkFont(nullptr, size));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkFont_nFree(JNIEnv *env, jobject thiz, jlong native_ptr) {
    delete reinterpret_cast<SkFont *>(native_ptr);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkFont_nSetSize(JNIEnv *env, jobject thiz, jlong native_ptr,
                                          jfloat size) {
    SK_FONT->setSize(size);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkFont_nSetScaleX(JNIEnv *env, jobject thiz, jlong native_ptr,
                                            jfloat scale_x) {
    SK_FONT->setScaleX(scale_x);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkFont_nSetSkewX(JNIEnv *env, jobject thiz, jlong native_ptr,
                                           jfloat scale_x) {
    SK_FONT->setSkewX(scale_x);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkFont_nSetEdging(JNIEnv *env, jobject thiz, jlong native_ptr,
                                            jint edging) {
    SK_FONT->setEdging(static_cast<SkFont::Edging>(edging));
}
extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkFont_nSetHinting(JNIEnv *env, jobject thiz, jlong native_ptr,
                                             jint hinting) {
    SK_FONT->setHinting(static_cast<SkFontHinting>(hinting));
}