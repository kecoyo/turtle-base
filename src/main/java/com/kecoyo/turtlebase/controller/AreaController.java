package com.kecoyo.turtlebase.controller;

import com.kecoyo.turtlebase.common.web.ResponseResult;
import com.kecoyo.turtlebase.domain.entity.Area;
import com.kecoyo.turtlebase.service.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/area")
@Tag(name = "区域服务")
public class AreaController {

    @Autowired
    private AreaService areaService;


    @Operation(summary = "获取所有区域")
    @GetMapping("/getAllAreas")
    public ResponseResult<List<Area>> getAllAreas() {
        List<Area> result = areaService.list();
        return ResponseResult.success(result);
    }

}
