package com.atguigu.eduservice.client;

import org.springframework.stereotype.Service;

@Service
public class OrderClientImpl implements OrderClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
