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


    public static final String YLZZ_FRAGMENT_SHADER = "// 精度\n" +
            "precision highp float;\n" +
            "// 通过uniform传递过来的纹理\n" +
            "uniform sampler2D u_texture;\n" +
            "// 纹理坐标\n" +
            "varying highp vec2 v_textureCoordinate;\n" +
            "\n" +
            "void main() {\n" +
            "    \n" +
            "    vec2 uv = v_textureCoordinate.xy;\n" +
            "    float y;\n" +
            "    // 0.0～0.5 范围内显示0.25～0.75范围的像素\n" +
            "    if (uv.y >= 0.0 && uv.y <= 0.5) {\n" +
            "        y = uv.y + 0.25;\n" +
            "    }else {\n" +
            "        // 0.5～1.0范围内显示 0.25～0.75范围的像素\n" +
            "        y = uv.y - 0.25;\n" +
            "    }\n" +
            "    \n" +
            "    // 获取纹理像素，用于显示\n" +
            "    gl_FragColor = texture2D(u_texture, vec2(uv.x, y));\n" +
            "}\n" +
            "\n";


    public static final String YLZZ_FRAGMENT_SHADER_2 = "// 精度\n" +
            "precision highp float;\n" +
            "// 通过uniform传递过来的纹理\n" +
            "uniform sampler2D u_texture;\n" +
            "// 纹理坐标\n" +
            "varying highp vec2 v_textureCoordinate;\n" +
            "\n" +
            "void main() {\n" +
            "    \n" +
            "    vec2 uv = v_textureCoordinate.xy;\n" +
            "    float x;\n" +
            "   float distance = 1.0/3.0;\n" +
            "    if (uv.x >= 0.0 && uv.x <= distance) {\n" +
            "        x = uv.x * 3.0;\n" +
            "    }else if (uv.x >= distance && uv.x <= distance*2.0) {\n" +
            "        x = (uv.x - distance)*3.0;\n" +
            "    }else {\n" +
            "        x = (uv.x - distance*2.0)*3.0;\n" +
            "    }\n" +
            "    \n" +
            "    // 获取纹理像素，用于显示\n" +
            "    gl_FragColor = texture2D(u_texture, vec2(x, uv.y));\n" +
            "}\n" +
            "\n";
}
