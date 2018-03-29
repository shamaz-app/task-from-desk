package org.taskFromDesk.service

import org.springframework.stereotype.Service
import org.taskFromDesk.data.Point
import org.taskFromDesk.data.Result

@Service
class DeskTaskService {

    fun checkPoints(pointFrom: Point, pointTo: Point)
            : Result {

        var checkX = false
        var checkY = false

        val allowedStepsForX = pointTo.x / pointFrom.y
        val allowedStepsForY = pointTo.y / pointFrom.x

        for (i in 1..allowedStepsForX) {
            if ((pointTo.x - (pointFrom.y * i)) % pointFrom.x == 0) {
                checkX = true
                break
            }
        }

        if (!checkX) return Result(false, "Can't reach the second point")

        for (i in 1..allowedStepsForY) {
            if ((pointTo.y - (pointFrom.x * i)) % pointFrom.y == 0) {
                checkY = true
                break
            }
        }
        val checked = checkX && checkY
        val message = if (checked) "Can reach the second point" else "Can't reach the second point"


        return Result(checked, message)
    }
}