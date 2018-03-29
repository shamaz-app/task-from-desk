package org.taskFromDesk.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.taskFromDesk.data.Point
import org.taskFromDesk.data.Result
import org.taskFromDesk.service.DeskTaskService

@RestController
class Controller(@Autowired
                 var deskTaskService: DeskTaskService) {

    @GetMapping("/compute")
    fun compute(
            @RequestParam(value = "x1", defaultValue = "1") x1: Int,
            @RequestParam(value = "y1", defaultValue = "1") y1: Int,
            @RequestParam(value = "x2", defaultValue = "2") x2: Int,
            @RequestParam(value = "y2", defaultValue = "3") y2: Int = 3
    ): Result {
        return deskTaskService.checkPoints(
                Point(x1, y1),
                Point(x2, y2)
        )
    }
}