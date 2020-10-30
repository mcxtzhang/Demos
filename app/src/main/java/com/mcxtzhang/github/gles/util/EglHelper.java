package com.mcxtzhang.github.gles.util;

/**
 * An EGL helper class.
 */

public class EglHelper {
//    public final static boolean LOG_EGL = true;
//
//    public EglHelper(WeakReference<TextureView> glSurfaceViewWeakRef) {
//        mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
//    }
//
//    /**
//     * Initialize EGL for a given configuration spec.
//     */
//    public void start() {
//        if (LOG_EGL) {
//            Log.w("EglHelper", "start() tid=" + Thread.currentThread().getId());
//        }
//        /*
//         * Get an EGL instance
//         */
//        mEgl = (EGL10) EGLContext.getEGL();
//
//        /*
//         * Get to the default display.
//         */
//        mEglDisplay = mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
//
//        if (mEglDisplay == EGL10.EGL_NO_DISPLAY) {
//            throw new RuntimeException("eglGetDisplay failed");
//        }
//
//        /*
//         * We can now initialize EGL for that display
//         */
//        int[] version = new int[2];
//        if (!mEgl.eglInitialize(mEglDisplay, version)) {
//            throw new RuntimeException("eglInitialize failed");
//        }
//        TextureView view = mGLSurfaceViewWeakRef.get();
//        if (view == null) {
//            mEglConfig = null;
//            mEglContext = null;
//        } else {
//            mEglConfig = view.mEGLConfigChooser.chooseConfig(mEgl, mEglDisplay);
//
//            /*
//             * Create an EGL context. We want to do this as rarely as we can, because an
//             * EGL context is a somewhat heavy object.
//             */
//            mEglContext = view.mEGLContextFactory.createContext(mEgl, mEglDisplay, mEglConfig);
//        }
//        if (mEglContext == null || mEglContext == EGL10.EGL_NO_CONTEXT) {
//            mEglContext = null;
//            throwEglException("createContext");
//        }
//        if (LOG_EGL) {
//            Log.w("EglHelper", "createContext " + mEglContext + " tid=" + Thread.currentThread().getId());
//        }
//
//        mEglSurface = null;
//    }
//
//    /**
//     * Create an egl surface for the current SurfaceHolder surface. If a surface
//     * already exists, destroy it before creating the new surface.
//     *
//     * @return true if the surface was created successfully.
//     */
//    public boolean createSurface() {
//        if (LOG_EGL) {
//            Log.w("EglHelper", "createSurface()  tid=" + Thread.currentThread().getId());
//        }
//        /*
//         * Check preconditions.
//         */
//        if (mEgl == null) {
//            throw new RuntimeException("egl not initialized");
//        }
//        if (mEglDisplay == null) {
//            throw new RuntimeException("eglDisplay not initialized");
//        }
//        if (mEglConfig == null) {
//            throw new RuntimeException("mEglConfig not initialized");
//        }
//
//        /*
//         *  The window size has changed, so we need to create a new
//         *  surface.
//         */
//        destroySurfaceImp();
//
//        /*
//         * Create an EGL surface we can render into.
//         */
//        GLSurfaceView view = mGLSurfaceViewWeakRef.get();
//        if (view != null) {
//            mEglSurface = view.mEGLWindowSurfaceFactory.createWindowSurface(mEgl,
//                    mEglDisplay, mEglConfig, view.getHolder());
//        } else {
//            mEglSurface = null;
//        }
//
//        if (mEglSurface == null || mEglSurface == EGL10.EGL_NO_SURFACE) {
//            int error = mEgl.eglGetError();
//            if (error == EGL10.EGL_BAD_NATIVE_WINDOW) {
//                Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
//            }
//            return false;
//        }
//
//        /*
//         * Before we can issue GL commands, we need to make sure
//         * the context is current and bound to a surface.
//         */
//        if (!mEgl.eglMakeCurrent(mEglDisplay, mEglSurface, mEglSurface, mEglContext)) {
//            /*
//             * Could not make the context current, probably because the underlying
//             * SurfaceView surface has been destroyed.
//             */
//            logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", mEgl.eglGetError());
//            return false;
//        }
//
//        return true;
//    }
//
//    /**
//     * Create a GL object for the current EGL context.
//     *
//     * @return
//     */
//    GL createGL() {
//
//        GL gl = mEglContext.getGL();
//        GLSurfaceView view = mGLSurfaceViewWeakRef.get();
//        if (view != null) {
//            if (view.mGLWrapper != null) {
//                gl = view.mGLWrapper.wrap(gl);
//            }
//
//            if ((view.mDebugFlags & (DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS)) != 0) {
//                int configFlags = 0;
//                Writer log = null;
//                if ((view.mDebugFlags & DEBUG_CHECK_GL_ERROR) != 0) {
//                    configFlags |= GLDebugHelper.CONFIG_CHECK_GL_ERROR;
//                }
//                if ((view.mDebugFlags & DEBUG_LOG_GL_CALLS) != 0) {
//                    log = new LogWriter();
//                }
//                gl = GLDebugHelper.wrap(gl, configFlags, log);
//            }
//        }
//        return gl;
//    }
//
//    /**
//     * Display the current render surface.
//     *
//     * @return the EGL error code from eglSwapBuffers.
//     */
//    public int swap() {
//        if (!mEgl.eglSwapBuffers(mEglDisplay, mEglSurface)) {
//            return mEgl.eglGetError();
//        }
//        return EGL10.EGL_SUCCESS;
//    }
//
//    public void destroySurface() {
//        if (LOG_EGL) {
//            Log.w("EglHelper", "destroySurface()  tid=" + Thread.currentThread().getId());
//        }
//        destroySurfaceImp();
//    }
//
//    private void destroySurfaceImp() {
//        if (mEglSurface != null && mEglSurface != EGL10.EGL_NO_SURFACE) {
//            mEgl.eglMakeCurrent(mEglDisplay, EGL10.EGL_NO_SURFACE,
//                    EGL10.EGL_NO_SURFACE,
//                    EGL10.EGL_NO_CONTEXT);
//            GLSurfaceView view = mGLSurfaceViewWeakRef.get();
//            if (view != null) {
//                view.mEGLWindowSurfaceFactory.destroySurface(mEgl, mEglDisplay, mEglSurface);
//            }
//            mEglSurface = null;
//        }
//    }
//
//    public void finish() {
//        if (LOG_EGL) {
//            Log.w("EglHelper", "finish() tid=" + Thread.currentThread().getId());
//        }
//        if (mEglContext != null) {
//            GLSurfaceView view = mGLSurfaceViewWeakRef.get();
//            if (view != null) {
//                view.mEGLContextFactory.destroyContext(mEgl, mEglDisplay, mEglContext);
//            }
//            mEglContext = null;
//        }
//        if (mEglDisplay != null) {
//            mEgl.eglTerminate(mEglDisplay);
//            mEglDisplay = null;
//        }
//    }
//
//    private void throwEglException(String function) {
//        throwEglException(function, mEgl.eglGetError());
//    }
//
//    public static void throwEglException(String function, int error) {
//        String message = formatEglError(function, error);
//        if (LOG_THREADS) {
//            Log.e("EglHelper", "throwEglException tid=" + Thread.currentThread().getId() + " "
//                    + message);
//        }
//        throw new RuntimeException(message);
//    }
//
//    public static void logEglErrorAsWarning(String tag, String function, int error) {
//        Log.w(tag, formatEglError(function, error));
//    }
//
//    public static String formatEglError(String function, int error) {
//        return function + " failed: " + EGLLogWrapper.getErrorString(error);
//    }
//
//    private WeakReference<TextureView> mGLSurfaceViewWeakRef;
//    EGL10 mEgl;
//    EGLDisplay mEglDisplay;
//    EGLSurface mEglSurface;
//    EGLConfig mEglConfig;
//    EGLContext mEglContext;

}