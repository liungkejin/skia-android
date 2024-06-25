//
// Created by LiangKeJin on 2024/6/19.
//

#include "skia.h"
#include <jni.h>

#define SK_PATH ((SkPath *) ptr)

extern "C"
JNIEXPORT jlong JNICALL
Java_com_bhs_android_skia_SkPath_nNew(JNIEnv *env, jobject thiz) {
    return (jlong) (new SkPath());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nFree(JNIEnv *env, jobject thiz, jlong ptr) {
    delete SK_PATH;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nSetFillType(JNIEnv *env, jobject thiz, jlong ptr,
                                              jint fill_type) {
    SK_PATH->setFillType((SkPathFillType)fill_type);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nSetVolatile(JNIEnv *env, jobject thiz, jlong ptr, jboolean vol) {
    SK_PATH->setIsVolatile(vol);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nMoveTo(JNIEnv *env, jobject thiz, jlong ptr, jfloat x, jfloat y) {
    SK_PATH->moveTo(x, y);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nRMoveTo(JNIEnv *env, jobject thiz, jlong ptr, jfloat dx,
                                          jfloat dy) {
    SK_PATH->rMoveTo(dx, dy);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nLineTo(JNIEnv *env, jobject thiz, jlong ptr, jfloat x, jfloat y) {
    SK_PATH->lineTo(x, y);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nRLineTo(JNIEnv *env, jobject thiz, jlong ptr, jfloat dx,
                                          jfloat dy) {
    SK_PATH->rLineTo(dx, dy);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nQuadTo(JNIEnv *env, jobject thiz, jlong ptr, jfloat x1, jfloat y1,
                                         jfloat x2, jfloat y2) {
    SK_PATH->quadTo(x1, y1, x2, y2);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nRQuadTo(JNIEnv *env, jobject thiz, jlong ptr, jfloat dx1,
                                          jfloat dy1, jfloat dx2, jfloat dy2) {
    SK_PATH->rQuadTo(dx1, dy1, dx2, dy2);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nConicTo(JNIEnv *env, jobject thiz, jlong ptr, jfloat x1,
                                          jfloat y1, jfloat x2, jfloat y2, jfloat w) {
    SK_PATH->conicTo(x1, y1, x2, y2, w);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nRConicTo(JNIEnv *env, jobject thiz, jlong ptr, jfloat dx1,
                                           jfloat dy1, jfloat dx2, jfloat dy2, jfloat w) {
    SK_PATH->rConicTo(dx1, dy1, dx2, dy2, w);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nCubicTo(JNIEnv *env, jobject thiz, jlong ptr, jfloat x1,
                                          jfloat y1, jfloat x2, jfloat y2, jfloat x3, jfloat y3) {
    SK_PATH->cubicTo(x1, y1, x2, y2, x3, y3);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nRCubicTo(JNIEnv *env, jobject thiz, jlong ptr, jfloat dx1,
                                           jfloat dy1, jfloat dx2, jfloat dy2, jfloat dx3,
                                           jfloat dy3) {
    SK_PATH->cubicTo(dx1, dy1, dx2, dy2, dx3, dy3);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nArcTo(JNIEnv *env, jobject thiz, jlong ptr, jfloat l,
                                        jfloat t, jfloat r, jfloat b, jfloat start_angle,
                                        jfloat sweep_angle, jboolean force_mode_to) {
    SkRect rect;
    rect.setLTRB(l, t, r, b);
    SK_PATH->arcTo(rect, start_angle, sweep_angle, force_mode_to);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nArcTo2(JNIEnv *env, jobject thiz, jlong ptr, jfloat x1, jfloat y1,
                                         jfloat x2, jfloat y2, jfloat radius) {
    SK_PATH->arcTo(x1, y1, x2, y2, radius);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nAddRect(JNIEnv *env, jobject thiz, jlong ptr, jfloat l, jfloat t,
                                          jfloat r, jfloat b, jint direction, jint start) {
    SkRect rect;
    rect.setLTRB(l, t, r, b);
    SK_PATH->addRect(rect, (SkPathDirection)direction, start);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nAddOval(JNIEnv *env, jobject thiz, jlong ptr, jfloat l, jfloat t,
                                          jfloat r, jfloat b, jint direction) {
    SkRect rect;
    rect.setLTRB(l, t, r, b);
    SK_PATH->addRect(rect, (SkPathDirection)direction);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nAddCircle(JNIEnv *env, jobject thiz, jlong ptr, jfloat x,
                                            jfloat y, jfloat radius, jint direction) {
    SK_PATH->addCircle(x, y, radius, (SkPathDirection)direction);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nAddArc(JNIEnv *env, jobject thiz, jlong ptr, jfloat l, jfloat t,
                                         jfloat r, jfloat b, jfloat start_angle,
                                         jfloat sweep_angle) {
    SkRect rect;
    rect.setLTRB(l, t, r, b);
    SK_PATH->addArc(rect, start_angle, sweep_angle);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nAddRoundRect(JNIEnv *env, jobject thiz, jlong ptr, jfloat l,
                                               jfloat t, jfloat r, jfloat b, jfloat rx, jfloat ry,
                                               jint dir) {
    SkRect rect;
    rect.setLTRB(l, t, r, b);
    SK_PATH->addRoundRect(rect, rx, ry, (SkPathDirection)dir);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nAddPolygon(JNIEnv *env, jobject thiz, jlong ptr,
                                             jfloatArray points, jint count, jboolean close) {
    float *parr = env->GetFloatArrayElements(points, nullptr);

    SkPoint pts[count];
    for (int i = 0; i < count; ++i) {
        pts[i].set(parr[i*2], parr[i*2+1]);
    }
    SK_PATH->addPoly(pts, count, close);

    env->ReleaseFloatArrayElements(points, parr, 0);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkPath_nClose(JNIEnv *env, jobject thiz, jlong ptr) {
    SK_PATH->close();
}