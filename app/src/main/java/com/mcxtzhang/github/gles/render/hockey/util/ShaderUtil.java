package com.mcxtzhang.github.gles.render.hockey.util;

public class ShaderUtil {

    public static final String GRAY_FRAGMENT_SHADER = "precision highp float;\n" +
            "uniform sampler2D u_texture;\n" +
            "varying vec2 v_textureCoordinate;\n" +
            "//灰度计算比率 (借用GPUImage的值)\n" +
            "const highp vec3 ratio = vec3(0.2125, 0.7154, 0.0721);\n" +
            "void main (void) {\n" +
            "    vec4 mask = texture2D(u_texture, v_textureCoordinate);\n" +
            "    // Gray值\n" +
            "    float luminance = dot(mask.rgb, ratio);\n" +
            "    gl_FragColor = vec4(vec3(luminance), 1.0);\n" +
            "}\n" +
            "\n";
}
