package com.knakul853.mysecretdiary.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

fun provideApplicationScope() = CoroutineScope(SupervisorJob())

