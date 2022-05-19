package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    // 生成微信支付二维码接口
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        // 返回信息，包含二维码地址，还有其他信息
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    // 查询订单支付状态
    // 参数：订单号，根据订单号查询 支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {//出错
            return R.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            // 更改订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }

}

