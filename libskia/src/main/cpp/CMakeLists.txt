# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

project("kejin_skia")

set(SKIA_PATH ${CMAKE_SOURCE_DIR}/../../../../skia-android-next-release)
message("SKIA_PATH: ${SKIA_PATH}")

set(ABI ${CMAKE_ANDROID_ARCH})
message("ABI: ${ABI}, arch: ${CMAKE_ANDROID_ARCH}, abi: ${ANDROID_ABI}")

include_directories(
        ${SKIA_PATH}
        ${SKIA_PATH}/out/${ABI}/gen )

#file(GLOB_RECURSE SKIA_LIBS ${SKIA_PATH}/out/${ABI}/*.a)

set(SKIA_LIBS ${SKIA_PATH}/out/${ABI}/libskia.a)

add_library(${CMAKE_PROJECT_NAME} SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        skia.cpp)

target_link_libraries(${CMAKE_PROJECT_NAME}
        # List libraries link to the target library
        android
        log
        jnigraphics
        EGL
        GLESv2
        ${SKIA_LIBS})