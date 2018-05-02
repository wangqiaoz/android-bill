package com.wang.bilaccount.util;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.wang.bilaccount.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/27/027.
 */

public class LineChartUtil {

    public static void init(LineChart lineChart) {

        // 曲线赋值与设置

//        //  R.layout.custom_marker_view
//        CustomMarkerView mv = new CustomMarkerView(context, R.layout.custom_marker_view, "",type);
//        lineChart.setMarkerView(mv);
        lineChart.getDescription().setEnabled(false);
        // 是否在拆线图上添加边框
        lineChart.setDrawBorders(false);
        lineChart.setTouchEnabled(false);
        // 无数据时显示
//        lineChart.setNoDataText("暂无数据");
        lineChart.setNoDataText("");
        // 表格颜色
        lineChart.setGridBackgroundColor(Color.parseColor("#00ffffff"));
        // 是否启动触摸响应

        // 是否可以拖拽
        lineChart.setDragEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        // 是否可以绽放
        lineChart.setScaleEnabled(false);
        // false 为 x轴y轴分别缩放
        lineChart.setPinchZoom(false);
        //设置y轴不能缩放
        lineChart.setScaleYEnabled(false);
        lineChart.setScaleXEnabled(false);
        // 背景色
        lineChart.setBackgroundColor(Color.TRANSPARENT);

        // 图例对象
        Legend mLegend = lineChart.getLegend();
        // 位置
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        // 图例样式 (CIRCLE圆形；LINE线性；SQUARE是方块）
        mLegend.setForm(Legend.LegendForm.LINE);
        mLegend.setTextColor(Color.WHITE);
        mLegend.setTextSize(8f);
        // 图例大小
        mLegend.setFormSize(3f);
        mLegend.setEnabled(false);
        // 隐藏右侧Y轴
        lineChart.getAxisRight().setEnabled(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawGridLines(true);
        yAxis.setTextColor(Color.parseColor("#32b4cc"));
        yAxis.setStartAtZero(true);
        yAxis.setAxisLineColor(Color.TRANSPARENT);
        yAxis.setEnabled(true);
        //MyXFormat format = new MyXFormat(xDataList);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setAxisLineColor(Color.parseColor("#32b4cc"));
//        xAxis.setValueFormatter(new MyCustomXAxisValueFormatter(xDataList));

        // 设置标签字符间的空隙
//        xAxis.setSpaceMin(0.5f);
        // 显示X轴上的刻度
        xAxis.setDrawLabels(true);
        // 设置x轴的数据显示在报表的下方
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 设置 轴的字体颜色
        xAxis.setTextColor(Color.parseColor("#32b4cc"));
        // 轴线
        xAxis.setDrawAxisLine(true);
        // 从x轴发出纵向直线
        xAxis.setDrawGridLines(true);
        // 如果设置为true，则在绘制时会避免“剪掉”在x轴上的图表或屏幕边缘的第一个和最后一个坐标轴标签项
        xAxis.setAvoidFirstLastClipping(true);

        // 执行动画
        lineChart.animateX(10);
        lineChart.notifyDataSetChanged();
    }


    public void setData(LineChart lineChart,
                        List<String> xDataList, List<LineDataSet> lineDataSetList) {

    }
}
