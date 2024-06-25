//
// Created by LiangKeJin on 2024/6/16.
//


#include "skia.h"
#include <jni.h>
#include <android/log.h>

#define SK_FONT ((SkFont *) native_ptr)

extern "C"
JNIEXPORT jlong JNICALL
Java_com_bhs_android_skia_SkFont_nNew(JNIEnv *env, jobject thiz) {
    auto mgr = SkFontMgr::RefDefault();
    // TODO 实现根据当前语言，自动适配查找合适的文字
    uint32_t utf32string[] = { 0x4F60 };
    SkTypeface *typeface = mgr->matchFamilyStyleCharacter(nullptr,SkFontStyle::Normal(),
                                                          nullptr, 0, utf32string[0]);
//    __android_log_print(ANDROID_LOG_DEBUG, "ntest", "typeface: %p", typeface);
    if (typeface) {
        SkString familyName;
        typeface->getFamilyName(&familyName);

//        __android_log_print(ANDROID_LOG_DEBUG, "ntest", "typeface name: %s", familyName.c_str());
        return reinterpret_cast<jlong>(new SkFont(sk_sp(typeface)));
    }

    return reinterpret_cast<jlong>(new SkFont());
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_bhs_android_skia_SkFont_nNew2(JNIEnv *env, jobject thiz, jstring font_file, jint index) {
    const char *filePath = env->GetStringUTFChars(font_file, nullptr);

    sk_sp<SkTypeface> face = SkTypeface::MakeFromFile(filePath, index);
    jlong ptr = (jlong) (new SkFont(face));

    env->ReleaseStringUTFChars(font_file, filePath);
    return ptr;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_bhs_android_skia_SkFont_nNew3(JNIEnv *env, jobject thiz, jstring family_name, jint style) {
    const char *name = env->GetStringUTFChars(family_name, nullptr);
    SkFontStyle fontStyle = SkFontStyle::Normal();
    if (style == 1) {
        fontStyle = SkFontStyle::Bold();
    } else if (style == 2) {
        fontStyle == SkFontStyle::Italic();
    } else if (style == 3) {
        fontStyle == SkFontStyle::BoldItalic();
    }

    sk_sp<SkTypeface> face = SkTypeface::MakeFromName(name, fontStyle);
    jlong ptr = (jlong) (new SkFont(face));

    env->ReleaseStringUTFChars(family_name, name);
    return ptr;
}

//extern "C"
//JNIEXPORT jlong JNICALL
//Java_com_bhs_android_skia_SkFont_nNewSize(JNIEnv *env, jobject thiz, jfloat size) {
//    return reinterpret_cast<jlong>(new SkFont(nullptr, size));
//}

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

extern "C"
JNIEXPORT void JNICALL
Java_com_bhs_android_skia_SkFont_nMeasureText(JNIEnv *env, jobject thiz, jlong native_ptr,
                                              jstring text, jlong paint_native_ptr, jfloatArray output) {
    float * out = env->GetFloatArrayElements(output, nullptr);
    const char * str = env->GetStringUTFChars(text, nullptr);
    int strlen = env->GetStringUTFLength(text);

    SkRect bounds;
    SkPaint *paint = (SkPaint *) paint_native_ptr;
    float width = SK_FONT->measureText(str, strlen,
                                       SkTextEncoding::kUTF8, &bounds, paint);
    env->ReleaseStringUTFChars(text, str);

    out[0] = width;
    out[1] = bounds.left();
    out[2] = bounds.top();
    out[3] = bounds.right();
    out[4] = bounds.bottom();
    env->ReleaseFloatArrayElements(output, out, 0);
}