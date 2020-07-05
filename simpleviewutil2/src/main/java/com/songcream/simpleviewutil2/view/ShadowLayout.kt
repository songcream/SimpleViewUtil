package com.songcream.simpleviewutil2.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.RelativeLayout
import utils.UIUtils

class ShadowLayout : RelativeLayout{
    var paint= Paint()
    var shadowWidth=6f
    var lShadow=true
    var tShadow=true
    var rShadow=true
    var bShadow=true
    var position="4"
        set(value) {
            field=value
            lShadow=position.contains("4") || position.contains("0")
            tShadow=position.contains("4") || position.contains("1")
            rShadow= position.contains("4") || position.contains("2")
            bShadow=position.contains("4") || position.contains("3")
            invalidate()
        }
    var lGradient: LinearGradient? = null
    var tGradient:LinearGradient? = null
    var rGradient:LinearGradient? = null
    var bGradient:LinearGradient? = null
    var ltGradient:RadialGradient? = null
    var rtGradient:RadialGradient? = null
    var lbGradient:RadialGradient? = null
    var rbGradient:RadialGradient? = null
    var colors= intArrayOf(Color.parseColor("#d0bfc1c2"),Color.parseColor("#d0edeef2"),Color.parseColor("#d0fafafa"))
    var positions= floatArrayOf(0f,0.03f,1f)

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    private fun init() {
        shadowWidth= UIUtils.dip2px(context,10f).toFloat()
        setPadding(if (lShadow) shadowWidth.toInt() else 0,
                if(tShadow) shadowWidth.toInt() else 0,
                if(rShadow) shadowWidth.toInt() else 0,
                if(bShadow) shadowWidth.toInt() else 0)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(lGradient==null){
            lGradient = LinearGradient(shadowWidth,0f,0f,0f,colors,positions, Shader.TileMode.CLAMP)
            tGradient = LinearGradient(0f,shadowWidth,0f,0f,colors,positions, Shader.TileMode.CLAMP)
            rGradient = LinearGradient(width-shadowWidth,0f,width*1f,0f,colors,positions, Shader.TileMode.CLAMP)
            bGradient = LinearGradient(0f,height-shadowWidth,0f,height*1f,colors,positions, Shader.TileMode.CLAMP)
            ltGradient= RadialGradient(shadowWidth,shadowWidth,shadowWidth,colors,positions, Shader.TileMode.CLAMP)
            rtGradient= RadialGradient(width-shadowWidth,shadowWidth,shadowWidth,colors,positions, Shader.TileMode.CLAMP)
            lbGradient= RadialGradient(shadowWidth,height-shadowWidth,shadowWidth,colors,positions, Shader.TileMode.CLAMP)
            rbGradient= RadialGradient(width-shadowWidth,height-shadowWidth,shadowWidth,colors,positions, Shader.TileMode.CLAMP)
        }

        if(lShadow) {
            paint.setShader(lGradient)
            canvas.drawRect(shadowWidth, shadowWidth, 0f, height-shadowWidth, paint)
        }
        if(tShadow) {
            paint.setShader(tGradient)
            canvas.drawRect(shadowWidth, shadowWidth, width-shadowWidth, 0f, paint)
        }
        if(rShadow) {
            paint.setShader(rGradient)
            canvas.drawRect(width-shadowWidth, shadowWidth, width * 1f, height-shadowWidth, paint)
        }
        if(bShadow) {
            paint.setShader(bGradient)
            canvas.drawRect(shadowWidth, height  - shadowWidth, width-shadowWidth, height * 1f, paint)
        }
        if(lShadow || tShadow) {
            paint.setShader(ltGradient)
            canvas.drawRect(0f, 0f, shadowWidth, shadowWidth, paint)
        }
        if(lShadow || bShadow){
            paint.setShader(lbGradient)
            canvas.drawRect(0f, height-shadowWidth, shadowWidth, height*1f, paint)
        }
        if(rShadow || tShadow){
            paint.setShader(rtGradient)
            canvas.drawRect(width-shadowWidth, 0f, width*1f, shadowWidth, paint)
        }
        if(rShadow || bShadow){
            paint.setShader(rbGradient)
            canvas.drawRect(width-shadowWidth, height*1f-shadowWidth, width*1f, height*1f, paint)
        }
    }

    open class Position{
        companion object{
            val BOTTOM = "3"
            val LEFT="0";
            val RIGHT="2";
            val TOP="1";
            val ALL="4";
        }
    }
}