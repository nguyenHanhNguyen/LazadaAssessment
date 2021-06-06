package com.lazada.model.user

import com.lazada.core.Failure

class UserFailure {
    class UserNotFound() : Failure.FeatureFailure()
}