package com.example.checheritox.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class ChecheritoViewModel<T>(public val activity: T) : ViewModel() {

    // El que llama las corutinas
    protected var viewModelJob = Job()

    // El scope
    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Siempre overridear y cancelar los lanzadores de corutinas en este caso solo uno (viewModelJob)
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}
