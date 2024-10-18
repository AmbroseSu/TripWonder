package com.ambrose.tripwonder.dto.request;

import com.ambrose.tripwonder.entities.*;

import java.util.Date;
import java.util.List;

public class PackageTourRequest {

    private String name;
    private String description;
    private String shortDescription;
    private double price;
    private Date startTime;
    private Date endTime;
    private int attendance;
    private boolean status;
    private Long categoryId;

    private Long provinceId;

    private int ratingReviews;

    private List<Recommendation> recommendations;

    private List<Gallery> galleries;

    private List<OrderDetail> orderDetails;

    private Supplier supplier; // Quan hệ 1 Tour thuộc về 1 Supplier
}
