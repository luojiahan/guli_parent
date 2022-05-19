package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-05-17
 */
@Api(tags = "订单接口")
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 生成订单的方法
    @ApiOperation(value = "生成订单接口")
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        // 返回订单号
        String orderId = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    // 根据id获取订单信息接口
    @ApiOperation(value = "根据id获取订单信息接口")
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrder(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    // 根据用户id和课程id查询订单信息
    @ApiOperation(value = "根据用户id和课程id查询订单信息")
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,
                               @PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq("course_id", courseId);
        wrapper.eq("status", 1);

        // 订单状态是1表示支付成功
        int count = orderService.count(wrapper);
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }
}

