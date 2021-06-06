package com.lazada.core

sealed class Failure {
    object NetworkConnection : Failure()
    object GeneralFailure: Failure()
    abstract class FeatureFailure: Failure()
}