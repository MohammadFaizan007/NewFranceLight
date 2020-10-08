package com.inferrix.lightsmart.constant;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.core.content.ContextCompat;

import static android.view.View.LAYER_TYPE_SOFTWARE;

public class Constants {
    public static final String ATTRIBUTE_TYPE = "attribute_type";
    public static final String LUX_LEVEL_TYPE = "lux_level_type";
    public static final String LUX_LEVEL_TYPE_TWO = "lux_level_type_two";
    public static final String ADD_ASSOCIATE_TYPE = "add_associate_type";
    public final static String MAIN_KEY="HelperLoad";
    public final static String GROUP_DETAIL_KEY="GroupDetail";
    public final static String LIGHT_DETAIL_KEY="LightDetail";
    public final static String DETAIL_KEY="DeviceDetail";
    public final static String SITE_GROUP_DETAIL_KEY="SiteGroupDetail";
    public final static String BUILDIN_GROUP_DETAIL_KEY="BuildingGroupDetail";
    public final static int MY_NETWORK_CODE=101;
    public final static int SMART_DEVICE_CODE=102;
    public final static int DASHBOARD_CODE=103;
    public final static int GROUP_CODE=104;
    public final static int DEMO_CODE=105;
    public final static int EDIT_LIGHT=109;
    public final static int EDIT_GROUP=110;
    public final static int CREATE_GROUP=111;
    public final static int ASSOCIATE=112;
    public final static int READ_ASSOCIATE=113;
    public final static int ADD_ASSOCIATE=114;
    public final static int READ_DETAILS=115;
    public final static int EDIT_SITE_GROUP=116;
    public final static int EDIT_BUILDING_GROUP=117;
    public final static int EDIT_LEVEL_GROUP=118;
    public final static int EDIT_ROOM_GROUP=119;
    public final static int EDIT_LUX_LEVEL=120;
    public static String PWM="PWM Driver Cenzer";             ///0x00
    public static String PWM_INFERRIX="PWM Driver Inferrix";  ///0x02
    public static String VCC="0-10 V Controller Cenzer";      ///0x01
    public static String VCC_INFERRIX="0-10 V Controller Inferrix";///0x03
    public static String INFERRIX_SMART_DERIVE="Inferrix Derive Type";///0x04
    public static Drawable generateBackgroundWithShadow(View view, @ColorRes int backgroundColor,
                                                        @DimenRes int cornerRadius,
                                                        @ColorRes int shadowColor,
                                                        @DimenRes int elevation,
                                                        int shadowGravity) {
        float cornerRadiusValue = view.getContext().getResources().getDimension(cornerRadius);
        int elevationValue = (int) view.getContext().getResources().getDimension(elevation);
        int shadowColorValue = ContextCompat.getColor(view.getContext(),shadowColor);
        int backgroundColorValue = ContextCompat.getColor(view.getContext(),backgroundColor);

        float[] outerRadius = {cornerRadiusValue, cornerRadiusValue, cornerRadiusValue,
                cornerRadiusValue, cornerRadiusValue, cornerRadiusValue, cornerRadiusValue,
                cornerRadiusValue};

        Paint backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setShadowLayer(cornerRadiusValue, 10, 10, 50);

        Rect shapeDrawablePadding = new Rect();
        shapeDrawablePadding.left = elevationValue;
        shapeDrawablePadding.right = elevationValue;

        int DY;
        switch (shadowGravity) {
            case Gravity.CENTER:
                shapeDrawablePadding.top = elevationValue;
                shapeDrawablePadding.bottom = elevationValue;
                DY = 0;
                break;
            case Gravity.TOP:
                shapeDrawablePadding.top = elevationValue*2;
                shapeDrawablePadding.bottom = elevationValue;
                DY = -1*elevationValue/3;
                break;
            default:
            case Gravity.BOTTOM:
                shapeDrawablePadding.top = elevationValue;
                shapeDrawablePadding.bottom = elevationValue*2;
                DY = elevationValue/3;
                break;
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setPadding(shapeDrawablePadding);
        shapeDrawable.getPaint().setColor(backgroundColorValue);
        shapeDrawable.getPaint().setShadowLayer(cornerRadiusValue/3, 0, DY, shadowColorValue);

        view.setLayerType(LAYER_TYPE_SOFTWARE, shapeDrawable.getPaint());

        shapeDrawable.setShape(new RoundRectShape(outerRadius, null, null));

        LayerDrawable drawable = new LayerDrawable(new Drawable[]{shapeDrawable});
        drawable.setLayerInset(0, elevationValue, elevationValue*2, elevationValue, elevationValue*2);

        return drawable;

    }

}
